package com.dawoo.gamebox.util.line;

import android.os.Environment;

import com.dawoo.coretool.util.packageref.PackageInfoUtil;
import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.R;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler instance;

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        try {
            String result = getErrorInfo(arg1);//message
            String currentTime = System.currentTimeMillis() + "";
            String mark = currentTime.substring(currentTime.length() - 6);
            UploadErrorLinesUtil.uploadAppCrashLog(
                    result,
                    BoxApplication.getContext().getString(R.string.app_code),
                    mark,
                    true);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }


    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     * @throws Exception
     */
    private String getErrorInfo(Throwable ex) throws Exception {
        StringBuffer sb = new StringBuffer();
        try {
            sb.append("版本号: " + PackageInfoUtil.getVersionCode(BoxApplication.getContext()));
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            String date = sDateFormat.format(new java.util.Date());
            sb.append("\r\n" + date + "\n");
            for (Map.Entry<String, String> entry : infos.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key + "=" + value + "\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            return sb.toString();
        } catch (Exception e) {
            sb.append("an error occured while writing file...\r\n");
        }
        return null;
    }


    public static String getGlobalpath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "crash" + File.separator;
    }

}
