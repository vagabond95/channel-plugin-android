package com.zoyi.channel.plugin.android.sample;

import android.app.Application;
import com.zoyi.channel.plugin.android.ChannelIO;

/**
 * Created by mika on 2017. 1. 17..
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    ChannelIO.initialize(this);
  }
}
