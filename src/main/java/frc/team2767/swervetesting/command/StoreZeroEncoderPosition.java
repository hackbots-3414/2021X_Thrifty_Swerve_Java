// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.team2767.swervetesting.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2767.swervetesting.subsystem.DriveSubsystem;

public class StoreZeroEncoderPosition extends CommandBase {

  private DriveSubsystem driveSubsystem;
  boolean finished = false;
  /** Creates a new StoreZeroEncoderPosition. */
  public StoreZeroEncoderPosition(DriveSubsystem driveSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveSubsystem);
    this.driveSubsystem = driveSubsystem;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSubsystem.saveAzimuthPositions();
    finished = true;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
