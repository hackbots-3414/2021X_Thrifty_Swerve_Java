// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
// import frc.robot.commands.TestMotor11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Add your docs here. */
public class DriverControls {

    public static int driveMode = 1;

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

    public void configureButtonBindings() {
        JoystickButton buttonA = new JoystickButton(joystick, XboxController.Button.kA.value);
        buttonA.whenPressed(RobotContainer.TEST_MOTOR_11);
        JoystickButton buttonB = new JoystickButton(joystick, XboxController.Button.kB.value);
        buttonB.whenPressed(RobotContainer.SET_ZERO);
    }

}