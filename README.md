# Channel plugin for Android

## Requirement

minSdkVersion &ge; 15

#### Gradle

```groovy
repositories {
  jcenter()
}

dependencies {
  compile 'com.zoyi.channel:plugin-android:$[version]'
}
```

[ ![Download](https://api.bintray.com/packages/zoyi/maven-channel/plugin-android/images/download.svg) ](https://bintray.com/zoyi/maven-channel/plugin-android/_latestVersion)

**Replace $[version] to version on top**

## Getting plugin key

[See this document's 'Getting plugin key' section](https://medium.com/channel-korea/%EC%B1%84%EB%84%90-%ED%94%8C%EB%9F%AC%EA%B7%B8%EC%9D%B8-%EA%B0%80%EC%9D%B4%EB%93%9C-01-%EC%9B%B9%EC%97%90-%EB%B6%99%EC%9D%B4%EA%B8%B0-1f6d70fefbcc#.hj5jcuyb1)

## Initialize

You must create a class that extends `Application` and then declare this in your AndroidManifest.xml.
If you already use own `Application` class, you can use that.

**Important**

Channel plugin must be initialize in `Application`'s `onCreate()` event.

If you initialize it in any other way, it will not guarantee correct operation.


```xml
<application
    android:name="com.example.MyApplication"
    ...
    >
</application>
```

```java
public class MyApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    ChannelPlugin.initialize(this, pluginKey);
    // ChannelPlugin.initialize(this, pluginKey, true); // for debug
  }
}
```

## Check in

You must check in to start chat.

There are two ways to check in.

### Check in as veil

Just call `ChannelPlugin.checkIn();` anywhere.

### Check in as user

User information is required to check in as a user.

The following information is required for check in.

- User id (Required, String)
- Name (Optional, String)
- Mobile number (Optional, String)
- Avatar url (Optional, String)
- Meta data (Optional, Map<String, String>)

Meta data is meta data showing in user information.

Just call `ChannelPlugin.checkIn(CheckIn checkIn)` and This is how to make `CheckIn` instance.

```java
CheckIn checkIn = CheckIn.create()
  .withUserId("214")
  .withName("UserName")
  .withMobileNumber("+821012345678")
  .withAvatarUrl("https://zoyi.co/images/home/ch.png")
  .withMeta("Account Level", "VIP")
  .withMeta("Last seen item", "rose")
  .withMeta("Credit", 100)
  .withMeta("isVip", true);

ChannelPlugin.checkIn(checkIn);
```

If you not set user id, it check in with veil.

#### Check in callback

You can get result of check in. Use

```java
ChannelPlugin.checkIn(OnCheckInListener listener) // for veil
```

or

```java
ChannelPlugin.checkIn(CheckIn checkIn, OnCheckInListener listener) // for user
```

```OnCheckInListener``` has two methods.

- ```onSuccess()```

- ```onFailed(ChannelException exception)```

```ChannelException.StatusCode``` show reason why check in is failed.

- NOT_INITIALIZED

- NETWORK_TIMEOUT

- NOT_AVAILABLE_VERSION

- SERVICE_UNDER_CONSTRUCTION

#### Add badge changed listner

When you make own channel button, you can use `OnChannelPluginChangedListener` and call `ChannelPlugin.addOnChannelPluginChangedListener(listener)`

When badge changed, you can receive counter from `badgeChanged(int count);`

```java
public class MyActivity extends Activity implements OnChannelPluginChangedListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    ...
    ChannelPlugin.addOnChannelPluginChangedListener(this);
    ...
  }

  @Override
  public void badgeChanged(int count) {
    // change your own badge.
  }
}
```

## Start chat

There are two ways to start a chat.

### Add channel button view to your layout

if you add `ChannelButton` in your layout, it shows automatically when check in successed.
```xml
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
  <your_view></your_view>
  <com.zoyi.channel.plugin.android.ChannelView
      xmlns:app="http://schemas.android.com/apk/res-auto"
      android:layout_width="match_parent"
      android:layout_height="match_parent"/>
</FrameLayout>
```

Place the ChannelButton view in a location that can be displayed across the screen.

#### XML attributes

| Name | Type | Default | Description |
|:----:|:----:|:-------:|:-----------:|
| channel_button_gravity | enum | bottom_right | ChannelView button's gravity (bottom_right, bottom_left, top_right, top_left) |
| channel_button_left_margin | dimension | 16dp | ChannelView button's left margin |
| channel_button_right_margin | dimension | 16dp | ChannelView button's right margin |
| channel_button_top_margin | dimension | 16dp | ChannelView button's top margin |
| channel_button_bottom_margin | dimension | 16dp | ChannelView button's bottom margin |

**Unused margins are ignored. (For example, right_margin will ignored when gravity is bottom_left)**

### Start manually

Just call `ChannelPlugin.launch(Context context);`

### Finish chat session.

Just call `ChannelPlugin.checkOut();`