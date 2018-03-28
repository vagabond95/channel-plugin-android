package com.zoyi.channel.plugin.android.sample;

import android.app.Application;
import com.zoyi.channel.plugin.android.ChannelPlugin;

/**
 * Created by mika on 2017. 1. 17..
 */
public class App extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    ChannelPlugin.initialize(this, "4be44efa-59d8-4847-990f-d5cb3e9af40f");
  }
}
