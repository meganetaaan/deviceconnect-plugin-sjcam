package jp.meganetaaan.deviceconnect.plugin.sjcam;

import org.deviceconnect.android.message.DConnectMessageService;
import org.deviceconnect.android.profile.SystemProfile;
import org.deviceconnect.android.service.DConnectService;

import jp.meganetaaan.deviceconnect.plugin.sjcam.profiles.MyBatteryProfile;
import jp.meganetaaan.deviceconnect.plugin.sjcam.profiles.MyCanvasProfile;
import jp.meganetaaan.deviceconnect.plugin.sjcam.profiles.MyDeviceOrientationProfile;
import jp.meganetaaan.deviceconnect.plugin.sjcam.profiles.MyMediaStreamRecordingProfile;
import jp.meganetaaan.deviceconnect.plugin.sjcam.profiles.MySystemProfile;
import org.deviceconnect.profile.ServiceDiscoveryProfileConstants.NetworkType;


public class MyMessageService extends DConnectMessageService {

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO 以降の処理では常駐型のサービスを生成しています. 要件に適さない場合は修正してください.
        DConnectService service = new DConnectService("my_service_id");
        // TODO サービス名の設定
        service.setName("SjcamPlugin Service");
        // TODO サービスの使用可能フラグのハンドリング
        service.setOnline(true);
        // TODO ネットワークタイプの指定 (例: BLE, Wi-Fi)
        service.setNetworkType(NetworkType.UNKNOWN);
        service.addProfile(new MyBatteryProfile());
        service.addProfile(new MyCanvasProfile());
        service.addProfile(new MyDeviceOrientationProfile());
        service.addProfile(new MyMediaStreamRecordingProfile());
        getServiceProvider().addService(service);
    }

    @Override
    protected SystemProfile getSystemProfile() {
        return new MySystemProfile();
    }

    @Override
    protected void onManagerUninstalled() {
        // TODO Device Connect Managerアンインストール時に実行したい処理. 実装は任意.
    }

    @Override
    protected void onManagerTerminated() {
        // TODO Device Connect Manager停止時に実行したい処理. 実装は任意.
    }

    @Override
    protected void onManagerEventTransmitDisconnected(final String origin) {
        // TODO アプリとのWebSocket接続が切断された時に実行したい処理. 実装は任意.
    }

    @Override
    protected void onDevicePluginReset() {
        // TODO Device Connect Managerの設定画面上で「プラグイン再起動」を要求された場合の処理. 実装は任意.
    }
}