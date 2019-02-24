package com.lxzh123.fpstest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Choreographer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description $desc$
 * author      Created by lxzh
 * date        2019/2/1
 */
public class FpsService extends Service {
    private final static String TAG = "FpsService";

    public final static String MSG_FPS = "FPS";

    private final static int MSG_START_FPS = 0;
    private final static int MSG_STOP_FPS = 1;
    private final static int MSG_GETTING_FPS = 2;

    private final static int GET_FPS_INTERVAL = 1000;

    private ExecutorService fpsExecutor;
    private FpsTool fpsTool;
    private boolean isCaptureFps;

    @Override
    public void onCreate() {
        super.onCreate();
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            @Override
            public void doFrame(long frameTimeNanos) {
                Intent frmIntent = new Intent();
                frmIntent.setAction("frame");
                frmIntent.putExtra("frame", frameTimeNanos);
                Log.d("FpsService:doFrame", "frame:" + frameTimeNanos);
                sendBroadcast(frmIntent);
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_START_FPS:
                    startFps();
                    break;
                case MSG_STOP_FPS:
                    stopFps();
                    break;
                case MSG_GETTING_FPS:
                    getFps();
                    break;
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int msg = intent.getIntExtra(MSG_FPS, 0);
        if (isCaptureFps) {
            handler.sendEmptyMessage(MSG_STOP_FPS);
        } else {
            handler.sendEmptyMessage(MSG_START_FPS);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startFps() {
        if (fpsExecutor == null) {
            fpsExecutor = Executors.newSingleThreadExecutor();
        }
        fpsTool = new FpsTool();
        isCaptureFps = true;
        handler.sendEmptyMessage(MSG_GETTING_FPS);
    }

    private void stopFps() {
        isCaptureFps = false;
    }

    private void getFps() {

        fpsExecutor.execute(new Runnable() {
            @Override
            public void run() {
                double frameRate = fpsTool.getFrameRate();
                int fps = (int) Math.round(frameRate);
                Log.d(TAG, "fps is:" +fps+","+frameRate);
            }
        });
        if (isCaptureFps) {
            handler.sendEmptyMessageDelayed(MSG_GETTING_FPS, GET_FPS_INTERVAL);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
