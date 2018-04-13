package com.zoyi.channel.plugin.android.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.zoyi.channel.plugin.android.ChannelIO;
import com.zoyi.channel.plugin.android.ChannelPluginCompletionStatus;
import com.zoyi.channel.plugin.android.ChannelPluginSettings;
import com.zoyi.channel.plugin.android.OnBootListener;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    findViewById(R.id.veil).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkInVeil();
      }
    });

    findViewById(R.id.user).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ActivityUtils.setNextActivity(MainActivity.this, LoginActivity.class).startActivity();
      }
    });
  }

  private void checkInVeil() {
    ChannelPluginSettings pluginSettings = new ChannelPluginSettings("4be44efa-59d8-4847-990f-d5cb3e9af40f");

    ChannelIO.boot(pluginSettings, new OnBootListener() {
      @Override
      public void onCompletion(ChannelPluginCompletionStatus status) {
        switch (status) {
          case SUCCESS:
            ActivityUtils.setNextActivity(MainActivity.this, VeilActivity.class).startActivity();
            break;
          default:
            Toast.makeText(MainActivity.this, "Check in failed", Toast.LENGTH_SHORT).show();
            break;
        }
      }
    });
  }
}
