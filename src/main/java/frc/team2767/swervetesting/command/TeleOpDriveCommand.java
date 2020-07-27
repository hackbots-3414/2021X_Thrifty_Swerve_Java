package frc.team2767.swervetesting.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team2767.swervetesting.RobotContainer;
import frc.team2767.swervetesting.control.DriverControls;
import frc.team2767.swervetesting.subsystem.DriveSubsystem;

public final class TeleOpDriveCommand extends CommandBase {

  private static final double DEADBAND = 0.05;
  private static final DriveSubsystem DRIVE = RobotContainer.DRIVE;
  private static final DriverControls controls = RobotContainer.CONTROLS.getDriverControls();

  public TeleOpDriveCommand() {
    addRequirements(DRIVE);
  }

  @Override
  public void execute() {
    double forward = deadband(controls.getForward());
    double strafe = deadband(controls.getStrafe());
    double azimuth = deadband(controls.getYaw());

    DRIVE.drive(forward, strafe, azimuth);
  }

  @Override
  public void end(boolean interrupted) {
    DRIVE.drive(0.0, 0.0, 0.0);
  }

  private double deadband(double value) {
    if (Math.abs(value) < DEADBAND) return 0.0;
    return value;
  }
}
