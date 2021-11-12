// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Add your docs here. */
public class DriverControls {
    private static final Logger LOG = LoggerFactory.getLogger(DriverControls.class);
    private XboxController joystick;

    public DriverControls(int joystickPort) {
        joystick = new XboxController(joystickPort);
    }

    public double getForward() {
        LOG.trace("getForward: {}", -joystick.getX(Hand.kLeft));
        return -joystick.getY(Hand.kLeft);
    }

    public double getStrafe() {
        LOG.trace("getStrafe: {}", -joystick.getY(Hand.kLeft));
        return -joystick.getX(Hand.kLeft);
    }

    public double getYaw() {
        LOG.trace("getYaw: {}", -joystick.getY(Hand.kRight));
        return -joystick.getX(Hand.kRight);
    }

}