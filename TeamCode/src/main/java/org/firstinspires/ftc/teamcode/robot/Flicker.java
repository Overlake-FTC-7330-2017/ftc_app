package org.firstinspires.ftc.teamcode.robot;

/**
 * Created by EvanCoulson on 10/25/16.
 */
public class Flicker {
    private DcMotor flicker;
//    public Button shootButton;

    public Flicker(HardwareMap map) {
        this.flicker = map.dcMotor.get("flicker");
    }

    public void shoot() {
        if (!flicker.isBusy()) {
            flicker.setTargetPosition(flicker.getCurrentPosition() + 1120);
            flicker.setDirection(DcMotor.Direction.FORWARD);
            flicker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            flicker.setPower(0.8);
        }
    }
}
