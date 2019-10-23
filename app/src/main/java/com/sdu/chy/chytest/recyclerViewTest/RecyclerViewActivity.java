package com.sdu.chy.chytest.recyclerViewTest;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sdu.chy.chytest.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    RecyclerView recyclerView;
    List<Goods> goodsList;
    GoodsAdapter goodsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        initDatas();
        initViews();
    }

    public void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.chy_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        goodsAdapter = new GoodsAdapter();
        goodsAdapter.setDataItems(goodsList);
        goodsAdapter.addDelegate(new GoodsOfMineDelegateAdapter());
        goodsAdapter.addDelegate(new GoodsOfOthersDelegateAdapter());

        recyclerView.setAdapter(goodsAdapter);
    }

    public void initDatas(){
        goodsList = new ArrayList<>();
        Goods goods1 = new Goods("谋杀疑云","http://img5.mtime.cn/mg/2019/04/28/143651.43848115_120X90X4.jpg","预告安妮斯顿&桑德勒变头号嫌疑犯",1,"Tirami");
        Goods goods2 = new Goods("零食大礼包","http://img5.mtime.cn/mg/2019/04/27/112949.17035685_120X90X4.jpg","很多好吃的零食，有坚果、蔬菜、零食等等",1,"xianxianyun");
        Goods goods3 = new Goods("好看的衣服","http://img5.mtime.cn/mg/2019/04/25/184654.52419742_120X90X4.jpg","夏季衣服清仓，连衣裙短袖衬衫等等",0,"阿云");
        goodsList.add(goods1);
        goodsList.add(goods2);
        goodsList.add(goods3);
    }
}
