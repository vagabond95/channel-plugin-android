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
    ChannelPlugin.initialize(this, "faa73a7c-4158-4a38-9a7a-793a969fd5bc");
  }
}
