package jp.meganetaaan.deviceconnect.plugin.sjcam.profiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import jp.meganetaaan.deviceconnect.plugin.sjcam.MySettingActivity;
import org.deviceconnect.android.profile.SystemProfile;


public class MySystemProfile extends SystemProfile {
    @Override
    protected Class<? extends Activity> getSettingPageActivity(final Intent request, final Bundle param) {
        // TODO 設定画面が不要な場合、null を返却してください.
        return MySettingActivity.class;
    }
}