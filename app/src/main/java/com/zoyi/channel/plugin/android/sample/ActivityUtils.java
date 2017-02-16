package com.zoyi.channel.plugin.android.sample;

import android.content.Context;
import android.content.Intent;

/**
 * Created by mika on 2017. 2. 16..
 */
public class ActivityUtils {
  private Context context;
  private Intent intent;

  public static ActivityUtils setNextActivity(Context context, Class<?> clazz) {
    ActivityUtils intentUtils = new ActivityUtils();
    intentUtils.context = context;
    intentUtils.intent = new Intent(context, clazz);
    return intentUtils;
  }

  public void startActivity() {
    if (context != null && intent != null) {
      try {
        context.startActivity(intent);
      } catch (Exception ex) {
      }
    }
  }
}