package com.sdu.chy.chytest.recyclerViewTest;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Goods> goodslist = new ArrayList<>();
    private List<IDelegateAdapter> delegateAdapters = new ArrayList<>();
    private Integer currentType = 0;

    public void setDataItems(List<Goods> goodslist){
        this.goodslist = goodslist;
        notifyDataSetChanged();
    }

    public void addDelegate(IDelegateAdapter delegateAdapter){
        delegateAdapters.add(delegateAdapter);
    }

    @Override
    public int getItemViewType(int position) {
        Goods goods = goodslist.get(position);
        for(IDelegateAdapter delegateAdapter : delegateAdapters){
            if(delegateAdapter.isForViewType(goods))
                currentType = goods.getGoodsType();
        }

        return currentType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 缓冲池没有相应的ViewHolder，根据 getItemViewType得到的viewType设置相应的布局
        // 找到对应的委托Adapter
        IDelegateAdapter delegateAdapter = delegateAdapters.get(viewType);
        // 把onCreateViewHolder交给委托Adapter去处理
        RecyclerView.ViewHolder viewHolder = delegateAdapter.onCreateViewHolder(parent, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // RecyclerView 缓冲池有相应的布局
        int viewType = holder.getItemViewType();
        // 找到对应的委托Adapter
        IDelegateAdapter delegateAdapter = delegateAdapters.get(viewType);
        // 把onCreateViewHolder交给委托Adapter去处理
        delegateAdapter.onBindViewHolder(holder,position,goodslist.get(position));
    }

    @Override
    public int getItemCount() {
        return goodslist.size();
    }
}
