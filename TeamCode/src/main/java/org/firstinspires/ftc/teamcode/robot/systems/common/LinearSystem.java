package org.firstinspires.ftc.teamcode.robot.systems.common;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by MichaelSimpson on 3/15/2018.
 */

public class LinearSystem extends System {

    protected static final double ABSOLUTE_MAX = 1.0;
    protected static final double ABSOLUTE_MIN = 0.0;

    protected int max;

    /**
     * An array of values between the absolute min and max of size TBD by subclass containing
     * a list of positions in an order declared
      */
    protected double[] positions;

    public LinearSystem(OpMode opMode, String systemName) {
        super(opMode, systemName);

        this.positions = new double[config.getInt("arraySize")];
        this.max = positions.length - 1;

        for (int i = 0; i < positions.length; i++) {
            positions[i] = config.getDouble("position" + i);
        }
    }
}
