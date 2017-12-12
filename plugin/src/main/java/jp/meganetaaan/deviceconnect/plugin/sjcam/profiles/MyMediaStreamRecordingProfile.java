package jp.meganetaaan.deviceconnect.plugin.sjcam.profiles;

import android.content.Intent;

import org.deviceconnect.android.event.Event;
import org.deviceconnect.android.event.EventError;
import org.deviceconnect.android.event.EventManager;
import org.deviceconnect.android.message.MessageUtils;
import org.deviceconnect.android.profile.DConnectProfile;
import org.deviceconnect.android.profile.api.DeleteApi;
import org.deviceconnect.android.profile.api.GetApi;
import org.deviceconnect.android.profile.api.PostApi;
import org.deviceconnect.android.profile.api.PutApi;
import org.deviceconnect.message.DConnectMessage;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import jp.meganetaaan.deviceconnect.plugin.sjcam.sjcam.SjcamDevice;
import jp.meganetaaan.deviceconnect.plugin.sjcam.sjcam.SjcamDeviceManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyMediaStreamRecordingProfile extends DConnectProfile {

    public MyMediaStreamRecordingProfile() {

        // GET /gotapi/mediaStreamRecording/mediaRecorder
        addApi(new GetApi() {
            @Override
            public String getAttribute() {
                return "mediaRecorder";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // PUT /gotapi/mediaStreamRecording/muteTrack
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "muteTrack";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // DELETE /gotapi/mediaStreamRecording/onPhoto
        addApi(new DeleteApi() {
            @Override
            public String getAttribute() {
                return "onPhoto";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");

                EventError error = EventManager.INSTANCE.removeEvent(request);
                switch (error) {
                    case NONE:
                        setResult(response, DConnectMessage.RESULT_OK);

                        // 以下、サンプルのイベントの定期的送信を停止.
                        String taskId = serviceId;
                        stopTimer(taskId);
                        break;
                    case INVALID_PARAMETER:
                        MessageUtils.setInvalidRequestParameterError(response);
                        break;
                    case NOT_FOUND:
                        MessageUtils.setUnknownError(response, "Event is not registered.");
                        break;
                    default:
                        MessageUtils.setUnknownError(response);
                        break;
                }
                return true;
            }
        });

        // PUT /gotapi/mediaStreamRecording/onPhoto
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "onPhoto";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                Long interval = parseLong(request, "interval");

                if (interval == null) {
                    interval = 1000L;
                }
                EventError error = EventManager.INSTANCE.addEvent(request);
                switch (error) {
                    case NONE:
                        setResult(response, DConnectMessage.RESULT_OK);

                        // 以下、サンプルのイベントの定期的送信を開始.
                        String taskId = serviceId;
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                Event event = EventManager.INSTANCE.getEvent(request);
                                Intent message = EventManager.createEventMessage(event);
                                // WARNING: イベントの定義が不正です.
                                sendEvent(message, event.getAccessToken());
                            }
                        };
                        startTimer(taskId, task, interval);
                        break;
                    case INVALID_PARAMETER:
                        MessageUtils.setInvalidRequestParameterError(response);
                        break;
                    default:
                        MessageUtils.setUnknownError(response);
                        break;
                }
                return true;
            }
        });

        // GET /gotapi/mediaStreamRecording/onPhoto
        addApi(new GetApi() {
            @Override
            public String getAttribute() {
                return "onPhoto";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // DELETE /gotapi/mediaStreamRecording/onRecordingChange
        addApi(new DeleteApi() {
            @Override
            public String getAttribute() {
                return "onRecordingChange";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");

                EventError error = EventManager.INSTANCE.removeEvent(request);
                switch (error) {
                    case NONE:
                        setResult(response, DConnectMessage.RESULT_OK);

                        // 以下、サンプルのイベントの定期的送信を停止.
                        String taskId = serviceId;
                        stopTimer(taskId);
                        break;
                    case INVALID_PARAMETER:
                        MessageUtils.setInvalidRequestParameterError(response);
                        break;
                    case NOT_FOUND:
                        MessageUtils.setUnknownError(response, "Event is not registered.");
                        break;
                    default:
                        MessageUtils.setUnknownError(response);
                        break;
                }
                return true;
            }
        });

        // PUT /gotapi/mediaStreamRecording/onRecordingChange
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "onRecordingChange";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                Long interval = parseLong(request, "interval");

                if (interval == null) {
                    interval = 1000L;
                }
                EventError error = EventManager.INSTANCE.addEvent(request);
                switch (error) {
                    case NONE:
                        setResult(response, DConnectMessage.RESULT_OK);

                        // 以下、サンプルのイベントの定期的送信を開始.
                        String taskId = serviceId;
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                Event event = EventManager.INSTANCE.getEvent(request);
                                Intent message = EventManager.createEventMessage(event);
                                // WARNING: イベントの定義が不正です.
                                sendEvent(message, event.getAccessToken());
                            }
                        };
                        startTimer(taskId, task, interval);
                        break;
                    case INVALID_PARAMETER:
                        MessageUtils.setInvalidRequestParameterError(response);
                        break;
                    default:
                        MessageUtils.setUnknownError(response);
                        break;
                }
                return true;
            }
        });

        // GET /gotapi/mediaStreamRecording/onRecordingChange
        addApi(new GetApi() {
            @Override
            public String getAttribute() {
                return "onRecordingChange";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // PUT /gotapi/mediaStreamRecording/options
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "options";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");
                Integer imageWidth = parseInteger(request, "imageWidth");
                Integer imageHeight = parseInteger(request, "imageHeight");
                Integer previewWidth = parseInteger(request, "previewWidth");
                Integer previewHeight = parseInteger(request, "previewHeight");
                Float previewMaxFrameRate = parseFloat(request, "previewMaxFrameRate");
                String mimeType = (String) request.getExtras().get("mimeType");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // GET /gotapi/mediaStreamRecording/options
        addApi(new GetApi() {
            @Override
            public String getAttribute() {
                return "options";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // PUT /gotapi/mediaStreamRecording/pause
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "pause";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // DELETE /gotapi/mediaStreamRecording/preview
        addApi(new DeleteApi() {
            @Override
            public String getAttribute() {
                return "preview";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");

                return true;
            }
        });

        // PUT /gotapi/mediaStreamRecording/preview
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "preview";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");

                return true;
            }
        });

        // GET /gotapi/mediaStreamRecording/preview
        addApi(new GetApi() {
            @Override
            public String getAttribute() {
                return "preview";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // POST /gotapi/mediaStreamRecording/record
        addApi(new PostApi() {
            @Override
            public String getAttribute() {
                return "record";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                return onPostRecord(request, response);
            }
        });

        // PUT /gotapi/mediaStreamRecording/resume
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "resume";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

        // PUT /gotapi/mediaStreamRecording/stop
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "stop";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                return onPutStop(request, response);
            }
        });

        // POST /gotapi/mediaStreamRecording/takePhoto
        addApi(new PostApi() {
            @Override
            public String getAttribute() {
                return "takePhoto";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                return onPostTakePhoto(request, response);
            }
        });

        // PUT /gotapi/mediaStreamRecording/unmuteTrack
        addApi(new PutApi() {
            @Override
            public String getAttribute() {
                return "unmuteTrack";
            }

            @Override
            public boolean onRequest(final Intent request, final Intent response) {
                String serviceId = (String) request.getExtras().get("serviceId");
                String target = (String) request.getExtras().get("target");

                // TODO ここでAPIを実装してください. 以下はサンプルのレスポンス作成処理です.
                setResult(response, DConnectMessage.RESULT_OK);
                // WARNING: レスポンスの定義が不正です.
                return true;
            }
        });

    }

    @Override
    public String getProfileName() {
        return "mediaStreamRecording";
    }

    private final Map<String, TimerTask> mTimerTasks = new ConcurrentHashMap<>();
    private final Timer mTimer = new Timer();

    private void startTimer(final String taskId, final TimerTask task, final long interval) {
        synchronized (mTimerTasks) {
            stopTimer(taskId);
            mTimerTasks.put(taskId, task);
            mTimer.scheduleAtFixedRate(task, 0, interval);
        }
    }

    private void stopTimer(final String taskId) {
        synchronized (mTimerTasks) {
            TimerTask timer = mTimerTasks.remove(taskId);
            if (timer != null) {
                timer.cancel();
            }
        }
    }

    private boolean onPostRecord(final Intent request, final Intent response) {
        String serviceId = (String) request.getExtras().get("serviceId");
        String target = (String) request.getExtras().get("target");
        SjcamDevice sjcamDevice = SjcamDeviceManager.getSjcamDevice();
        try {
            sjcamDevice.startRecording();
            setResult(response, DConnectMessage.RESULT_OK);
        } catch (Exception e) {
            MessageUtils.setInvalidRequestParameterError(response);
        }
        return true;
    }

    private boolean onPutStop(final Intent request, final Intent response) {
        String serviceId = (String) request.getExtras().get("serviceId");
        String target = (String) request.getExtras().get("target");
        SjcamDevice sjcamDevice = SjcamDeviceManager.getSjcamDevice();
        try {
            sjcamDevice.stopRecording();
            setResult(response, DConnectMessage.RESULT_OK);
        } catch (Exception e) {
            MessageUtils.setInvalidRequestParameterError(response);
        }
        return true;
    }

    private boolean onPostTakePhoto(final Intent request, final Intent response) {
        String serviceId = (String) request.getExtras().get("serviceId");
        String target = (String) request.getExtras().get("target");
        SjcamDevice sjcamDevice = SjcamDeviceManager.getSjcamDevice();
        try {
            sjcamDevice.takePhoto();
            setResult(response, DConnectMessage.RESULT_OK);
        } catch (Exception e) {
            MessageUtils.setInvalidRequestParameterError(response);
        }
        return true;
    }
}