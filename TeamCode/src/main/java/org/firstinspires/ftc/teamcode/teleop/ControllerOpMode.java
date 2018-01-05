package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robot.*;
import org.firstinspires.ftc.teamcode.util.Handler;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;

/**
 * Created by jacks on 10/5/2017.
 */
@TeleOp(name="ContollerOpMode", group="TeleOp")
public class ControllerOpMode extends BaseOpMode {
    private ClawSystem claw;
    private ElevatorSystem elevator;
    private ParellelLiftSystem lifter;
    private ConfigParser config;

    boolean slowDrive;

    public ControllerOpMode(){
        super("ControllerOpMode");
        this.slowDrive = false;
    }

    @Override
    public void init(){
        this.config = new ConfigParser("TeleOpMecanum.omc");
        claw = new ClawSystem(this.hardwareMap);
        elevator = new ElevatorSystem(this.hardwareMap, telemetry);

    //Claw

        // load position claw
        controller2.rightTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.goToLoadPosition();
            }
        };

        // release claw
        controller2.leftTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.goToReleasePosition();
            }
        };

        // save load position
        controller2.leftTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.setLoadPosition();
            }
        };

        // save release position
        controller2.leftTriggerShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.setReleasePosition();
            }
        };

        // increment servo
        controller2.dPadRight.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.incrementServo();
            }
        };

        // decrement servo
        controller2.dPadRight.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                claw.decrementServo();
            }
        };

    //ELEVATOR

        //goes to 0
        controller2.a.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.runMotorDown();
            }
        };

        // goes to Block 2
        controller2.b.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.goToUnloadBlock2();
            }
        };

        // goes to Block 3
        controller2.x.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.goToUnloadBlock3();
            }
        };

        // run motor up
        controller2.y.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.runMotorUp();
            }
        };

        // increment elevator up
        controller2.dPadUp.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.incrementUp();
            }
        };

        // decrements elevator down
        controller2.dPadDown.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.incrementDown();
            }
        };

        // save block 2 position
        controller2.bShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.setPositionBlock2();
            }
        };

        // save block 3 position
        controller2.xShifted.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                elevator.setPositionBlock3();
            }
        };

        controller1.setTriggerValue(TriggerType.LEFT, 0.5f);

        controller1.leftTrigger.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                slowDrive = !slowDrive;
            }
        };

        controller1.x.pressedHandler = new Handler() {
            @Override
            public void invoke() throws Exception {
                driveSystem.resetInitialHeading();
            }
        };

        /*
        controller1.a.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        lifter.goToBottom();
                    }
                }; */
    }


    @Override
    public void loop() {
        controller1.handle();
        controller2.handle();

        float rx = controller1.gamepad.right_stick_x;
        float ry = controller1.gamepad.right_stick_y;
        float lx = controller1.gamepad.left_stick_x;
        float ly = controller1.gamepad.left_stick_y;

        if (config.getBoolean("superDrive")) {
            float coeff = slowDrive == true ? 0.5f : 1f;
            this.driveSystem.driveGodMode(rx, ry, lx, ly, coeff);
        } else  {
            this.driveSystem.mecanumDrive(rx, ry, lx, ly, slowDrive);
        }

        telemetry.update();
    }


    @Override
    public void stop() {

    }


}