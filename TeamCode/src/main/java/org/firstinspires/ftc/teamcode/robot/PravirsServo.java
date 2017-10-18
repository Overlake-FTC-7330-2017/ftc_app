package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Steven Abbott on 9/25/2017.
 */

public class PravirsServo {
    public Servo servoPravirsServo;
    HardwareMap hwMap;
    public void init(HardwareMap hwMap) {
        this.hwMap = hwMap;
        this.servoPravirsServo = this.hwMap.servo.get("pravirServo");
        servoPravirsServo.setPosition(0.5);
    }

    public void setAddPositionServoPravirsServo(double pos) {
        servoPravirsServo.setPosition(servoPravirsServo.getPosition() + pos);
    }

    public void setSubtractPositionServoPravirsServo(double pos) {
        servoPravirsServo.setPosition(servoPravirsServo.getPosition() - pos);
    }
}
