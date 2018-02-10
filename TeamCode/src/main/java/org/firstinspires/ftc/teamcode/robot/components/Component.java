package org.firstinspires.ftc.teamcode.robot.components;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.config.ConfigParser;
import org.firstinspires.ftc.teamcode.util.logger.Logger;

/**
 * Created by EvanCoulson on 2/10/18.
 */

public abstract class Component {
    public ConfigParser config;
    public Logger logger;

    public Component(String componentName) {
        this.config = new ConfigParser(componentName + ".omc");
        this.logger = new Logger(componentName);
    }

}
