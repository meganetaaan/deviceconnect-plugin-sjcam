package jp.meganetaaan.deviceconnect.plugin.sjcam.sjcam;


import org.deviceconnect.android.message.MessageUtils;
import org.deviceconnect.message.DConnectMessage;

import java.io.IOException;
import android.util.Log;

import jp.meganetaaan.deviceconnect.plugin.sjcam.sjcam.OkHttpManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ishik on 17/12/11.
 */

public class SjcamDevice {
    private final static String TAG = SjcamDevice.class.getSimpleName();
    public enum CameraMode {
        PHOTO(0),
        VIDEO(1);

        private final int code;
        private CameraMode(final int code) { this.code = code; }

        public int getCode() { return this.code; }
    }

    public enum Command {
        TAKE_PHOTO(1001),
        RECORD_VIDEO(2001),
        SET_CAMERA_MODE(3001);

        private final int code;
        private Command(final int code) { this.code = code; }
        public int getCode() {return this.code; }
    }

    public enum Resolution {
        R_12M(0),
        R_10M(1),
        R_8M(2),
        R_5M(3),
        R_3M(4),
        R_2MHD(5),
        R_1_3M(6),
        R_VGA(7),
        R_14M(8);

        private final int code;
        private Resolution(final int code) {
            this.code = code;
        }

        public int getCode() {
            return this.code;
        }
    }

    private final String BASE_URL = "http://192.168.1.254/?custom=1";
    private final String PREVIEW_URL = "http://192.168.1.254:8192/";
    private OkHttpClient client;

    public SjcamDevice() {
        this.client = OkHttpManager.getOkHttpClient();
    }

    public int setCameraMode (CameraMode mode) {
        return 0;
    }

    public CameraMode getCameraMode () {
        return CameraMode.PHOTO;
    }

    public int takePhoto () {
        int result = executeCommand(Command.TAKE_PHOTO);
        return result;
    }

    public int startRecording () {
        int result = executeCommand(Command.RECORD_VIDEO, 1);
        return result;
    }

    public int stopRecording () {
        int result = executeCommand(Command.RECORD_VIDEO, 0);
        return result;
    }

    public int getPowerStatus () {
        return 0;
    }

    private int executeCommand(Command cmd) {
        String url = new StringBuilder()
                .append(BASE_URL)
                .append("&cmd=")
                .append(cmd.getCode()).toString();
        try {
            sendRequest(url);
        } catch(IOException e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int executeCommand(Command cmd, int par) {
        String url = new StringBuilder()
                .append(BASE_URL)
                .append("&cmd=")
                .append(cmd.getCode())
                .append("&par=")
                .append(par).toString();
        try {
            // TODO: parse and return response
            sendRequest(url);
        } catch(IOException e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int executeCommand(Command cmd, String str) {
        String url = new StringBuilder()
                .append(BASE_URL)
                .append("&cmd=")
                .append(cmd.getCode())
                .append("&str=")
                .append(str).toString();
        try {
            sendRequest(url);
        } catch(IOException e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
        return 0;
    }

    private Response sendRequest(String url) throws IOException {
        Log.i(TAG, "sendRequest: " + url);
        Request req = new Request.Builder().url(url).build();
        Response res = this.client.newCall(req).execute();
        return res;
    }
}
