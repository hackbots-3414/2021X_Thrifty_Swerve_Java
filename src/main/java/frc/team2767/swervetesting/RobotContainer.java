package frc.team2767.swervetesting;

import edu.wpi.first.wpilibj.RobotBase;
import frc.team2767.swervetesting.command.TeleOpDriveCommand;
import frc.team2767.swervetesting.control.Controls;
import frc.team2767.swervetesting.subsystem.DriveSubsystem;
import org.strykeforce.thirdcoast.telemetry.TelemetryController;
import org.strykeforce.thirdcoast.telemetry.TelemetryService;

public class RobotContainer {
  public static TelemetryService TELEMETRY;
  public static DriveSubsystem DRIVE;
  public static Controls CONTROLS;

  public RobotContainer() {

    if (RobotBase.isReal()) {

      TELEMETRY = new TelemetryService(TelemetryController::new);

      DRIVE = new DriveSubsystem();
      CONTROLS = new Controls();

      TELEMETRY.start();

      DRIVE.setDefaultCommand(new TeleOpDriveCommand());
    }
  }
}
