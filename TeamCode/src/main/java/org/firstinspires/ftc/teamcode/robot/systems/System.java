package org.firstinspires.ftc.teamcode.robot.systems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;
import org.firstinspires.ftc.teamcode.util.logger.Logger;
import org.firstinspires.ftc.teamcode.util.logger.LoggingService;

/**
 * Created by EvanCoulson on 10/5/17.
 */

public abstract class System {

    private String system;
    private String fileName;

    protected HardwareMap map;
    public ConfigParser config;
    public Telemetry telemetry;

    public Logger logger;

    public System(OpMode opMode, String system) {
        this.map = opMode.hardwareMap;
        this.system = system;
        this.fileName = system + ".omc";
        this.telemetry = opMode.telemetry;
        this.config = new ConfigParser(fileName);
        this.logger = new Logger(system);
    }

    public String getFileName() {
        return fileName;
    }

    public String getSystemName() {
        return system;
    }
}
