package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoControllerEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.teleop.ControllerOpMode;
import org.firstinspires.ftc.teamcode.util.config.*;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by jacks on 11/28/2017.
 */

public class ParallelLiftSystem {
    private ConfigParser config;
    private DigitalChannel parallelTouch;
    private DcMotor parallelMotor;
    private int bottom;
    private int middle;
    private int top;
    private int encoderVal;
    private int position;
    private boolean isAtBottom = false;
    private boolean debouncing = false;
    private double negativePower = -0.55;
    private double positivePower = 0.55;

    private ElapsedTime debounceTime = new ElapsedTime();

    Telemetry telemetry;

    public ParallelLiftSystem(ControllerOpMode opMode, HardwareMap map) {
        this.telemetry = telemetry;
        this.config = new org.firstinspires.ftc.teamcode.util.config.ConfigParser("lifter.omc");
        this.parallelMotor = map.dcMotor.get("parallelMotor");
        this.parallelTouch = map.get(DigitalChannel.class, "parallelTouch");
        bottom = config.getInt("bottom");
        middle = config.getInt("middle");
        top = config.getInt("top");
    }


    public void checkForBottom(Telemetry telemetry){
        //On Rev, when configuring use the second input in digital
        parallelTouch.setMode(DigitalChannel.Mode.INPUT);
        boolean bottomSwitchPushed = (parallelTouch.getState() == false);
        if( bottomSwitchPushed&& !isAtBottom) {
            parallelMotor.setPower(0.0);
            encoderVal = parallelMotor.getCurrentPosition();
            position = bottom;
            isAtBottom = true;
        } else if (isAtBottom) {
            if(!bottomSwitchPushed && !debouncing) {
                debounceTime.reset();
                debouncing = true;
            }
            if (debouncing && debounceTime.milliseconds() > 50) {
                isAtBottom =  false; //(touchSensorBottom.getState() == false);
                debouncing = false;
            }
        }
    }

    public void runMotorDown() {
        parallelMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        parallelMotor.setPower(positivePower);
    }

    public void goToMiddle() {
        parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        parallelMotor.setTargetPosition(encoderVal + middle);
        if(position > middle) {
            parallelMotor.setPower(negativePower);
        } else {
            parallelMotor.setPower(positivePower);

        }
        position = middle;
    }

    public void goToTop() {
        parallelMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        parallelMotor.setTargetPosition(encoderVal + top);
        if(position > top) {
            parallelMotor.setPower(negativePower);
        } else {
            parallelMotor.setPower(positivePower);

        }
        position = top;
    }


}
