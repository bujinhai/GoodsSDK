package com.jinshu.goodslibrary.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.adapter.HotGoodsAdapter;
import com.jinshu.goodslibrary.api.GApi;
import com.jinshu.goodslibrary.api.GHostType;
import com.jinshu.goodslibrary.base.GBaseActivity;
import com.jinshu.goodslibrary.baserx.GRxHelper;
import com.jinshu.goodslibrary.baserx.GRxSchedulers;
import com.jinshu.goodslibrary.baserx.GRxSubscriber;
import com.jinshu.goodslibrary.entity.GoodsEntity;
import com.jinshu.goodslibrary.entity.GoodsInfo;
import com.jinshu.goodslibrary.recyclerview.animation.ScaleInAnimation;
import com.jinshu.goodslibrary.recyclerview.irc.IRecyclerView;
import com.jinshu.goodslibrary.recyclerview.irc.OnItemClickListener;
import com.jinshu.goodslibrary.recyclerview.irc.OnLoadMoreListener;
import com.jinshu.goodslibrary.recyclerview.irc.OnRefreshListener;
import com.jinshu.goodslibrary.recyclerview.widget.LoadMoreFooterView;
import com.jinshu.goodslibrary.utils.BaseUtils;
import com.jinshu.goodslibrary.widget.GLoadingTip;

import io.reactivex.disposables.Disposable;

/**
 * Create on 2019/11/21 10:32 by bll
 */


public class HotGoodsActivity extends GBaseActivity implements OnRefreshListener, OnLoadMoreListener {

    private ImageView ivBack;

    private int currentPage = 1;
    private int pageNumber = 10;

    private IRecyclerView mIrc;
    private HotGoodsAdapter mAdapter;
    private GLoadingTip mLoadingTip;

    private final String navigatorID = "35fe48ac956d49b08068043d0b2f7064";

    @Override
    public int getLayoutId() {
        return R.layout.g_activity_hot_goods;
    }

    @Override
    public void initView(Intent intent, @Nullable Bundle savedInstanceState) {
        TextView tvTitle = findViewById(R.id.tv_title);
        ivBack = findViewById(R.id.iv_left_image);

        mIrc = findViewById(R.id.irc);
        mLoadingTip = findViewById(R.id.loadedTip);

        tvTitle.setText("热卖商品");
        mIrc.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HotGoodsAdapter(this, R.layout.g_adapter_item_hot_goods);
        mAdapter.openLoadAnimation(new ScaleInAnimation());
        mIrc.setAdapter(mAdapter);
        mIrc.setOnRefreshListener(this);
        mIrc.setOnLoadMoreListener(this);

        setListener();
    }

    @Override
    protected void onInitData(Intent intent, @Nullable Bundle savedInstanceState) {
        getGoodsRecommendList(true);
    }

    private void getGoodsRecommendList(final boolean isRefresh) {
        GApi.getDefault(GHostType.BASE_URL)
                .getGoodsRecommendList(BaseUtils.getSessionID(), navigatorID, currentPage, pageNumber)
                .compose(GRxHelper.<GoodsEntity>handleResult())
                .compose(GRxSchedulers.<GoodsEntity>io_main())
                .subscribe(new GRxSubscriber<GoodsEntity>(mContext, false) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        if (isRefresh) {
                            mLoadingTip.setLoadingTip(GLoadingTip.LoadStatus.loading);
                        }
                    }

                    @Override
                    protected void _onNext(GoodsEntity goodsEntity) {
                        if (goodsEntity.getData() == null || goodsEntity.getData().getRows() == null) {
                            return;
                        }
                        if (isRefresh) {
                            if (goodsEntity.getData().getRows().size() == 0) {
                                mLoadingTip.setLoadingTip(GLoadingTip.LoadStatus.empty);
                                return;
                            }
                        }
                        currentPage++;
                        mLoadingTip.setLoadingTip(GLoadingTip.LoadStatus.finish);
                        if (mAdapter.getPageBean().isRefresh()) {
                            mIrc.setRefreshing(false);
                            mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                        } else {
                            if (goodsEntity.getData().getRows().size() > 0) {
                                mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                            } else {
                                mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                            }
                        }
                        if (isRefresh) {
                            mAdapter.replaceAll(goodsEntity.getData().getRows());
                        } else {
                            mAdapter.addAll(goodsEntity.getData().getRows());
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        mLoadingTip.setLoadingTip(GLoadingTip.LoadStatus.error);
                    }
                });
    }

    private void setListener() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                GoodsInfo info = (GoodsInfo) o;
                GoodsDetailActivity.jumpActivity(mActivity, info.getGoodsShopID(), BaseUtils.getMemberID());
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        mAdapter.getPageBean().setRefresh(true);
        mIrc.setRefreshing(true);
        getGoodsRecommendList(true);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        mAdapter.getPageBean().setRefresh(false);
        mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.LOADING);
        getGoodsRecommendList(false);
    }
}
