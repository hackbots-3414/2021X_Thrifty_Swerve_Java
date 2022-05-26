package frc.robot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.XboxController;

public class DriverControls {

    private static final Logger LOG = LoggerFactory.getLogger(DriverControls.class);
    private XboxController joystick;

    public DriverControls(int joystickPort) {
        joystick = new XboxController(joystickPort);
    }

    public double getForward() {
        LOG.trace("getForward: {}", joystick.getLeftY());
        return joystick.getLeftY();
    }

    public double getStrafe() {
        LOG.trace("getStrafe: {}", joystick.getLeftX());
        return joystick.getLeftX();
    }

    public double getYaw() {
        LOG.trace("getYaw: {}", -joystick.getRightY());
        return joystick.getRightX();
    }

}