package jp.meganetaaan.deviceconnect.plugin.sjcam.profiles;

import android.content.Intent;
import android.os.Bundle;

import org.deviceconnect.android.message.MessageUtils;
import org.deviceconnect.android.profile.DConnectProfile;
import org.deviceconnect.android.profile.api.GetApi;
import org.deviceconnect.message.DConnectMessage;

public class MyBatteryProfile extends DConnectProfile {

    public MyBatteryProfile() {

        // GET /gotapi/battery
        addApi(new GetApi() {
            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                Bundle root = response.getExtras();
                root.putBoolean("charging", false);
                root.putLong("dischargingTime", 0L);
                root.putFloat("level", 0.0f);
                root.putLong("chargingTime", 0L);
                response.putExtras(root);
                return true;
            }
        });

    }

    @Override
    public String getProfileName() {
        return "battery";
    }
}