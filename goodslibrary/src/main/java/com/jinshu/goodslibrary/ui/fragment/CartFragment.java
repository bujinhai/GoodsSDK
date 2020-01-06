package com.jinshu.goodslibrary.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.adapter.ShoppingCartAdapter;
import com.jinshu.goodslibrary.api.GApi;
import com.jinshu.goodslibrary.api.GHostType;
import com.jinshu.goodslibrary.base.GBaseFragment;
import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.jinshu.goodslibrary.basebean.GBaseResponse;
import com.jinshu.goodslibrary.baserx.GRxHelper;
import com.jinshu.goodslibrary.baserx.GRxSchedulers;
import com.jinshu.goodslibrary.baserx.GRxSubscriber;
import com.jinshu.goodslibrary.entity.CartGoodsEntity;
import com.jinshu.goodslibrary.entity.CartGoodsInfo;
import com.jinshu.goodslibrary.entity.Configure;
import com.jinshu.goodslibrary.recyclerview.animation.ScaleInAnimation;
import com.jinshu.goodslibrary.recyclerview.irc.IRecyclerView;
import com.jinshu.goodslibrary.recyclerview.irc.OnRefreshListener;
import com.jinshu.goodslibrary.recyclerview.widget.LoadMoreFooterView;
import com.jinshu.goodslibrary.ui.activity.OrderActivity;
import com.jinshu.goodslibrary.utils.BaseUtils;
import com.jinshu.goodslibrary.utils.JumpUtils;
import com.jinshu.goodslibrary.utils.StrUtils;
import com.jinshu.goodslibrary.utils.ToastUtil;
import com.jinshu.goodslibrary.widget.GAlertDialog;
import com.jinshu.goodslibrary.widget.GLoadingTip;

import io.reactivex.functions.Consumer;


/**
 * Create on 2019/9/30 16:34 by bll
 * 购物车
 */


public class CartFragment extends GBaseFragment implements OnRefreshListener, View.OnClickListener {

    private IRecyclerView mIrc;
    private GLoadingTip mLoadingTip;
    private ImageView ivAllSelect, ivBack;
    private TextView tvGoodsNumber, tvTitle;
    private TextView tvTotalPrice;
    private LinearLayout ll_all_select;
    private Button btnBalance;

    private ShoppingCartAdapter mAdapter;
    private int currentPage = 1;
    private int pageNumber = 1000;
    private boolean isAllOpt = true;
    private int count = 1;
    private int tally;

    @Override
    protected Object getLayoutResource() {
        return R.layout.g_fragment_cart;
    }

    public static CartFragment startFragment() {
        return new CartFragment();
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        ivBack = bindView(R.id.iv_left_image);
        tvTitle = bindView(R.id.tv_title);
        mIrc = bindView(R.id.irc);
        mLoadingTip = bindView(R.id.loadedTip);
        ivAllSelect = bindView(R.id.iv_all_select);
        tvGoodsNumber = bindView(R.id.tv_goods_number);
        tvTotalPrice = bindView(R.id.tv_total_price);
        ll_all_select = bindView(R.id.ll_all_select);
        btnBalance = bindView(R.id.btn_balance);

        mAdapter = new ShoppingCartAdapter(mContext, R.layout.g_adapter_item_cart);
        mAdapter.openLoadAnimation(new ScaleInAnimation());
        mIrc.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mIrc.setLayoutManager(new LinearLayoutManager(mContext));
        mIrc.setAdapter(mAdapter);
        mIrc.setOnRefreshListener(this);

        ll_all_select.setOnClickListener(this);
        btnBalance.setOnClickListener(this);

        tvTitle.setText("购物车");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        setListener();
    }

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        getCartGoodsList(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        mRxManager.on(GAppConstant.HAS_CREATE_ORDER, new Consumer<Object>() {

            @Override
            public void accept(Object o) throws Exception {
                getCartGoodsList(true);
            }
        });
    }

    private void getCartGoodsList(final boolean isRefresh) {
        GApi.getDefault(GHostType.BASE_CART_URL)
                .getCartGoodsList(BaseUtils.getSessionID(), currentPage, pageNumber)
                .compose(GRxHelper.<CartGoodsEntity>handleResult())
                .compose(GRxSchedulers.<CartGoodsEntity>io_main())
                .subscribe(new GRxSubscriber<CartGoodsEntity>(mContext, false) {

                    @Override
                    protected void _onNext(CartGoodsEntity entity) {
                        if (entity.getData() == null) {
                            return;
                        }

                        if (isRefresh) {
                            if (entity.getData().size() == 0) {
                                mLoadingTip.setLoadingTip(GLoadingTip.LoadStatus.empty);
                                return;
                            }
                        }

                        if (mAdapter.getPageBean().isRefresh()) {
                            mIrc.setRefreshing(false);
                            mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                        } else {
                            if (entity.getData().size() > 0) {
                                mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.GONE);
                            } else {
                                mIrc.setLoadMoreStatus(LoadMoreFooterView.Status.THE_END);
                            }
                        }
                        if (isRefresh) {
                            mAdapter.replaceAll(entity.getData().get(0).getGoodsList());
                        } else {
                            mAdapter.addAll(entity.getData().get(0).getGoodsList());
                        }

                        tally = 0;
                        double total = 0;
                        for (CartGoodsInfo goodsInfo : entity.getData().get(0).getGoodsList()) {
                            if (goodsInfo.getIsSelected() == 1) {
                                tally++;
                                total += goodsInfo.getPriceTotal();
                            }
                        }
                        tvTotalPrice.setText(StrUtils.format(total));

                        if (tally == entity.getData().get(0).getGoodsList().size()) {
                            isAllOpt = true;
                            ivAllSelect.setBackgroundResource(R.drawable.g_select);
                        } else {
                            isAllOpt = false;
                            ivAllSelect.setBackgroundResource(R.drawable.g_unselect);
                        }
                        String str = "已选(" + tally + ")";
                        tvGoodsNumber.setText(str);
                    }

                    @Override
                    protected void _onError(String message) {
                        if (message.equals("购物车商品为空")) {
                            mLoadingTip.setLoadingTip(GLoadingTip.LoadStatus.empty);
                            return;
                        }
                        mLoadingTip.setLoadingTip(GLoadingTip.LoadStatus.error);
                    }
                });
    }

    private void setListener() {
        mLoadingTip.setOnReloadListener(new GLoadingTip.onReloadListener() {
            @Override
            public void reload() {
                getCartGoodsList(true);
            }
        });

        mAdapter.setOnCartItemClickListener(new ShoppingCartAdapter.OnCartItemClickListener() {
            @Override
            public void onSelect(CartGoodsInfo info) {
                if (info.getIsSelected() == 1) {
                    selectOneGoods(info, 2);
                } else {
                    selectOneGoods(info, 1);
                }
            }

            @Override
            public void onAdd(CartGoodsInfo info) {
                count = info.getQty();
                count++;
                updateCartGoodsNumber(info, count);
            }

            @Override
            public void onMinus(CartGoodsInfo info) {
                count = info.getQty();
                count--;
                if (count < 1) {
                    ToastUtil.showShort("商品不能少于1");
                    return;
                }
                updateCartGoodsNumber(info, count);
            }

            @Override
            public void onDelete(final CartGoodsInfo info) {
                if (info.getIsSelected() == 1) {
                    ToastUtil.showShort("此商品已选中，不能删除");
                    return;
                }
                GAlertDialog dialog = new GAlertDialog(mContext, "提示", "", "确定", "取消",
                        new GAlertDialog.OnDialogButtonClickListener() {
                            @Override
                            public void onDialogButtonClick(boolean isPositive) {
                                if (isPositive) {
                                    removeCartGoods(info);
                                }
                            }
                        });
                dialog.show();
            }
        });
    }

    private void selectOneGoods(CartGoodsInfo info, int isSelect) {
        selectCartGoods(info.getId(), isSelect);
    }

    private void updateCartGoodsNumber(CartGoodsInfo info, int count) {
        GApi.getDefault(GHostType.BASE_CART_URL)
                .updateCartGoodsNumber(BaseUtils.getSessionID(), info.getId(), count)
                .compose(GRxSchedulers.<GBaseResponse>io_main())
                .subscribe(new GRxSubscriber<GBaseResponse>(mContext, false) {
                    @Override
                    protected void _onNext(GBaseResponse gBaseResponse) {
                        getCartGoodsList(true);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    private void removeCartGoods(final CartGoodsInfo info) {
        GApi.getDefault(GHostType.BASE_CART_URL)
                .removeCartGoods(BaseUtils.getSessionID(), info.getId())
                .compose(GRxSchedulers.<GBaseResponse>io_main())
                .subscribe(new GRxSubscriber<GBaseResponse>(mContext, false) {
                    @Override
                    protected void _onNext(GBaseResponse baseResponse) {
                        if (baseResponse.header.code == 0) {
                            mAdapter.remove(info);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }


    @Override
    public void onRefresh() {
        mAdapter.getPageBean().setRefresh(true);
        mIrc.setRefreshing(true);
        currentPage = 1;
        getCartGoodsList(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_all_select) {
            if (isAllOpt) {
                selectAllGoods(2);
                ivAllSelect.setBackgroundResource(R.drawable.g_unselect);
                isAllOpt = false;
            } else {
                selectAllGoods(1);
                ivAllSelect.setBackgroundResource(R.drawable.g_select);
                isAllOpt = true;
            }
        } else if (v.getId() == R.id.btn_balance) {
            if (tally == 0) {
                ToastUtil.showShort("请选择商品");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString(GAppConstant.ORDER_TYPE, Configure.FROM_CART.name());
            JumpUtils.jumpActivity(getActivity(), OrderActivity.class, bundle);
        }
    }

    private void selectAllGoods(int isSelect) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mAdapter.getSize(); i++) {
            if (i == mAdapter.getSize() - 1) {
                sb.append(mAdapter.getAll().get(i).getId());
            } else {
                sb.append(mAdapter.getAll().get(i).getId() + ",");
            }
        }
        selectCartGoods(sb.toString(), isSelect);
    }

    private void selectCartGoods(String cardID, int isSelect) {
        GApi.getDefault(GHostType.BASE_CART_URL)
                .selectCartGoods(BaseUtils.getSessionID(), cardID, isSelect)
                .compose(GRxSchedulers.<GBaseResponse>io_main())
                .subscribe(new GRxSubscriber<GBaseResponse>(mContext, false) {
                    @Override
                    protected void _onNext(GBaseResponse baseResponse) {
                        if (baseResponse.header.code != 0) {
                            ToastUtil.showShort(baseResponse.header.msg);
                        }

                        getCartGoodsList(true);
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }
}
