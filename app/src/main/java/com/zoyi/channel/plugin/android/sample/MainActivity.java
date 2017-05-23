package com.zoyi.channel.plugin.android.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.zoyi.channel.plugin.android.ChannelException;
import com.zoyi.channel.plugin.android.ChannelPlugin;
import com.zoyi.channel.plugin.android.OnCheckInListener;

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
    ChannelPlugin.checkIn(new OnCheckInListener() {
      @Override
      public void onSuccessed() {
        ActivityUtils.setNextActivity(MainActivity.this, VeilActivity.class).startActivity();
      }

      @Override
      public void onFailed(ChannelException ex) {
        Toast.makeText(MainActivity.this, "Check in failed", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
