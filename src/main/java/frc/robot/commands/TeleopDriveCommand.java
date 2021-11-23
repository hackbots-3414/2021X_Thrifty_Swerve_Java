// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import org.strykeforce.thirdcoast.util.ExpoScale;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.DriverControls;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class TeleopDriveCommand extends CommandBase {
  private DriveSubsystem drive = RobotContainer.DRIVE;
  private DriverControls driverControls = RobotContainer.DRIVER_CONTROLS;
  
  private static final double FORWARD_DEADBAND = 0.05;
  private static final double STRAFE_DEADBAND = 0.05;
  private static final double YAW_DEADBAND = 0.01;

  private static final double FORWARD_XPOSCALE = 0.6;
  private static final double STRAFE_XPOSCALE = 0.6;
  private static final double YAW_XPOSCALE = 0.75;

  private final ExpoScale forwardScale;
  private final ExpoScale strafeScale;
  private final ExpoScale yawScale;

  
  /** Creates a new TeleopDriveCommand. */
  public TeleopDriveCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
    forwardScale = new ExpoScale(FORWARD_DEADBAND, FORWARD_XPOSCALE);
    strafeScale = new ExpoScale(STRAFE_DEADBAND, STRAFE_XPOSCALE);
    yawScale = new ExpoScale(YAW_DEADBAND, YAW_XPOSCALE);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // TODO Replate with something for the driverControls...
    double forward = forwardScale.apply(driverControls.getForward()); // CLENSE THE WORLD OF LINE NAME LENGTH
    double strafe  = strafeScale. apply(driverControls. getStrafe());
    double yaw     = yawScale.    apply(driverControls.    getYaw());
    forward        = forward * Constants.DriveConstants.kMaxSpeedMetersPerSecond;
    strafe         = strafe *  Constants.DriveConstants.kMaxSpeedMetersPerSecond;
    yaw            = yaw *     Constants.DriveConstants.               kMaxOmega;

    drive.drive(forward, strafe, yaw);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drive.drive(0, 0, 0);
  }

  // Returns true when the command should end.
  // for a "default" command like this, ALWAYS return false
  @Override
  public boolean isFinished() {
    return false;
  }
}
