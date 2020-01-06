package com.jinshu.goodssdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.jinshu.goodslibrary.api.GApi;
import com.jinshu.goodslibrary.api.GHostType;
import com.jinshu.goodslibrary.base.GBaseActivity;
import com.jinshu.goodslibrary.baseapp.GAppConstant;
import com.jinshu.goodslibrary.baseapp.GBaseSdk;
import com.jinshu.goodslibrary.basebean.GBaseResponse;
import com.jinshu.goodslibrary.baserx.GRxHelper;
import com.jinshu.goodslibrary.baserx.GRxSchedulers;
import com.jinshu.goodslibrary.baserx.GRxSubscriber;
import com.jinshu.goodslibrary.entity.GUserEntity;
import com.jinshu.goodslibrary.utils.SPUtils;
import com.jinshu.goodslibrary.utils.StrUtils;
import com.jinshu.goodslibrary.utils.ToastUtil;
import com.jinshu.settinglibrary.base.baseapp.SettingBaseSDK;
import com.jinshu.settinglibrary.entity.UserData;
import com.jinshu.settinglibrary.utils.MasterUtils;
import com.jinshu.settinglibrary.utils.SystemUtils;

/**
 * Create on 2019/11/18 09:32 by bll
 */


public class LoginActivity extends GBaseActivity {

    private EditText etLoginName;
    private EditText etPsw;
    private Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Intent intent, @Nullable Bundle savedInstanceState) {
        etLoginName = findViewById(R.id.et_user_name);
        etPsw = findViewById(R.id.et_user_psw);
        btnLogin = findViewById(R.id.btn_login);

        String userName = SPUtils.getSharedStringData(GAppConstant.KEY_USER);
        String psw = SPUtils.getSharedStringData(GAppConstant.KEY_PASSWORD);
        if (StrUtils.isNotEmpty(userName) && StrUtils.isNotEmpty(psw)) {
            etLoginName.setText(userName);
            etPsw.setText(psw);
        }

        btnLogin.setOnClickListener(v -> {

            login();
        });
    }

    private void login() {
        String name = etLoginName.getText().toString();
        String psw = etPsw.getText().toString();

        GApi.getDefault(GHostType.BASE_URL)
                .login(name, psw, MasterUtils.addMasterInfo())
                .compose(GRxSchedulers.<GBaseResponse<GUserEntity>>io_main())
                .compose(GRxHelper.<GUserEntity>handleResult())
                .subscribe(new GRxSubscriber<GUserEntity>(this, "登录中...", true) {

                    @Override
                    protected void _onNext(GUserEntity entity) {
                        UserData data = changeData(entity);
                        SettingBaseSDK.getInstance().setUserEntity(data);
                        GBaseSdk.setSessionID(entity.getSessionID());
                        GBaseSdk.setMemberID(entity.getMemberID());

                        SPUtils.setSharedStringData(GAppConstant.KEY_USER, name);
                        SPUtils.setSharedStringData(GAppConstant.KEY_PASSWORD, psw);
                        SystemUtils.jumpActivity(LoginActivity.this, MainActivity.class);
                        finish();
                    }

                    @Override
                    protected void _onError(String message) {
                        ToastUtil.showShort(message);
                    }
                });
    }

    public static UserData changeData(GUserEntity entity) {
        UserData data = new UserData();
        data.setAvatarURL(entity.getAvatarURL());
        data.setLoginName(entity.getLoginName());
        data.setMajorID(entity.getMajorID());
        data.setMajorName(entity.getMajorName());
        data.setMemberID(entity.getMemberID());
        data.setName(entity.getName());
        data.setPhone(entity.getPhone());
        data.setRankID(entity.getRankID());
        data.setRankName(entity.getRankName());
        data.setRecommandCode(entity.getRecommandCode());
        data.setSessionID(entity.getSessionID());
        data.setShortName(entity.getShortName());
        data.setWeixinToken(entity.getWeixinToken());
        return data;
    }
}
