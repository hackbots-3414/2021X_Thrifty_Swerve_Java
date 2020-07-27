package frc.team2767.swervetesting.command;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team2767.swervetesting.RobotContainer;
import frc.team2767.swervetesting.subsystem.DriveSubsystem;

public final class ZeroGyroCommand extends InstantCommand {

  private static final DriveSubsystem DRIVE = RobotContainer.DRIVE;

  public ZeroGyroCommand() {
    addRequirements(DRIVE);
  }

  @Override
  public void initialize() {
    DRIVE.zeroGyro();
  }
}
