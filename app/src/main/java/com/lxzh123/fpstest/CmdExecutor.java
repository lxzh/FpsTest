package com.lxzh123.fpstest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * description $desc$
 * author      Created by lxzh
 * date        2019/2/24
 */
public class CmdExecutor {
    private final static String LINE_SEPARATOR=System.getProperty("line.separator");

    static String exeCmdFast(String cmd){
        String result="";
        BufferedReader br=null;
        DataOutputStream dos =null;
        try{
            Process p=Runtime.getRuntime().exec("su");
            dos=new DataOutputStream(p.getOutputStream());
            br=new BufferedReader(new InputStreamReader(p.getInputStream()));

            dos.writeBytes(cmd+LINE_SEPARATOR);
            dos.flush();
            result=br.readLine();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(dos!=null){
                try {
                    dos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            if(br!=null){
                try {
                    br.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
