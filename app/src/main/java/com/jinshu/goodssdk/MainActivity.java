package com.jinshu.goodssdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jinshu.goodslibrary.widget.RecommendGoodsView;

public class MainActivity extends AppCompatActivity {

    private final String member_id = "041f966515f14c5f9bd4739a34dfcd2d";
    private final String shopGoodsid = "8a2f462a6dc8fcb7016e7c4f095e00a1";
    private final String NAVIGATOR_ID = "35fe48ac956d49b08068043d0b2f7064";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CartFragment fragment = CartFragment.startFragment();
//        FragmentTransaction action = getSupportFragmentManager().beginTransaction();
//        action.replace(R.id.fl_layout, fragment);
//        action.commitAllowingStateLoss();

//        GoodsDetailActivity.jumpActivity(this, shopGoodsid, member_id);

        RecommendGoodsView goodsView = findViewById(R.id.goods_view);
        goodsView.setNavigatorID(NAVIGATOR_ID);
        goodsView.setActivity(this);
//        goodsView.setShowNumber(10);

//        JumpUtils.jumpActivity(MainActivity.this, HotGoodsActivity.class);

    }
}
