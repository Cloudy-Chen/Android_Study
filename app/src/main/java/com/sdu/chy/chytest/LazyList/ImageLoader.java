package com.sdu.chy.chytest.LazyList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import com.sdu.chy.chytest.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by danding on 2019/4/8.
 */

public class ImageLoader {

//    关于异步加载图片的思路是：
//    1.第一次进入时，是没有图片的，这时候我们会启动一个线程池，异步的从网上获得图片数据，为了防止图片过大导致OOM，可以调用BitmapFactory中的Options类对图片进行适当的缩放，最后再显示主线程的ImageView上。
//    2.把加载好的图片以图片的Url做为唯一的key存入内存缓存当中，并严格的控制好这个缓存的大小，防止OOM的发生。
//    3.把图片缓存在SD当中，如果没有SD卡就放在系统的缓存目录cache中，以保证在APP退出后，下次进来能看到缓存中的图片，这样就可以让使你的APP不会给客户呈现一片空白的景象。
//    4.用户第二次进来的时候，加载图片的流程则是倒序的，首先从内容中看是否存在缓存图片，如果没有就从SD卡当中寻找，再没有然后才是从网络中获取图片数据。这样做的既可以提高加载图片的效率，同时也节约了用户的流量。

    MemoryCache memoryCache=new MemoryCache();
    FileCache fileCache;
    private Map<ImageView, String> imageViews= Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
    ExecutorService executorService;
    Handler handler=new Handler();//handler to display images in UI thread

    public ImageLoader(Context context){
        fileCache=new FileCache(context);
        executorService= Executors.newFixedThreadPool(5);
    }

    final int stub_id= R.drawable.ic_launcher_background;
    public void DisplayImage(String url, ImageView imageView)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap=memoryCache.get(url);
        if(bitmap!=null)
            imageView.setImageBitmap(bitmap);
        else
        {
            queuePhoto(url, imageView);
            imageView.setImageResource(stub_id);
        }
    }

    private void queuePhoto(String url, ImageView imageView)
    {
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url)
    {
        File f=fileCache.getFile(url);

        //from SD cache
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;

        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            conn.disconnect();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1=new FileInputStream(f);
            BitmapFactory.decodeStream(stream1,null,o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
            Bitmap bitmap=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u;
            imageView=i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad photoToLoad){
            this.photoToLoad=photoToLoad;
        }

        @Override
        public void run() {
            try{
                if(imageViewReused(photoToLoad))
                    return;
                Bitmap bmp=getBitmap(photoToLoad.url);
                memoryCache.put(photoToLoad.url, bmp);
                if(imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);
            }catch(Throwable th){
                th.printStackTrace();
            }
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad){
        String tag=imageViews.get(photoToLoad.imageView);
        if(tag==null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap!=null)
                photoToLoad.imageView.setImageBitmap(bitmap);
            else
                photoToLoad.imageView.setImageResource(stub_id);
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }

}
