package com.jinshu.goodslibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.adapter.RecommendGoodsAdapter;
import com.jinshu.goodslibrary.api.GApi;
import com.jinshu.goodslibrary.api.GHostType;
import com.jinshu.goodslibrary.baserx.GRxHelper;
import com.jinshu.goodslibrary.baserx.GRxSchedulers;
import com.jinshu.goodslibrary.baserx.GRxSubscriber;
import com.jinshu.goodslibrary.entity.GoodsEntity;
import com.jinshu.goodslibrary.entity.GoodsInfo;
import com.jinshu.goodslibrary.recyclerview.irc.OnItemClickListener;
import com.jinshu.goodslibrary.ui.activity.GoodsDetailActivity;
import com.jinshu.goodslibrary.utils.BaseUtils;
import com.jinshu.goodslibrary.utils.ToastUtil;

/**
 * Create on 2019/11/8 15:47 by bll
 */


public class RecommendGoodsView extends LinearLayout {

    private int currentPage = 1;
    private int pageNumber = 10;

    private LinearLayout llMore;
    private RecommendGoodsAdapter mAdapter;
    private Activity mActivity;
    private String memberID;
    private String navigatorID;

    private OnShopCartClickListener mListener;

    public RecommendGoodsView(Context context) {
        this(context, null);
    }

    public RecommendGoodsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecommendGoodsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    public void setActivity(Activity activity) {
        this.mActivity = activity;
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.g_recommend_goods_view, this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        llMore = view.findViewById(R.id.ll_more_goods);
        mAdapter = new RecommendGoodsAdapter(context, R.layout.g_fragment_adapter_item_goods);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        recyclerView.setAdapter(mAdapter);
        memberID = BaseUtils.getMemberID();
        setListener();
    }

    public void setNavigatorID(String navigatorID) {
        this.navigatorID = navigatorID;
        getGoodsRecommendList();
    }

    private void setListener() {
//        llMore.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                JumpUtils.jumpActivity(mActivity, ShopListActivity.class);
//            }
//        });
        GoodsDetailActivity.setOnShopCartClickListener(new GoodsDetailActivity.OnShopCartClickListener() {
            @Override
            public void onShopCartClick() {
                if (mListener != null) {
                    mListener.onShopCartClick();
                }
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                GoodsInfo info = (GoodsInfo) o;
                GoodsDetailActivity.jumpActivity(mActivity, info.getGoodsShopID(), memberID);
            }
        });
    }

    /**
     * 动态设置店铺数量
     *
     * @param num
     */
    public void setShowNumber(int num) {
        pageNumber = num;
//        getGoodsRecommendList();
    }

    private void getGoodsRecommendList() {
        GApi.getDefault(GHostType.BASE_URL)
                .getGoodsRecommendList(BaseUtils.getSessionID(), navigatorID, currentPage, pageNumber)
                .compose(GRxHelper.<GoodsEntity>handleResult())
                .compose(GRxSchedulers.<GoodsEntity>io_main())
                .subscribe(new GRxSubscriber<GoodsEntity>(getContext(), false) {
                    @Override
                    protected void _onNext(GoodsEntity shopEntity) {
                        if (shopEntity.getData() == null || shopEntity.getData().getRows() == null) {
                            return;
                        }
                        if (shopEntity.getData().getRows().size() == 0) {
                            llMore.setVisibility(GONE);
                            return;
                        }
                        mAdapter.replaceAll(shopEntity.getData().getRows());
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    public interface OnShopCartClickListener{
        void onShopCartClick();
    }

    public void setOnShopCartClickListener(OnShopCartClickListener listener) {
        mListener = listener;
    }
}
