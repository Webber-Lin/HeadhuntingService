package com.linzhaowei.headhuntingservice.fragment;




import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.linzhaowei.headhuntingservice.ECChat;
import com.linzhaowei.headhuntingservice.R;


public class MessageFragment extends EaseConversationListFragment{


    @Override
    protected void initView() {
        super.initView();
        final View view = (LinearLayout) View.inflate(getActivity(),R.layout.activity_message_fragment, null);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        hideTitleBar();

    }

    public MessageFragment(){
        setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                startActivity(new Intent(getActivity(), ECChat.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()));
            }
        });
    }


}