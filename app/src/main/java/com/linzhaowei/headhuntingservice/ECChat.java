package com.linzhaowei.headhuntingservice;

import android.os.Bundle;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;

/**
 * 聊天界面
 */

public class ECChat extends EaseBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecchat);
        EaseChatFragment chatFragment = new EaseChatFragment();
        chatFragment.setRealname(getIntent().getStringExtra("realname"));
        chatFragment.setArguments(getIntent().getExtras());



        getSupportFragmentManager().beginTransaction().add(R.id.container,chatFragment).commit();



    }


}