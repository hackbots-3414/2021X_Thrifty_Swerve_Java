package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
// import org.strykeforce.thirdcoast.util.ExpoScale;
// import frc.robot.Constants;
// import frc.robot.DriverControls;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DriveSubsystem;

public class TestMotor11 extends CommandBase {
    private DriveSubsystem drive = RobotContainer.DRIVE;
    private long startTimeMillis;
    public TestMotor11() {
        addRequirements(drive);
    }
    
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drive.drive(0,0,0);
        startTimeMillis = System.currentTimeMillis();
    }


    @Override
    public void execute() {
        for (int i = 0; i < 4; i++) {
            drive.setSwerveModuleVelocity(0.1, i);
        }
        
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        // drive.drive(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        // return true;
        return startTimeMillis + 1000 <= System.currentTimeMillis();
    }
    
}
