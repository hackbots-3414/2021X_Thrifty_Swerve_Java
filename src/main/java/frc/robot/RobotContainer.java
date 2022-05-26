package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.StrykeForceTeleOp;
import frc.robot.subsystems.DriveSubsystem;

public class RobotContainer {
  public static DriveSubsystem DRIVE = new DriveSubsystem();
  public static DriverControls DRIVER_CONTROLS = new DriverControls(0);

  public RobotContainer() {
    DRIVE.setDefaultCommand(new StrykeForceTeleOp());
    configureButtonBindings();
  }

  private void configureButtonBindings() {
  }

  public Command getAutonomousCommand() {
    return null;
  }


}
