package com.zoyi.channel.plugin.android.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.zoyi.channel.plugin.android.ChannelPlugin;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
  View layoutVeil, layoutUser;
  EditText id, name, phone;
  Button login, guest, logout, launch;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    layoutVeil = findViewById(R.id.layout_veil);
    layoutUser = findViewById(R.id.layout_user);

    id = (EditText) findViewById(R.id.id);
    name = (EditText) findViewById(R.id.name);
    phone = (EditText) findViewById(R.id.phone);

    login = (Button) findViewById(R.id.login);
    guest = (Button) findViewById(R.id.login_guest);
    logout = (Button) findViewById(R.id.logout);
    launch = (Button) findViewById(R.id.launch);

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkInUser();
      }
    });

    guest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkInGuest();
      }
    });

    logout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkInGuest();
      }
    });

    launch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ChannelPlugin.launch(MainActivity.this);
      }
    });
  }

  private void checkInGuest() {
    ChannelPlugin.checkOut();

    layoutVeil.setVisibility(View.GONE);
    layoutUser.setVisibility(View.GONE);
    launch.setVisibility(View.GONE);

    setTitle("Try to check in guest");

    ChannelPlugin.checkIn(new CheckInCallback() {
      @Override
      public void finish(boolean success) {
        if (success) {
          setTitle("Checked in (guest)");
          layoutVeil.setVisibility(View.VISIBLE);
          launch.setVisibility(View.VISIBLE);
        } else {
          setTitle("Please check in");
          layoutVeil.setVisibility(View.VISIBLE);
        }
      }
    });
  }

  private void checkInUser() {
    ChannelPlugin.checkOut();

    layoutVeil.setVisibility(View.GONE);
    layoutUser.setVisibility(View.GONE);
    launch.setVisibility(View.GONE);

    setTitle("Try to check in user");

    Map<String, String> meta = new HashMap<>();
    meta.put("name", name.getText().toString());
    meta.put("mobileNumber", phone.getText().toString());

    meta.put("temp", "Hello");
    meta.put("Mika", "derica");

    ChannelPlugin.checkIn(id.getText().toString(), meta, new CheckInCallback() {
      @Override
      public void finish(boolean success) {
        if (success) {
          setTitle("Checked in (user)");
          layoutUser.setVisibility(View.VISIBLE);
          launch.setVisibility(View.VISIBLE);
        } else {
          setTitle("Please check in");
          layoutVeil.setVisibility(View.VISIBLE);
        }
      }
    });
  }

  private void setTitle(String title) {
    try {
      if (getSupportActionBar() != null) {
        getSupportActionBar().setTitle(title);
      }
    } catch (Exception ex) {
    }
  }
}
