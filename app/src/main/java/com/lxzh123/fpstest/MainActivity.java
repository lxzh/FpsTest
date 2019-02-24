package com.lxzh123.fpstest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FpsView fpsView;
    FpsReceiver receiver;
    Intent fpsIntent;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fpsView = findViewById(R.id.fps);
        fpsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpsView.invalidate();
            }
        });
        findViewById(R.id.btnFPS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this,FpsService.class));
            }
        });
        register();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister();
    }

    private void register() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("frame");
        receiver = new FpsReceiver();
        registerReceiver(receiver, filter);

        fpsIntent = new Intent(this, FpsService.class);
        startService(fpsIntent);
    }

    private void unregister() {
        unregisterReceiver(receiver);
        stopService(fpsIntent);
    }

    class FpsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("frame")) {
                long time = intent.getLongExtra("frame", 0);
                fpsView.setText("fps:" + time);
            }
        }
    }
}
