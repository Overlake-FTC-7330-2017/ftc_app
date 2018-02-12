package org.firstinspires.ftc.teamcode.util.logger;

import android.os.Environment;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by EvanCoulson on 1/5/18.
 */

public class Logger {
    private File globalLogFile;
    private File logFile;
    private FileWriter globalWriter;
    private FileWriter localWriter;
    private String system;
    private boolean shouldLogGlobal;
    private File logDir;

    public Logger(String fileName) {
        this(fileName, true);
    }

    public Logger(String fileName, boolean shouldLogGlobal) {
        this.shouldLogGlobal = shouldLogGlobal;
        this.system = fileName;

        if (isExternalStorageWriteable()) {
            File appDir = new File(Environment.getExternalStorageDirectory() + "/Overlake_FTC");
            this.logDir = new File(appDir + "/logs");

            if (!appDir.exists()) {
                appDir.mkdir();
            }

            if (!logDir.exists()) {
                logDir.mkdir();
            }

            try {
                this.logFile = new File(logDir, fileName + ".txt");
                this.localWriter = new FileWriter(logFile);
                if (shouldLogGlobal) {
                    this.globalLogFile = new File(logDir, "log.txt");
                    this.globalWriter = new FileWriter(globalLogFile);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void setGlobalLoggingState(boolean shouldLogGlobal) {
        this.shouldLogGlobal = shouldLogGlobal;
        if (shouldLogGlobal) {
            try {
                this.globalLogFile = new File(this.logDir, "log.txt");
                this.globalWriter = new FileWriter(globalLogFile);
            } catch (IOException e) {
                e("Exception", e.toString());
                e.printStackTrace();
            }
        } else {
            this.globalLogFile = null;
            this.globalWriter = null;
        }
    }

    public boolean getGlobalLoggingState() {
        return this.shouldLogGlobal;
    }

    private boolean isExternalStorageWriteable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public void v(String tag, String data) {
        println(tag, "verbose", data);
    }
    public void d(String tag, String data) {
        println(tag, "debug", data);
    }
    public void i(String tag, String data) {
        println(tag, "info", data);
    }
    public void w(String tag, String data) {
        println(tag, "warn", data);
    }
    public void e(String tag, String data) {
        println(tag, "error", data);
    }

    public void println(String tag, String priority, String data) {
        Date d = new Date();
        String line = d.toString() + " [" + this.system + "] " + "{" + tag + "} " + priority + ": " + data + "\n";
        try {
            this.localWriter.append(line);
            if (this.shouldLogGlobal) {
                this.globalWriter.append(line);
                this.globalWriter.flush();
            }
            this.localWriter.flush();
        } catch(Exception e) {
            e.printStackTrace();;
        }
    }


}
