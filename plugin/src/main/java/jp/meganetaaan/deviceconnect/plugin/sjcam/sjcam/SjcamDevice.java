package jp.meganetaaan.deviceconnect.plugin.sjcam.sjcam;


import org.deviceconnect.android.message.MessageUtils;
import org.deviceconnect.message.DConnectMessage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private final static String STATUS_REGEX = "<Status>(.+)</Status>";
    private final static String COMMAND_REGEX = "<Cmd>(.+)</Cmd>";
    private class Result {
        private Command command;
        private int status;

        public Command getCommand() {
            return command;
        }

        public int getStatus() {
            return status;
        }

        public Response getResponse() {
            return response;
        }

        private Response response; // TODO: wrap

        public Result(Response response) {
            try {
                String bodyStr = response.body().string();
                Log.d(TAG, "bodyStr: " + bodyStr);
                Matcher commandMatcher = Pattern.compile(COMMAND_REGEX).matcher(bodyStr);
                Matcher regexMatcher = Pattern.compile(STATUS_REGEX).matcher(bodyStr);

                if(commandMatcher.find()) {
                    this.command = Command.getCommand(Integer.parseInt(commandMatcher.group(1)));
                }
                if(regexMatcher.find()) {
                    this.status = Integer.parseInt(regexMatcher.group(1));
                }
                this.response = response;
            } catch (IOException e) {
                e.printStackTrace();
                // TODO: error handling
            }
        }
    }
    public enum CameraMode {
        PHOTO(0),
        VIDEO(1),
        TIMELAPSE_VIDEO(2),
        TIMELAPSE_PHOTO(3);

        private final int code;
        private CameraMode(final int code) { this.code = code; }
        public static CameraMode getCameraMode(int i) {
            for(CameraMode mode : CameraMode.values()) {
                if (mode.getCode() == i) {
                    return mode;
                }
            }
            return null;
        }

        public int getCode() { return this.code; }
    }

    public enum Command {
        TAKE_PHOTO(1001),
        RECORD_VIDEO(2001),
        SET_CAMERA_MODE(3001),
        GET_CAMERA_MODE(3016);

        private final int code;
        private Command(final int code) { this.code = code; }
        public static Command getCommand(int code) {
            for(Command cmd : Command.values()) {
                if (cmd.getCode() == code) {
                    return cmd;
                }
            }
            return null;
        }
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
        int code = mode.getCode();
        Result result = executeCommand(Command.SET_CAMERA_MODE, code);
        return result.getStatus();
    }

    public CameraMode getCameraMode () {
        Result result = executeCommand(Command.GET_CAMERA_MODE);
        return CameraMode.getCameraMode(result.getStatus());
    }

    public int takePhoto () {

        if(getCameraMode() != CameraMode.PHOTO) {
            setCameraMode(CameraMode.PHOTO);
        }
        Result result = executeCommand(Command.TAKE_PHOTO);
        // TODO: takePhoto result enum
        return result.getStatus();
    }

    public int startRecording () {
        if(getCameraMode() != CameraMode.VIDEO) {
            setCameraMode(CameraMode.VIDEO);
        }
        Result result = executeCommand(Command.RECORD_VIDEO, 1);
        return result.getStatus();
    }

    public int stopRecording () {
        Result result = executeCommand(Command.RECORD_VIDEO, 0);
        return result.getStatus();
    }

    public int getPowerStatus () {
        return 0;
    }

    private Result executeCommand(Command cmd) {
        String url = new StringBuilder()
                .append(BASE_URL)
                .append("&cmd=")
                .append(cmd.getCode()).toString();
        try {
            Response res = sendRequest(url);
            return new Result(res);
        } catch(IOException e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Result executeCommand(Command cmd, int par) {
        String url = new StringBuilder()
                .append(BASE_URL)
                .append("&cmd=")
                .append(cmd.getCode())
                .append("&par=")
                .append(par).toString();
        try {
            Response res = sendRequest(url);
            return new Result(res);
        } catch(IOException e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Result executeCommand(Command cmd, String str) {
        String url = new StringBuilder()
                .append(BASE_URL)
                .append("&cmd=")
                .append(cmd.getCode())
                .append("&str=")
                .append(str).toString();
        try {
            Response res = sendRequest(url);
            return new Result(res);
        } catch(IOException e) {
            Log.e(TAG, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Response sendRequest(String url) throws IOException {
        Log.i(TAG, "sendRequest: " + url);
        Request req = new Request.Builder().url(url).build();
        Response res = this.client.newCall(req).execute();
        return res;
    }
}
