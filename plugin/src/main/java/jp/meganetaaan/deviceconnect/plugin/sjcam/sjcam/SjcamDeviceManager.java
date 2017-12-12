package jp.meganetaaan.deviceconnect.plugin.sjcam.sjcam;

/**
 * Created by ishik on 17/12/13.
 */

public class SjcamDeviceManager {
    private static SjcamDevice sjcamDevice;
    public static SjcamDevice getSjcamDevice () {
        if (sjcamDevice == null) {
            sjcamDevice = new SjcamDevice();
        }
        return sjcamDevice;
    }
}
