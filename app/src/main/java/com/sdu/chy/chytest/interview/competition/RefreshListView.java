package com.sdu.chy.chytest.interview.competition;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdu.chy.chytest.R;

public class RefreshListView extends ListView implements AbsListView.OnScrollListener {

    private LayoutInflater inflater;

    private View mHeaderView;           // 头布局
    private ImageView mArrowView;       // 箭头视图
    private TextView mTitleText;        // 标题文本
    private ProgressBar pb;             // 进度条

    // 头布局实现下拉刷新
    private int paddingTop;             // 头布局的内边距（状态切换的依据）
    private int headerViewMeasureHeight;// 头布局的高度
    private float downY;                // 按下时的y坐标
    private float moveY;                // 移动时的y坐标

    private int currentState = 0;       // 当前刷新模式，初始为下拉刷新模式
    // 定义默认刷新模式
    public static final int PULL_TO_REFRESH = 0;    // 下拉刷新模式
    public static final int RELEASE_REFRESH = 1;    // 释放刷新模式
    public static final int REFRESHING = 2;         // 正在刷新模式

    private View mFooterView;           // 脚布局
    private TextView mLoadText;         // 加载文本

    // 脚布局实现上拉加载
    private int footerViewMeasureHeight;// 脚布局的高度

    private boolean isLoadingMore = false;      // 加载状态

    // 回调接口，实现通知数据更新与回调
    RefreshingListener refreshingListener;

    public RefreshListView(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    public void init(){
        initHeaderView();
        initFooterView();
        setOnScrollListener(this);
    }

    public void initHeaderView(){
        // 1. 自定义头部布局 & 获取
        // View.inflate() & LayoutInflater.inflate()
        // View.inflate()是封装了LayoutInflater的inflate()方法，由于是静态方法，比较简便；
        // LayoutInflater相当于比View多了一个三个参数的inflate()方法，功能更强大
        // mHeaderView = View.inflate(getContext(),R.layout.layout_header_list);
        mHeaderView = inflater.inflate(R.layout.layout_header_list,null);
        mTitleText = (TextView)mHeaderView.findViewById(R.id.header_title_text);

        // 2. 默认隐藏头部布局
        // 设置内边距，隐藏当前高度 paddingTop = -自身高度
        mHeaderView.measure(0,0);   // 按照设置的规格测量高度
        headerViewMeasureHeight = mHeaderView.getMeasuredHeight();
        mHeaderView.setPadding(0, -headerViewMeasureHeight,0,0);

        // 3. 添加头部布局
        addHeaderView(mHeaderView);
    }

    public void initFooterView(){
        mFooterView = inflater.inflate(R.layout.layout_footer_list,null);
        mLoadText = (TextView)mFooterView.findViewById(R.id.footer_title_text);
        mFooterView.measure(0,0);
        footerViewMeasureHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0,-footerViewMeasureHeight,0,0);

        addFooterView(mFooterView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev){
        // 处理触摸事件，ListView下拉时，修改paddingTop显示头部布局
        // 根据滑动距离，给HeaderView设置PaddingTop
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 向下移动的偏移量
                moveY = ev.getY();
                float offsetY = moveY - downY;
                // 处于正在刷新的状态，则不处理滑动事件
                if(currentState == REFRESHING)return super.onTouchEvent(ev);

                if(offsetY>0 && getFirstVisiblePosition() == 0){
                    // 头布局偏移距离计算 & 更新头布局
                    paddingTop = (int)(offsetY - headerViewMeasureHeight);
                    mHeaderView.setPadding(0, paddingTop,0,0);
                    if(paddingTop >= 0 && currentState != RELEASE_REFRESH){
                        // 完全显示 => 释放刷新
                        currentState = RELEASE_REFRESH;
                        updateHeader();
                    }else if(paddingTop < 0 && currentState != PULL_TO_REFRESH){
                        // 不完全显示 => 下拉刷新
                        currentState = PULL_TO_REFRESH;
                        updateHeader();
                    }
                    // 向下滑动 => 用户想下拉刷新
                    // 事件被消费，滑动事件不会传递给子View（子事件覆盖滑动事件）
                    return true;
                }
            case MotionEvent.ACTION_UP:
                if(currentState == PULL_TO_REFRESH){
                    // 若为下拉刷新状态，则抬起时恢复初始视图
                    mHeaderView.setPadding(0,-headerViewMeasureHeight,0,0);
                }else if(currentState == RELEASE_REFRESH){
                    // 若为释放刷新状态，则抬起时进行刷新
                    // 面向接口，利用接口通知Activity进行数据刷新
                    currentState = REFRESHING;
                    updateHeader();
                    if(refreshingListener!=null)
                        refreshingListener.refresh();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void updateHeader(){
        switch (currentState){
            case PULL_TO_REFRESH:
                mTitleText.setText("下拉刷新");
                break;
            case RELEASE_REFRESH:
                mTitleText.setText("释放刷新");
                break;
            case REFRESHING:
                mTitleText.setText("正在刷新");
                break;
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // 实现 OnScrollListener 接口
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // 实现 OnScrollListener 接口
        Log.i("scrollState", "onScrollStateChanged: " + scrollState);
        if(isLoadingMore)return;
        if(scrollState == SCROLL_STATE_IDLE && getLastVisiblePosition() >= (getCount()-1)){
            // 滚动到最后一个数据
            isLoadingMore = true;
            mLoadText.setText("正在加载");
            mFooterView.setPadding(0,0,0,0);
            setSelection(getCount());   // 移动到最后一个数据
            if(refreshingListener != null)refreshingListener.load();
        }
    }

    public void onRefreshComplete(){
        // Activity 刷新完毕，通知RefreshListView恢复视图
        mHeaderView.setPadding(0,-headerViewMeasureHeight,0,0);
        currentState = PULL_TO_REFRESH;
        updateHeader();
    }

    public void onLoadComplete(){
        // Activity 刷新完毕，通知RefreshListView恢复视图
        mFooterView.setPadding(0,-footerViewMeasureHeight,0,0);
        isLoadingMore = false;
    }

    public interface RefreshingListener{
        // 面向接口编程，调用回调函数
        void refresh();
        void load();
    }

    public void setRefreshListener(RefreshingListener refreshingListener){
        // 为列表设置监听器，监听数据变化
        this.refreshingListener = refreshingListener;
    }
}
