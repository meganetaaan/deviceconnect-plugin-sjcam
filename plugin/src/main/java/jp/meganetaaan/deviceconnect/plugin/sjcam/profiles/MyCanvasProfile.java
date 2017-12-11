package jp.meganetaaan.deviceconnect.plugin.sjcam.profiles;

import android.content.Intent;
import android.os.Bundle;

import org.deviceconnect.android.message.MessageUtils;
import org.deviceconnect.android.profile.DConnectProfile;
import org.deviceconnect.android.profile.api.PostApi;
import org.deviceconnect.android.profile.api.DeleteApi;
import org.deviceconnect.message.DConnectMessage;

public class MyCanvasProfile extends DConnectProfile {

    public MyCanvasProfile() {

        // DELETE /gotapi/canvas/drawImage
        addApi(new DeleteApi() {
            @Override
            public String getAttribute() {
                return "drawImage";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                return true;
            }
        });

        // POST /gotapi/canvas/drawImage
        addApi(new PostApi() {
            @Override
            public String getAttribute() {
                return "drawImage";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String mimeType = (String) request.getExtras().get("mimeType");
                byte[] data = (byte[]) request.getExtras().get("data");
                Integer x = parseInteger(request, "x");
                Integer y = parseInteger(request, "y");
                String mode = (String) request.getExtras().get("mode");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                return true;
            }
        });

    }

    @Override
    public String getProfileName() {
        return "canvas";
    }
}