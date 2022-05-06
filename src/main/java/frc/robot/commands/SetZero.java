/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetZero extends CommandBase {

  private static final Logger LOG = LoggerFactory.getLogger(SetZero.class);

  private DriveSubsystem drive = RobotContainer.DRIVE;

  /**
   * Creates a new SetZero.
   */
  public SetZero() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drive);
    LOG.trace("Constructed SetZero");
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    LOG.trace("Init SetZero");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drive.storeZeroPositions();
    LOG.warn("Execute zeroing");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    LOG.trace("SetZero inturupted");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    LOG.info("Finshed zeroing");
    return true;
  }
}
