package com.jinshu.goodslibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jinshu.goodslibrary.baserx.GRxManager;
import com.jinshu.goodslibrary.utils.ToastUtil;


/**
 * des:基类fragment
 * Created by bll
 */

public abstract class GBaseFragment extends Fragment {
    protected View rootView;
    protected Context mContext;
    public GRxManager mRxManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            if (getLayoutResource() instanceof Integer) {
                rootView = inflater.inflate((int) getLayoutResource(), container, false);
            } else if (getLayoutResource() instanceof View) {
                rootView = (View) getLayoutResource();
            }
        }
        mContext = getContext();
        mRxManager = new GRxManager();
        initView(savedInstanceState);
        initData(savedInstanceState);
        return rootView;
    }

    /**
     * 获取布局文件
     */
    protected abstract Object getLayoutResource();


    /**
     * 初始化view
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 请求数据
     */
    protected void initData(@Nullable Bundle savedInstanceState) {
    }

    protected <T extends View> T bindView(int nResId) {
        return (T) rootView.findViewById(nResId);
    }

    protected <T extends View> T bindView(View pView, int nResId) {
        return (T) pView.findViewById(nResId);
    }


    @Nullable
    public FragmentManager getChildSafeFragmentManager() {
        if (this.getContext() == null) {
            return null;
        }
        return this.getChildFragmentManager();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRxManager.clear();
        ToastUtil.cancelToast();
    }

    public void replaceFragment(int layoutId, GBaseFragment fragment) {
        if (getChildSafeFragmentManager() == null) {
            return;
        }
        FragmentTransaction transaction = getChildSafeFragmentManager().beginTransaction();
        transaction.replace(layoutId, fragment);
        transaction.commit();
    }
}
