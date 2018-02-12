package org.firstinspires.ftc.teamcode.util.logger;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EvanCoulson on 2/11/18.
 */

public class TelemetryLogger extends Logger {

    private OpMode mode;
    private Map<String, Telemetry.Item> items;

    public TelemetryLogger(OpMode mode, String fileName) {
        super(fileName);
        this.mode = mode;
        this.items = new HashMap<String, Telemetry.Item>();
    }

    public void watch(String tag, Object data) {
        items.put(tag, mode.telemetry.addData(tag, data));
    }

    public void v(String tag, Object data) {
        setValue(tag, data);
        println(tag, "v", data.toString());
    }

    public void d(String tag, Object data) {
        setValue(tag, data);
        println(tag, "d", data.toString());
    }

    public void i(String tag, Object data) {
        setValue(tag, data);
        println(tag, "i", data.toString());
    }

    public void w(String tag, Object data) {
        setValue(tag, data);
        println(tag, "w", data.toString());
    }

    public void e(String tag, Object data) {
        setValue(tag, data);
        println(tag, "e", data.toString());
    }

    private void setValue(String tag, Object data) {
        Telemetry.Item item = items.get(tag);
        item.setValue(data);
    }

}
