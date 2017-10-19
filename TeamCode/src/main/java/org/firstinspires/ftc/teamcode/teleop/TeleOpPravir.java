package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.teamcode.robot.Button;
import org.firstinspires.ftc.teamcode.robot.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.robot.PravirsServo;
//import org.firstinspires.ftc.teamcode.robot.TestMotor;

import org.firstinspires.ftc.teamcode.util.Handler;


/**
 * Created by Steven Abbott on 9/25/2017.
 */

@TeleOp(name="TeleOpPravir", group="TeleOp")
public class TeleOpPravir extends OpMode {
    private PravirsServo pravirsServo;
    //private TestMotor meMotor;
    private Button pravirButton;
    private Button doiButton;
    private Button motorButton;

    @Override
    public void init() {
        /*meMotor = new TestMotor();
        this.meMotor.init(this.hardwareMap);
        pravirsServo = new PravirsServo();
        this.pravirsServo.init(this.hardwareMap);

        this.motorButton = new Button();
        this.pravirButton = new Button();

        this.motorButton.isPressed=
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.y;
                    }
                };
        this.pravirButton.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.a;
                    }
                };

        this.motorButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        meMotor.rotate();
                        telemetry.addLine("TEST");
                    }
                };
        this.pravirButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {

                    }
                };
        this.pravirButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        pravirsServo.setAddPositionServoPravirsServo(0.2);
                        telemetry.addLine("TEST");
                    }
                };
        this.pravirButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {

                    }
                };

        this.doiButton = new Button();

        this.doiButton.isPressed =
                new Func<Boolean>()
                {
                    @Override
                    public Boolean value()
                    {
                        return gamepad2.b;
                    }
                };
        this.doiButton.pressedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {
                        pravirsServo.setSubtractPositionServoPravirsServo(0.2);
                    }
                };
        this.doiButton.releasedHandler =
                new Handler()
                {
                    @Override
                    public void invoke()
                    {

                    }
                };*/
    }
    public void loop() {
        motorButton.testAndHandle();

        //telemetry.addLine(String.format("m:%d", meMotor.meMotor.getCurrentPosition()));

        doiButton.testAndHandle();
        pravirButton.testAndHandle();
    }
}
