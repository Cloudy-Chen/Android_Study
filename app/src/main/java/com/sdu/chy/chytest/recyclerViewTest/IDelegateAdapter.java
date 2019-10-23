package com.sdu.chy.chytest.recyclerViewTest;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface IDelegateAdapter {

    // 查找委托时调用的方法，根据商品的类型选择样式
    boolean isForViewType(Goods goods);

    // 用于委托Adapter的onCreateViewHolder方法
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    // 用于委托Adapter的onBindViewHolder方法
    void onBindViewHolder(RecyclerView.ViewHolder holder, int position, Goods goods);
}
