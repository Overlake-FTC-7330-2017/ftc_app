package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;
import org.firstinspires.ftc.teamcode.robot.Eye;
import org.firstinspires.ftc.teamcode.robot.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.util.ramp.ExponentialRamp;
import org.firstinspires.ftc.teamcode.util.ramp.Ramp;

/**
 * Created by Steven Abbott on 9/15/2017.
 */

@Autonomous(name="EducayshunOpMode", group="Bot")
public class EducayshunOpMode extends AutonomousOpMode {
    public final double DRIVE_POWER;
    //VectorF trans = null;
    //Orientation rot = null;
    //OpenGLMatrix pose = null;
    //double  position = (1 - 0) / 2; // Start at halfway position
    public static final String TAG = "Vuforia VuMark Sample";
    //OpenGLMatrix lastLocation = null;
    //VuforiaLocalizer vuforia;

    public EducayshunOpMode(){
        DRIVE_POWER = 1;
    }
    public void runOpMode() {




        initializeAllDevices();
        waitForStart();
        double goTo = 161;
        //driveSystem.setTargetPositionInches(-30);
        //turnTo(271, 1);
        //eye.find();
        //driveXY(-900, -1000, driveSystem, 1);
        telemetry.addLine("Current heading is " + imuSystem.getHeading() + " degrees. Turning to " + goTo);
        telemetry.update();
        turnTo(goTo, 1);
        telemetry.addLine("We're there!!! Current heading: " + imuSystem.getHeading());
        telemetry.update();
        sleep(10000);
        learnIMU();
        //telemetry.addLine("Please align the robot to perpindicular to the wall.");
        //telemetry.update();
        //sleep(5000);
        //driveSystem.setTargetPositionInches((900/25.4));
        //sleep(5000);
        //learnIMU();
        //driveSystem.mecanumDriveXY(0, -1);
        //sleep(2000);
        //driveSystem.mecanumDriveXY(0, 0);
        stop();
    }


    /*public void testServo() {
        //armLeft.setPosition(position);
        //armRight.setPosition(position);


    }
    public void driveBox() {
        int echsdee = 0;
        while (echsdee < 4){
            driveToPositionRevs(3, DRIVE_POWER);
            turn(90, DRIVE_POWER);
            echsdee++;
        }

    }

    public void driveBox2() {
        driveSystem.mecanumDriveXY(0, 2);
        sleep(2000);
        driveSystem.mecanumDriveXY(2, 0);
        sleep(2000);
        driveSystem.mecanumDriveXY(0, -2);
        sleep(2000);
        driveSystem.mecanumDriveXY(-2, 0);
        sleep(2000);
        driveSystem.mecanumDriveXY(0, 0);
    }*/

    public void learnIMU() {
        while (true){
            telemetry.addLine("Heading:               " + imuSystem.getHeading());
            telemetry.addLine("X plane Slope:         " + imuSystem.getAngleOnPlaneX());
            telemetry.addLine("Y plane slope:         " + imuSystem.getAngleOnPlaneY());
            telemetry.addLine("Linear X acceleration: " + imuSystem.getLinearAccelerationX());
            telemetry.addLine("Linear Y acceleration: " + imuSystem.getLinearAccelerationY());
            telemetry.addLine("Linear Z acceleration: " + imuSystem.getLinearAceelerationZ());
            telemetry.addLine("x distance traveled:   " + imuSystem.getPosition().x / 1000);
            telemetry.addLine("y distance traveled:   " + imuSystem.getPosition().y / 1000);
            telemetry.addLine("z distance traveled:   " + imuSystem.getPosition().z / 1000);
            telemetry.update();
            sleep(1);
        }
    }

    /*public void balance() {
        double power  = 0.35;
        double BALANCED_X_PLANE = 159.9375;
        double BALANCED_Y_PLANE = 0.0625;
        double MARGIN_OF_ERROR = 3;
        int CORRECTION = 150;
        while (CORRECTION == 150) {
            if (imuSystem.getAngleOnPlaneX() > BALANCED_X_PLANE + MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(power, 0);
            } else if (imuSystem.getAngleOnPlaneX() < BALANCED_X_PLANE - MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(-power, 0);
            } else {
                driveSystem.mecanumDriveXY(0, 0);
            }
            sleep(CORRECTION);
            driveSystem.mecanumDriveXY(0, 0);
            if (imuSystem.getAngleOnPlaneY() < BALANCED_Y_PLANE - MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(0, power);
            } else if (imuSystem.getAngleOnPlaneY() > BALANCED_Y_PLANE + MARGIN_OF_ERROR) {
                driveSystem.mecanumDriveXY(0, -power);
            } else {
                driveSystem.mecanumDriveXY(0, 0);
            }
            sleep(CORRECTION);
            driveSystem.mecanumDriveXY(0, 0);
        }
    }

    void driveToPositionAccel(double revolutions, double maxPower)
    {
        double minPower = 0.1;

        this.driveSystem.setTargetPositionRevs(revolutions);
            double targetInches = driveSystem.ticksToInches(driveSystem.revolutionsToTicks(revolutions));
            telemetry.addLine("Target position " + revolutions + " revolutions or " + targetInches + " inches.");
            telemetry.update();
        /*
            Create a Ramp that will map a distance in revolutions between 0.01 and 1.0
            onto power values between minPower and maxPower.
            When the robot is greater than 1.0 revolution from the target the power
            will be set to maxPower, but when it gets within 1.0 revolutions, the power
            will be ramped down to minPower*/




    /*Position position1 = imuSystem.getPosition();
        Ramp ramp = new ExponentialRamp(driveSystem.revolutionsToTicks(0.01), minPower,
                driveSystem.revolutionsToTicks(1.0), maxPower);

        // Wait until they are done
        driveSystem.setPower(maxPower);
        while (this.driveSystem.anyMotorsBusy())
        {
            telemetry.update();

            this.idle();

            this.driveSystem.adjustPower(ramp);
        }

        // Now that we've arrived, kill the motors so they don't just sit there buzzing
        driveSystem.setPower(0);
        //Position position2 = imuSystem.getPosition();
        double imuDistanceReached = Math.hypot(position1.x, position1.y);
        telemetry.addLine("x distance traveled: ");

        // Always leave the screen looking pretty
        telemetry.update();
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

    /*public void cryptoBox() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);


        parameters.vuforiaLicenseKey = "AfIW5rj/////AAAAGaDrYjvjtkibrSYzQTjEFjJb+NGdODG1LJE2IVqxl0wdLW+9JZ3nIyQF2Hef7GlSLQxR/6SQ3pkFudWmzU48zdcBEYJ+HCwOH3vKFK8gJjuzrcc7nis7JrU+IMTONPctq+JTavtRk+LBhM5bxiFJhEO7CFnDqDDEFc5f720179XJOvZZA0nuCvIqwSslb+ybEVo/G8BDwH1FjGOaH/CxWaXGxVmGd4zISFBsMyrwopDI2T0pHdqvRBQ795QCuJFQjGQUtk9UU3hw/E8Z+oSC36CSWZPdpH3XkKtvSb9teM5xgomeEJ17MdV+XwTYL0iB/aRXZiXRczAtjrcederMUrNqqS0o7XvYS3eW1ViHfynl";


        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);


        //VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        //VuforiaTrackable relicTemplate = relicTrackables.get(0);
        //relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        //relicTrackables.activate();


        telemetry.addData(">", "Just initialized everything boss!");
        telemetry.update();


        driveSystem.mecanumDriveXY(-1, 0);
        sleep(1500);
        driveSystem.mecanumDriveXY(0, 0);
        /*double turnAngle = 162 - imuSystem.getHeading();
        double margin = 2;
        if (turnAngle > margin) {
            turn(turnAngle, 0.3);
            telemetry.addLine("Turning " + turnAngle + " degrees to align with el wall");
        }*/
        /*double xPlaneTrans = -400;
        double yPlaneTrans = 600;
        double transMargin = 10;
        int ttt = 100;
        telemetry.addLine("Just made the pose, rot and trans");
        telemetry.update();
        track();
        while (trans.get(1) < xPlaneTrans - transMargin) {
            driveSystem.mecanumDriveXY(1, 0);
            sleep(ttt);
            telemetry.addLine("First call to track is up next");
            telemetry.update();
            track();
            telemetry.addLine("SUCCESSFULLY called track");
            telemetry.update();
        }
        while (trans.get(1) > xPlaneTrans + transMargin) {
            driveSystem.mecanumDriveXY(1, 0);
            sleep(ttt);
            driveSystem.mecanumDriveXY(0, 0);
            track();
        }
        telemetry.addLine("Lined up on x");
        telemetry.update();
        while (trans.get(2) < yPlaneTrans - transMargin) {
            driveSystem.mecanumDriveXY(0, -1);
            sleep(ttt);
            driveSystem.mecanumDriveXY(0, 0);
            track();
        }

        while (trans.get(2) > yPlaneTrans + transMargin) {
            driveSystem.mecanumDriveXY(0, 1);
            sleep(ttt);
            driveSystem.mecanumDriveXY(0, 0);
            track();
        }
        telemetry.addLine("Lined up on y");
        telemetry.update();
    }*/

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    /*public void track() {
        telemetry.addLine("About to make trackable 1 step 1");
        telemetry.update();
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        telemetry.addLine("About to make trackable 1 step 2");
        telemetry.update();
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        telemetry.addLine("About to make trackable 1 step 3");
        telemetry.update();
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary
        telemetry.addLine("JUST made it");
        telemetry.update();

        pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
        telemetry.addLine("got the pose");
        telemetry.update();
        int doi = 0;

        telemetry.addLine("About to start looping");
        telemetry.update();
        while (pose == null && doi < 1000) {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", vuMark);

                driveSystem.mecanumDriveXY(0, 0);
                pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));

                if (pose != null) {
                    telemetry.addLine("Pose is not null, we found it!");
                    telemetry.update();
                    trans = pose.getTranslation();
                    rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    double tX = trans.get(0);
                    telemetry.addLine("X trans:" + tX);
                    double tY = trans.get(1);
                    telemetry.addLine("Y trans:" + tY);
                    double tZ = trans.get(2);
                    telemetry.addLine("Z trans:" + tZ);

                    double rX = rot.firstAngle;
                    telemetry.addLine("X rotation:" + rX);
                    double rY = rot.secondAngle;
                    telemetry.addLine("Y rotation:" + rY);
                    double rZ = rot.thirdAngle;
                    telemetry.addLine("Z rotation:" + rZ);
                    driveSystem.mecanumDriveXY(0, 0);
                    telemetry.addLine("Found the viewmark");
                }
            }
            else {
                telemetry.addData("VuMark", "not visible");
            }
            telemetry.update();
            doi++;
        }
    }*/

    /*public void vuforiaDrive(int posX, int posY) {
        double minPower = 0.1;
        double maxPower = 1;



        Ramp ramp = new ExponentialRamp(driveSystem.revolutionsToTicks(0.01), minPower,
                driveSystem.revolutionsToTicks(1.0), maxPower);

        // Wait until they are done
        driveSystem.setPower(maxPower);
        while (this.driveSystem.anyMotorsBusy())
        {
            telemetry.update();

            this.idle();

            this.driveSystem.adjustPower(ramp);
        }

        // Now that we've arrived, kill the motors so they don't just sit there buzzing
        driveSystem.setPower(0);
    }*/

    public void driveXY(int x, int y, MecanumDriveSystem driveSystem, double power){
        int error = 100;
        eye.look();
        while (eye.trans.get(1) > x + error || eye.trans.get(1) < x - error || eye.trans.get(2) > y + error || eye.trans.get(2) < y - error) {
            eye.look();
            if (eye.trans.get(1) > x + error) {//z toward and away from the picture
                //driveToPositionMM(eye.trans.get(2) - x, 0, power);
                telemetry.addLine("Too far right");
                telemetry.update();
                driveSystem.mecanumDriveXY(-1, 0);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else if (eye.trans.get(1) < x - error) {
                //driveToPositionMM(x - eye.trans.get(2), 0, power);
                telemetry.addLine("Too far left");
                telemetry.update();
                driveSystem.mecanumDriveXY(1, 0);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else {
                telemetry.addLine("Good on left right");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, 0);
            }
            if (eye.trans.get(2) > y + error) {
                //driveToPositionMM(0, eye.trans.get(1) - y, power);
                telemetry.addLine("Too far forward");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, 1);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else if (eye.trans.get(2) < y - error) {
                //driveToPositionMM(0, y - eye.trans.get(1), power);
                telemetry.addLine("Too far back");
                telemetry.update();
                driveSystem.mecanumDriveXY(0, -1);
                sleep(100);
                driveSystem.mecanumDriveXY(0, 0);
            } else {
                telemetry.addLine("Good on forward-back");
                telemetry.update();
                driveSystem.mecanumDriveXY(0,0);
            }
        }
        telemetry.addLine("YAAAAAAYYYYY!!!!!");
        telemetry.update();
        return;
    }

}
