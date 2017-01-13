package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

/**
 * Created by evancoulson on 12/3/16.
 */



public abstract class VuforiaBaseOpMode extends AutonomousOpMode {

    protected static class Target {
        public static int Wheels = 0;
        public static int Tools = 1;
        public static int Lego = 2;
        public static int Gears = 3;
    };

    VuforiaTrackables beacons;

    public void initialize() {
        initializeAllDevices();

        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AfIW5rj/////AAAAGaDrYjvjtkibrSYzQTjEFjJb+NGdODG1LJE2IVqxl0wdLW+9JZ3nIyQF2Hef7GlSLQxR/6SQ3pkFudWmzU48zdcBEYJ+HCwOH3vKFK8gJjuzrcc7nis7JrU+IMTONPctq+JTavtRk+LBhM5bxiFJhEO7CFnDqDDEFc5f720179XJOvZZA0nuCvIqwSslb+ybEVo/G8BDwH1FjGOaH/CxWaXGxVmGd4zISFBsMyrwopDI2T0pHdqvRBQ795QCuJFQjGQUtk9UU3hw/E8Z+oSC36CSWZPdpH3XkKtvSb9teM5xgomeEJ17MdV+XwTYL0iB/aRXZiXRczAtjrcederMUrNqqS0o7XvYS3eW1ViHfynl";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);

        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(Target.Wheels).setName("Wheels");
        beacons.get(Target.Tools).setName("Tools");
        beacons.get(Target.Lego).setName("Lego");
        beacons.get(Target.Gears).setName("Gears");

        beacons.activate();
    }

    public void driveToTarget(int targetNum) {
        VuforiaTrackableDefaultListener target = (VuforiaTrackableDefaultListener) beacons.get(targetNum).getListener();
        driveSystem.setPower(0.2);

        while (opModeIsActive() && target.getRawPose() == null) {
            idle();
        }

        driveSystem.setPower(0);

        //analyze beacon here
        VectorF translationWheels = target.getPose().getTranslation();
        double angleWheels = Math.atan2(translationWheels.get(2), translationWheels.get(0)); // in radians
        double degreesToTurnWheels = Math.toDegrees(angleWheels) + 90;                 // adjust for vertical orientation of phone
        double distanceWheels = Math.sqrt(translationWheels.get(2) * translationWheels.get(2) + translationWheels.get(0) * translationWheels.get(0));  // Pythagoras calc of hypotenuse
        //TODO: move robot toward the position provided by previous variables without turning





//////OLD CODE/////////////OLD CODE//////////////OLD CODE//////////////////////////OLD CODE/////////////////////////////////////////////////////////








        VectorF angles = anglesFromTarget(target);
        VectorF translation = navOffWall(target.getPose().getTranslation(), Math.toDegrees(angles.get(0) - 90), new VectorF(500, 0, 0));
        telemetry.addData("Translation 1 ", translation.get(0));
        if (translation.get(0) > 0) {
            driveSystem.motorBackLeft.setPower(0.2);
            driveSystem.motorFrontLeft.setPower(0.2);
            driveSystem.motorBackRight.setPower(-0.2);
            driveSystem.motorFrontRight.setPower(-0.2);
        } else {
            driveSystem.motorBackLeft.setPower(-0.2);
            driveSystem.motorFrontLeft.setPower(-0.2);
            driveSystem.motorBackRight.setPower(0.2);
            driveSystem.motorFrontRight.setPower(0.2);
        }

        do {
            if (target.getPose() != null) {
                translation = navOffWall(target.getPose().getTranslation(), Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0 , 0));
                telemetry.addData("Translation 2 ", translation.get(0));
            }
            idle();
            telemetry.update();
        } while(opModeIsActive() && Math.abs(translation.get(0)) > 30);

        driveSystem.setPower(0);

        driveSystem.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveSystem.motorBackLeft.setTargetPosition((int)(driveSystem.motorBackLeft.getCurrentPosition() + ((Math.hypot(translation.get(0), translation.get(2)) + 150) / 409.575 * 560)));
        driveSystem.motorBackRight.setTargetPosition((int)(driveSystem.motorBackRight.getCurrentPosition() + ((Math.hypot(translation.get(0), translation.get(2)) + 150) / 409.575 * 560)));
        driveSystem.motorFrontLeft.setTargetPosition((int)(driveSystem.motorFrontLeft.getCurrentPosition() + ((Math.hypot(translation.get(0), translation.get(2)) + 150) / 409.575 * 560)));
        driveSystem.motorFrontRight.setTargetPosition((int)(driveSystem.motorFrontRight.getCurrentPosition() + ((Math.hypot(translation.get(0), translation.get(2)) + 150) / 409.575 * 560)));

        driveSystem.setPower(0.3);

        while(opModeIsActive() && driveSystem.anyMotorsBusy()) {
            idle();
        }

        driveSystem.setPower(0);

        driveSystem.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (opModeIsActive() && (target.getPose() == null) || Math.abs(target.getPose().getTranslation().get(0)) > 10) {
            if (target.getPose() != null) {
                telemetry.addData("Translation 3 ", target.getPose().getTranslation().get(0));
                if (target.getPose().getTranslation().get(0) > 0) {
                    driveSystem.motorBackLeft.setPower(-0.2);
                    driveSystem.motorFrontLeft.setPower(-0.2);
                    driveSystem.motorBackRight.setPower(0.2);
                    driveSystem.motorFrontRight.setPower(0.2);
                } else {
                    driveSystem.motorBackLeft.setPower(0.2);
                    driveSystem.motorFrontLeft.setPower(0.2);
                    driveSystem.motorBackRight.setPower(-0.2);
                    driveSystem.motorFrontRight.setPower(-0.2);
                }
            } else {
                driveSystem.motorBackLeft.setPower(-0.2);
                driveSystem.motorFrontLeft.setPower(-0.2);
                driveSystem.motorBackRight.setPower(0.2);
                driveSystem.motorFrontRight.setPower(0.2);
            }
            idle();
            telemetry.update();
        }

        driveSystem.setPower(0);
    }

    public VectorF navOffWall(VectorF trans, double robotAngle, VectorF offWall) {
        return new VectorF((float) (trans.get(0) - offWall.get(0) * Math.sin(Math.toRadians(robotAngle)) - offWall.get(2) * Math.cos(Math.toRadians(robotAngle))), trans.get(1), (float) (trans.get(2) + offWall.get(0) * Math.cos(Math.toRadians(robotAngle)) - offWall.get(2) * Math.sin(Math.toRadians(robotAngle))));
    }

    public VectorF anglesFromTarget(VuforiaTrackableDefaultListener image) {
        float[] data = image.getRawPose().getData();
        float[][] rotation = {
                { data[0], data[1]},
                { data[4], data[5], data[6] },
                { data[8], data[9], data[10] }
        };
        double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
        double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
        double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);
        return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }
}