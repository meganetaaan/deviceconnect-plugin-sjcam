package jp.meganetaaan.deviceconnect.plugin.sjcam.sjcam;

import okhttp3.OkHttpClient;

/**
 * Created by ishik on 17/12/12.
 */

public class OkHttpManager {
    private static OkHttpClient okHttpClient;
    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
}
