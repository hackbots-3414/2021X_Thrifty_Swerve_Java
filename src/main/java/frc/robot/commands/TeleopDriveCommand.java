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
import frc.robot.util.CartesianPolar;

public class TeleopDriveCommand extends CommandBase {
  private DriveSubsystem drive = RobotContainer.DRIVE;
  private DriverControls driverControls = RobotContainer.DRIVER_CONTROLS;
  private CartesianPolar cartesianPolar = RobotContainer.CARTESIAN_POLAR;

  int currMotorTicks = 0;
  int prevMotorTicks = 0;
  int tickDifference = 0;
  int tickOffset = 0;
  
  /** Creates a new TeleopDriveCommand. */
  public TeleopDriveCommand() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currMotorTicks = (int) Math.round(cartesianPolar.cartesianToTheta(driverControls.getStrafe(), driverControls.getForward()));
    
    tickDifference = currMotorTicks - prevMotorTicks;

    if (Math.abs(tickDifference) >= 2048) {
      if (tickDifference >= 2048) {
        tickOffset -= 4096;
      }
      else if (tickDifference <= -2048) {
        tickOffset += 4096;
      }
    }

    if (Math.abs(driverControls.getStrafe()) == 0 && Math.abs(driverControls.getForward()) == 0) {
      currMotorTicks = prevMotorTicks;
    }

    for (int i = 0; i < drive.getSwerveModules().length; i++) {
      drive.setSwerveModuleYaw(currMotorTicks + tickOffset, i);
    }

    prevMotorTicks = currMotorTicks;
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
