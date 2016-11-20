package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by EvanCoulson on 11/15/16.
 */

public class BallLiftSystem {

    private HardwareMap map;
    private DcMotor lifter;
    private DcMotor belt;
    private DigitalChannel inputStream;
    private Telemetry telemetry;
    private LiftPosition position;
    public boolean autonomous;

    private boolean debug;

    public BallLiftSystem(HardwareMap map) {
        this.map = map;
        this.lifter = map.dcMotor.get("ballLiftMotor");
        this.belt = map.dcMotor.get("ballBeltMotor");
        //this.inputStream = map.digitalChannel.get("ballLifterSwitch");
        this.position = LiftPosition.AT_SWITCH;
    }

    public void runLift(boolean isFoward) {
        if (isFoward)
            lifter.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            lifter.setDirection(DcMotorSimple.Direction.REVERSE);
        lifter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lifter.setPower(0.42);
    }

    public void runLift(double revolutions) {
        while (lifter.isBusy()) {
            lifter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            int tics = (int)(1120 * revolutions);
            lifter.setTargetPosition(lifter.getCurrentPosition() + tics);
            lifter.setPower(0.42);
        }
    }

    public void stopLift() {
        lifter.setPower(0.0);
    }

    public void runBelt(boolean isFoward) {
        if (isFoward)
            belt.setDirection(DcMotorSimple.Direction.FORWARD);
        else
            belt.setDirection(DcMotorSimple.Direction.REVERSE);
        belt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        belt.setPower(0.6);
    }

    public void runBelt(double revolutions) {
        while (belt.isBusy()) {
            belt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            int tics = (int)(1120 * revolutions);
            belt.setTargetPosition(belt.getCurrentPosition() + tics);
            belt.setPower(0.6);
        }
    }

    public void stopBelt() {
        belt.setPower(0);
    }


    public void runToSwitchPosition() {
        position = LiftPosition.AT_ENCODER;
    }

    public void runToEncoderPosition() {
        position = LiftPosition.AT_SWITCH;
    }

    public void togglePosition() {
        if (position == LiftPosition.AT_SWITCH) {
            runToEncoderPosition();
        } else {
            runToSwitchPosition();
        }
    }

    public boolean getState() {
        try {
            return inputStream.getState();
        } catch (Exception e) {
            if (debug) {
                telemetry.addData("ERROR: ", "BALL LIFT INPUT STREAM IS NULL");
            }
            throw new NullPointerException("Null Ball Lift Stream");
        }
    }

    public void setDebug(Telemetry telemetry) {
        this.telemetry = telemetry;
        this.debug = true;
    }

    private enum LiftPosition {
        AT_SWITCH,
        AT_ENCODER
    }
}
