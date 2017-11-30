package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.autonomous.AutonomousOpMode;

/**
 * Created by Michael on 11/29/2017.
 */

@Autonomous (name="TestDance", group="Bot")
public class TestDance extends AutonomousOpMode {

    private final double DRIVE_POWER = 0.6;

    @Override
    public void runOpMode() throws InterruptedException {
        initializeAllDevices();
        waitForStart();
        for (int i = -3; i < 0; i++) {
            driveToPositionRevs(-1, DRIVE_POWER);
            sleep(250);
            turn(45, DRIVE_POWER);
            sleep(250);
            driveToPositionRevs(-1, DRIVE_POWER);
        }
    }
}
