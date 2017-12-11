package jp.meganetaaan.deviceconnect.plugin.sjcam.sjcam;

/**
 * Created by ishik on 17/12/11.
 */

public class SjcamDevice {
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
    public SjcamDevice() {
    }

    private final String makeRequestURL(String command, String mode){
        return BASE_URL;
    };
}
