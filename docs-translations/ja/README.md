# Channel plugin for Android

## 요구사항

minSdkVersion &ge; 15

## 설치 방법

채널 플러그인을 안드로이드 앱에서 사용하는 방법은 두 가지가 있습니다.

#### 방법 1: 채널 플러그인을 Firebase Cloud Message(FCM)과 함께 사용

푸시 알림을 사용하기 위한 [FCM 가이드](../../fcm-guide/ja)는 여기를 참고하세요.

```groovy
repositories {
  jcenter()
}

dependencies {
  compile 'com.zoyi.channel:plugin-android:$[version]'
  compile 'com.zoyi.channel:plugin-android-fcm:$[version]'
}
```

#### 방법 2: Firebase Cloud Message(FCM)은 사용하지 않고 채널 플러그인만 사용
```groovy
repositories {
  jcenter()
}

dependencies {
  compile 'com.zoyi.channel:plugin-android:$[version]'
}
```

[ ![Download](https://api.bintray.com/packages/zoyi/maven-channel/plugin-android/images/download.svg) ](https://bintray.com/zoyi/maven-channel/plugin-android/_latestVersion)

**$[version]을 위의 버전으로 변경합니다**

## 플러그인 키를 얻는 방법

[플러그인 키를 얻는 방법은 여기를 참고하세요](https://medium.com/channel-korea/%EC%B1%84%EB%84%90-%ED%94%8C%EB%9F%AC%EA%B7%B8%EC%9D%B8-%EA%B0%80%EC%9D%B4%EB%93%9C-01-%EC%9B%B9%EC%97%90-%EB%B6%99%EC%9D%B4%EA%B8%B0-1f6d70fefbcc#.hj5jcuyb1)

## Initialize

`Application`을 상속한 클래스를 만들고 AndroidManifest.xml에 그 클래스를에 추가해야 합니다.
만약 이미 `Application`을 상속한 클래스가 있다면 그것을 사용해도 됩니다.

또한, 파일 공유 설정을 위해 `ChannelFileProvider`를 AndroidManifest.xml에 추가해야 합니다. 관련 문서는 [여기](https://developer.android.com/training/secure-file-sharing/setup-sharing.html)를 참고하세요.

**중요**

채널 플러그인을 사용하기 위해서는 `Application`의 `onCreate()`에서 초기화를 해야 합니다.

만약 초기화 하지 않으면 정상적으로 작동하지 않게 됩니다.

```xml
<application
    android:name="com.example.MyApplication"
    ...
    >
    
    ...
    <provider 
        android:name="com.zoyi.channel.plugin.android.global.ChannelFileProvider"
        android:authorities="your.package.ch_provider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
          android:name="android.support.FILE_PROVIDER_PATHS"
          android:resource="@xml/ch_file_paths"/>
    </provider>
    ...
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

## 체크인

채팅을 시작하려면 먼저 체크인을 해야 합니다.

체크인을 하는 방법은 두 가지가 있습니다.

### 1) 게스트로 체크인

게스트로 체크인 하는 방법은 두 가지가 있습니다.

#### 1-1) 추가 정보 입력 없이 체크인 하는 방법
`ChannelPlugin.checkIn();`을 호출하여 체크인

#### 1-2) 정보 입력을 하여 체크인 하는 방법
`ChannelPlugin.checkIn(CheckIn checkIn);`을 호출하여 체크인 할 수 있습니다.

필요한 정보는 아래와 같습니다.

- 이름 (옵션 사항, String)
- 전화번호 (옵션 사항, String)
- 아바타 이미지 URL (옵션 사항, String)
- 메타 데이터 (옵션 사항, Map<String, String>)

**주의사항 - 게스트로 체크인 할 때는 유저 아이디를 입력하지 않습니다.**

### 2) 유저로 체크인

유저로 체크인 하기 위해서는 유저 정보를 입력해야 합니다.

필요한 유저 정보는 아래와 같습니다.

- 유저 아이디 (필수 사항, String)
- 이름 (옵션 사항, String)
- 전화번호 (옵션 사항, String)
- 아바타 이미지 URL (옵션 사항, String)
- 메타 데이터 (옵션 사항, Map<String, String>)

메타 데이터는 유저 정보의 메타 데이터로써 보여지게 됩니다.


`CheckIn` 객체를 만드는 방법은 아래와 같습니다.

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

CheckIn 객체를 만들고 난 후, `ChannelPlugin.checkIn(CheckIn checkIn)`을 호출하여 체크인 할 수 있습니다.

만약 유저 아이디를 설정하지 않으면 게스트로 로그인하게 됩니다.

#### 체크인 콜백

체크인을 하고 난 후 결과에 대해 콜백을 받을 수 있습니다.

```java
ChannelPlugin.checkIn(OnCheckInListener listener) // 게스트로 체크인하는 경우
```

또는

```java
ChannelPlugin.checkIn(CheckIn checkIn, OnCheckInListener listener) // 유저로 체크인 하는 경우
```

`OnCheckInListener`는 두 가지 함수를 가집니다.

- `onSuccess()`

- `onFailed(ChannelException exception)`

`ChannelException.StatusCode`는 실패에 대한 정보를 담고 있습니다.

- NOT_INITIALIZED

- NETWORK_TIMEOUT

- ALREADY_CHECKED_IN

- CHECK_IN_ERROR

- NOT_AVAILABLE_VERSION

- SERVICE_UNDER_CONSTRUCTION

#### 뱃지 변경에 대한 리스터 추가

채널 버튼을 커스텀으로 만들어서 사용할 때, `OnChannelPluginChangedListener`을 사용하여 `ChannelPlugin.addOnChannelPluginChangedListener(listener)`를 호출할 수 있습니다.

뱃지가 변경되면 `badgeChanged(int count);`함수를 통해 결과를 받을 수 있습니다.

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

## 체크아웃

앱에서 사용 중인 유저가 로그아웃 하거나 세션이 만료되는 시점에 반드시 체크아웃을 해야 합니다

`ChannelPlugin.checkOut();`을 호출하여 체크아웃을 할 수 있습니다.

## 채팅 시작하기

채팅을 시작하는 방법은 두 가지가 있습니다.

### 레이아웃에 ChannelView를 추가

뷰에 `ChannelView`를 추가한 경우에는 체크인 완료 후 자동으로 `ChannelButton`이 보여지게 됩니다.

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

XML 속성을 이용하면 `ChannelButton`을 화면 어디든 배치할 수 있습니다.

#### XML attributes

| Name | Type | Default | Description |
|:----:|:----:|:-------:|:-----------:|
| channel_button_gravity | enum | bottom_right | ChannelView button's gravity (bottom_right, bottom_left, top_right, top_left) |
| channel_button_left_margin | dimension | 16dp | ChannelView button's left margin |
| channel_button_right_margin | dimension | 16dp | ChannelView button's right margin |
| channel_button_top_margin | dimension | 16dp | ChannelView button's top margin |
| channel_button_bottom_margin | dimension | 16dp | ChannelView button's bottom margin |

**사용되지 않는 마진은 무시합니다. (예를 들어, gravity 속성이 bottom_left일 때 right_margin은 무시됩니다.)**

### 수동으로 채팅 시작

`ChannelPlugin.launch(Context context);`을 호출하여 시작할 수 있습니다.
