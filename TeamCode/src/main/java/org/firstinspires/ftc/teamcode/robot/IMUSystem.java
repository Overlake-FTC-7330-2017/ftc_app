package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.*;

/**
 * This is NOT an opmode.
 *
 *
 */
public class IMUSystem
{
    public BNO055IMU imu;
    public BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

    /* local OpMode members. */
    HardwareMap hwMap           =  null;

    /* Constructor */
    public IMUSystem(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        // Save reference to Hardware map
        this.hwMap = hwMap;

        this.parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        this.parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        this.parameters.loggingEnabled = true;
        this.parameters.loggingTag = "BNO055";
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        this.imu = this.hwMap.get(BNO055IMU.class, "imu");
        this.imu.initialize(parameters);

        // Enable reporting of position using the naive integrator
        imu.startAccelerationIntegration(new Position(), new Velocity(), 100); //TODO: Is the last parameter good?
        //imu.startAccelerationIntegration(new Position(DistanceUnit.MM, imu.getLinearAcceleration().xAccel, imu.getLinearAcceleration().yAccel, 0, 1000), new Velocity(), 1000);
    }

    public double getHeading() {
        Orientation orientation   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
        return orientation.firstAngle;
    }

    public double getAngleOnPlaneX(){
        Orientation orientation   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
        return orientation.thirdAngle;
    }

    public double getAngleOnPlaneY() {
        Orientation orientation   = imu.getAngularOrientation().toAxesReference(AxesReference.INTRINSIC).toAxesOrder(AxesOrder.ZYX);
        return orientation.secondAngle;
    }

    public double getLinearAccelerationX() {
        Acceleration acceleration = imu.getAcceleration();
        return acceleration.xAccel;
    }

    public double getLinearAccelerationY() {
        Acceleration acceleration = imu.getAcceleration();
        return acceleration.yAccel;
    }

    public double getLinearAceelerationZ() {
        Acceleration acceleration = imu.getAcceleration();
        return acceleration.zAccel;
    }

    public Position getPosition() {
        Position position = imu.getPosition();
        return position;
    }
}

