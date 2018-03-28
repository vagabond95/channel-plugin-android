package com.zoyi.channel.plugin.android.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.zoyi.channel.plugin.android.ChannelPlugin;
import com.zoyi.channel.plugin.android.OnChannelPluginChangedListener;
import com.zoyi.channel.plugin.android.model.etc.PushEvent;
import com.zoyi.channel.plugin.android.push.ChannelPushClient;

/**
 * Created by mika on 2017. 2. 16..
 */
public class UserActivity extends AppCompatActivity implements OnChannelPluginChangedListener {

  private static final String TAG = UserActivity.class.getSimpleName();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    ChannelPlugin.addOnChannelPluginChangedListener(this);

    ChannelPushClient.handlePushMessage(this);
  }

  @Override
  protected void onDestroy() {
    ChannelPlugin.removeOnChannelPluginChangedListener(this);
    ChannelPlugin.checkOut();
    Toast.makeText(this, "Check out (user)", Toast.LENGTH_SHORT).show();
    super.onDestroy();
  }

  @Override
  public void badgeChanged(int count) {
    Log.i(TAG, "Badge Changed count : " + count);
  }

  @Override
  public void onReceivedPushMessage(PushEvent pushEvent) {
    Log.i(TAG, "UserChat Id : " + pushEvent.getChatId());
    Log.i(TAG, "Name : " + pushEvent.getName());
    Log.i(TAG, "Message : " + pushEvent.getMessage());
    Log.i(TAG, "Avatar Url : " + pushEvent.getAvatarUrl());
  }

  @Override
  public void willShowChatList() {
    Log.i(TAG, "willShowChatList()");
  }

  @Override
  public void willHideChatList() {
    Log.i(TAG, "willHideChatList()");
  }
}
