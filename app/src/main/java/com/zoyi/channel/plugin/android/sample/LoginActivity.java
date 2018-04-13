package com.zoyi.channel.plugin.android.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.zoyi.channel.plugin.android.ChannelIO;
import com.zoyi.channel.plugin.android.ChannelPluginCompletionStatus;
import com.zoyi.channel.plugin.android.ChannelPluginSettings;
import com.zoyi.channel.plugin.android.Guest;
import com.zoyi.channel.plugin.android.OnBootListener;

/**
 * Created by mika on 2017. 2. 16..
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
  private final static int APP_ID = 100;

  private EditText editId, editName, editPhoneNumber;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    findViewById(R.id.login).setOnClickListener(this);

    editId = (EditText) findViewById(R.id.id);
    editName = (EditText) findViewById(R.id.name);
    editPhoneNumber = (EditText) findViewById(R.id.phone);
  }

  @Override
  public void onClick(View v) {
    String id = editId.getText().toString();
    String name = editName.getText().toString();
    String phoneNumber = editPhoneNumber.getText().toString();

    if (TextUtils.isEmpty(id)) {
      Toast.makeText(this, "Id cannot be null", Toast.LENGTH_SHORT).show();
      return;
    }

    ChannelPluginSettings pluginSettings = new ChannelPluginSettings("4be44efa-59d8-4847-990f-d5cb3e9af40f");

    Guest guest = Guest.create()
        .setId(id)
        .setName(name)
        .setMobileNumber(phoneNumber)
        .setProperty("App id", APP_ID);

    ChannelIO.boot(pluginSettings, guest, new OnBootListener() {
      @Override
      public void onCompletion(ChannelPluginCompletionStatus status) {
        switch (status) {
          default:
            Toast.makeText(LoginActivity.this, "Check in failed", Toast.LENGTH_SHORT).show();
            break;
        }
      }
    });
  }
}
