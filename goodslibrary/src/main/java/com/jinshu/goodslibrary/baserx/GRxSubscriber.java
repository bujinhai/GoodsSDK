package com.jinshu.goodslibrary.baserx;

import android.app.Activity;
import android.content.Context;

import com.jinshu.goodslibrary.R;
import com.jinshu.goodslibrary.baseapp.GBaseSdk;
import com.jinshu.goodslibrary.utils.GNetUtils;
import com.jinshu.goodslibrary.widget.GLoadingDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * des:订阅封装
 * Created by xsf
 * on 2016.09.10:16
 */

/********************使用例子********************/
/*_apiService.login(mobile, verifyCode)
        .//省略
        .subscribe(new GRxSubscriber<User user>(mContext,false) {
@Override
public void _onNext(User user) {
        // 处理user
        }

@Override
public void _onError(String msg) {
        ToastUtil.showShort(mActivity, msg);
        });*/
public abstract class GRxSubscriber<T> implements Observer<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = true;
    }

    public GRxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
    }

    public GRxSubscriber(Context context) {
        this(context, GBaseSdk.getAppContext().getString(R.string.g_loading), true);
    }

    public GRxSubscriber(Context context, boolean showDialog) {
        this(context, GBaseSdk.getAppContext().getString(R.string.g_loading), showDialog);
    }

    @Override
    public void onComplete() {
        if (showDialog)
            GLoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (showDialog) {
            try {
                GLoadingDialog.showDialogForLoading((Activity) mContext, msg, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
        if (showDialog)
            GLoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog) {
            GLoadingDialog.cancelDialogForLoading();
        }
        e.printStackTrace();
        if (!GNetUtils.isNetConnected(GBaseSdk.getAppContext())) {
            //网络
            _onError(GBaseSdk.getAppContext().getString(R.string.g_no_net));
        } else if (e instanceof HttpException) {
            _onError("服务器异常");
        } else if (e instanceof RuntimeException) {
            _onError(e.getMessage());
        } else {
            //其它
            _onError(GBaseSdk.getAppContext().getString(R.string.g_net_error));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
