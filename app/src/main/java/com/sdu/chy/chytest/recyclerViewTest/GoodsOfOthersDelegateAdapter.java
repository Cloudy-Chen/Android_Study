package com.sdu.chy.chytest.recyclerViewTest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sdu.chy.chytest.R;

public class GoodsOfOthersDelegateAdapter implements IDelegateAdapter {

    @Override
    public boolean isForViewType(Goods goods) {
        if(goods.getGoodsType()==0)return true;
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //缓存池没有ViewHolder，则创建一个ViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_of_others,parent,false);
        GoodsOfMineViewHolder viewHolder = new GoodsOfMineViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Goods goods) {
        //缓存池已有ViewHolder，直接拿出来进行复用
        GoodsOfMineViewHolder viewHolder = (GoodsOfMineViewHolder) holder;
        viewHolder.goodsName.setText(goods.getGoodsName());
        viewHolder.goodsDescription.setText(goods.getGoodsDescription());
        viewHolder.goodsPublisher.setText(goods.getPublisherName());
        Glide.with(holder.itemView.getContext()).load(goods.getGoodsImg()).into(viewHolder.goodsImg);
    }

    private static class GoodsOfMineViewHolder extends RecyclerView.ViewHolder{

        TextView goodsName;
        ImageView goodsImg;
        TextView goodsDescription;
        TextView goodsPublisher;

        public GoodsOfMineViewHolder(View view){
            super(view);
            goodsName = (TextView) view.findViewById(R.id.name_goods_of_others);
            goodsImg = (ImageView) view.findViewById(R.id.img_goods_of_others);
            goodsPublisher = (TextView) view.findViewById(R.id.publisher_goods_of_others);
            goodsDescription = (TextView) view.findViewById(R.id.description_goods_of_others);
        }
    }
}
