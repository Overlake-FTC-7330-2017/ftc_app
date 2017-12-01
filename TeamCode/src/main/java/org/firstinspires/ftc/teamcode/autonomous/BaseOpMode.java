package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends LinearOpMode {
    private ConfigParser config;

    public BaseOpMode(String opModeName) {
        try {
            config = new ConfigParser(opModeName + ".omc");
        } catch (Exception e) {
            throw new IllegalArgumentException("OP MODE CONFIGURATION NOT FOUND \"" + opModeName + "\"");
        }
    }
}