package com.zoyi.channel.plugin.android.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.zoyi.channel.plugin.android.ChannelIO;
import com.zoyi.channel.plugin.android.ChannelPluginListener;
import com.zoyi.channel.plugin.android.model.etc.PushEvent;
import com.zoyi.channel.plugin.android.push.ChannelPushClient;

/**
 * Created by mika on 2017. 2. 16..
 */
public class UserActivity extends AppCompatActivity implements ChannelPluginListener {

  private static final String TAG = UserActivity.class.getSimpleName();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    ChannelIO.addChannelPluginListener(this);
    ChannelPushClient.handlePushNotification(this);
  }

  @Override
  protected void onDestroy() {
    ChannelIO.removeChannelPluginListener(this);
    ChannelIO.shutdown();
    Toast.makeText(this, "Check out (user)", Toast.LENGTH_SHORT).show();
    super.onDestroy();
  }

  @Override
  public void willShowMessenger() {

  }

  @Override
  public void willHideMessenger() {

  }

  @Override
  public void onChangeBadge(int count) {
    Log.i(TAG, "Badge Changed count : " + count);
  }

  @Override
  public void onReceivePush(PushEvent pushEvent) {
    Log.i(TAG, "UserChat Id : " + pushEvent.getChatId());
    Log.i(TAG, "Name : " + pushEvent.getSenderName());
    Log.i(TAG, "Message : " + pushEvent.getMessage());
    Log.i(TAG, "Avatar Url : " + pushEvent.getSenderAvatarUrl());
  }

  @Override
  public boolean onClickChatLink(String url) {
    return false;
  }
}
