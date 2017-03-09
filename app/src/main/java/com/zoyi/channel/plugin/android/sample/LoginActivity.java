package com.zoyi.channel.plugin.android.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.zoyi.channel.plugin.android.ChannelException;
import com.zoyi.channel.plugin.android.ChannelPlugin;
import com.zoyi.channel.plugin.android.CheckIn;
import com.zoyi.channel.plugin.android.OnCheckInListener;

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

    CheckIn checkIn = CheckIn.create()
        .withUserId(id)
        .withName(name)
        .withMobileNumber(phoneNumber)
        .withMeta("App id", APP_ID);

    ChannelPlugin.checkIn(checkIn, new OnCheckInListener() {
      @Override
      public void onSuccessed() {
        ActivityUtils.setNextActivity(LoginActivity.this, UserActivity.class).startActivity();
      }

      @Override
      public void onFailed(ChannelException ex) {
        Toast.makeText(LoginActivity.this, "Check in failed", Toast.LENGTH_SHORT).show();
      }
    });
  }
}
