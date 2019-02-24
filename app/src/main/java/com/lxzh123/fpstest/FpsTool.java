package com.lxzh123.fpstest;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description $desc$
 * author      Created by lxzh
 * date        2019/2/24
 */
public class FpsTool {
    private final static String TAG = "FpsTool";
    private final static String SERVICE_CALL_COMMAND = "service call SurfaceFlinger 1013";
    private final static double NANO = 1000000000.0;
    private final static int DEFAULT_FRAME_RATE = 0;
    private Pattern pattern = Pattern.compile("([0-9a-f]{8})");
    private FrameData lastFrame;

    public Double getFrameRate() {
        long startTime = System.nanoTime();
//        {
//            for (int i = 0; i < 1; i++) {
//                CmdExecutor.exeCmdFast(SERVICE_CALL_COMMAND);
//            }
//            long endTime = System.nanoTime();
//            Log.d(TAG, "exe cmd for 10 times, time used:" + ((endTime - startTime) / 1000000) + "ms");
//        }

        String result = CmdExecutor.exeCmdFast(SERVICE_CALL_COMMAND);
        long endTime = System.nanoTime();
        long nanoTime=(startTime/2+endTime/2);

//        Log.d(TAG, "FPS:" + result);
        Matcher matcher = pattern.matcher(result);
        double fps = DEFAULT_FRAME_RATE;
        if (matcher.find()) {
            String number = matcher.group(1);
            int index = Integer.valueOf(number, 16);

            FrameData frame = new FrameData(index, nanoTime);
            if (lastFrame != null) {
                fps = calculateFps(lastFrame, frame);
                Log.d(TAG,"   FPS:time="+nanoTime+","+Math.round(fps)+","+fps);
            }
            lastFrame = frame;
        }
        return fps;
    }

    private double calculateFps(FrameData f1, FrameData f2) {
        return (f2.index - f1.index) * NANO / (f2.nanoTime - f1.nanoTime);
    }


}
