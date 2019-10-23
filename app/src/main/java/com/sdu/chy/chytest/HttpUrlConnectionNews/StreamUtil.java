package com.sdu.chy.chytest.HttpUrlConnectionNews;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil {

    public static String StreamToString(InputStream inputStream){
        String result = "";
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buffer))!=-1){
                outputStream.write(buffer, 0, len);
                outputStream.flush();
            }
            //result = outputStream.toString("utf-8");
            result = new String(outputStream.toByteArray(),"utf-8");

            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
            return result;
    }
}
