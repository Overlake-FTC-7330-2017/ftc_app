package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robot.systems.ClawSystem;
import org.firstinspires.ftc.teamcode.robot.systems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.robot.systems.Eye;
import org.firstinspires.ftc.teamcode.robot.systems.IMUSystem;
import org.firstinspires.ftc.teamcode.robot.systems.MecanumDriveSystem;
import org.firstinspires.ftc.teamcode.robot.systems.ParallelLiftSystem;
import org.firstinspires.ftc.teamcode.robot.systems.PixySystem;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;
import org.firstinspires.ftc.teamcode.util.logger.Logger;

/**
 * Created by EvanCoulson on 10/11/17.
 */

public abstract class BaseOpMode extends LinearOpMode {
    public ConfigParser config;
    public MecanumDriveSystem driveSystem;
    public IMUSystem imuSystem;
    public Eye eye;
    public ElevatorSystem elevator;
    public PixySystem pixySystem;
    public ClawSystem claw;
    public ParallelLiftSystem parallelLiftSystem;
    public Logger logger;

    public BaseOpMode(String opModeName) {
        this.config = new ConfigParser(opModeName + ".omc");
        this.logger = new Logger(opModeName);
    }

    protected void initSystems() {
        this.driveSystem = new MecanumDriveSystem(this);
        this.imuSystem = new IMUSystem(this);
        this.eye = new Eye(this);
        this.elevator = new ElevatorSystem(this);
        this.claw = new ClawSystem(this);
        this.parallelLiftSystem = new ParallelLiftSystem(this);
        this.pixySystem = null;
    }
}
