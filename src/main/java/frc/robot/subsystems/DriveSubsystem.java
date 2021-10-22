// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//
// Pulled from Strykeforce/infiniterecharge - many thanks to Strykeforce and their thirdcoast drive
//
package frc.robot.subsystems;

import static frc.robot.Constants.kTalonConfigTimeout;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.strykeforce.swerve.SwerveDrive;
import org.strykeforce.swerve.SwerveModule;
import org.strykeforce.swerve.TalonSwerveModule;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  private static final Logger LOG = LoggerFactory.getLogger(DriveSubsystem.class);
  private final SwerveDrive swerveDrive;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    var moduleBuilder = new TalonSwerveModule.Builder().driveGearRatio(DriveConstants.kDriveGearRatio)
        .wheelDiameterInches(DriveConstants.kWheelDiameterInches)
        .driveMaximumMetersPerSecond(DriveConstants.kMaxSpeedMetersPerSecond);

    TalonSwerveModule[] swerveModules = new TalonSwerveModule[4];
    Translation2d[] wheelLocations = DriveConstants.getWheelLocationMeters();

    for (int i = 0; i < 4; i++) {
      var azimuthTalon = new TalonSRX(i + 10);
      azimuthTalon.configFactoryDefault(kTalonConfigTimeout);
      azimuthTalon.configAllSettings(DriveConstants.getAzimuthTalonConfig(), kTalonConfigTimeout);
      azimuthTalon.enableCurrentLimit(true);
      azimuthTalon.enableVoltageCompensation(true);
      azimuthTalon.setNeutralMode(NeutralMode.Coast);

      LOG.trace("Constructing azimuth {}", azimuthTalon.getDeviceID());

      var driveTalon = new TalonFX(i + 20);
      driveTalon.configFactoryDefault(kTalonConfigTimeout);
      driveTalon.configAllSettings(DriveConstants.getDriveTalonConfig(), kTalonConfigTimeout);
      driveTalon.enableVoltageCompensation(true);
      driveTalon.setNeutralMode(NeutralMode.Brake);

      swerveModules[i] = moduleBuilder.azimuthTalon(azimuthTalon).driveTalon(driveTalon)
          .wheelLocationMeters(wheelLocations[i]).build();

      swerveModules[i].loadAndSetAzimuthZeroReference();
    }

    swerveDrive = new SwerveDrive(swerveModules);
    swerveDrive.resetGyro();
    swerveDrive.setGyroOffset(Rotation2d.fromDegrees(180));
  }

  /**
   * Returns the swerve drive kinematics object for use during trajectory
   * configuration.
   *
   * @return the configured kinemetics object
   */
  public SwerveDriveKinematics getSwerveDriveKinematics() {
    return swerveDrive.getKinematics();
  }

  /** Returns the configured swerve drive modules. */
  private SwerveModule[] getSwerveModules() {
    return swerveDrive.getSwerveModules();
  }

  /**
   * Resets the robot's position on the field.
   *
   * @param pose the current pose
   */
  public void resetOdometry(Pose2d pose) {
    swerveDrive.resetOdometry(pose);
    LOG.info("reset odometry with pose = {}", pose);
  }

  /**
   * Returns the position of the robot on the field.
   *
   * @return the pose of the robot (x and y ane in meters)
   */
  public Pose2d getPoseMeters() {
    return swerveDrive.getPoseMeters();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    swerveDrive.periodic();
  }

  /**
   * Drive the robot with given x, y, and rotational velocities with open-loop
   * velocity control.
   */
  public void drive(double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond) {
    swerveDrive.drive(vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond, true);
  }

  /**
   * Move the robot with given x, y, and rotational velocities with closed-loop
   * velocity control.
   */
  public void move(double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond,
      boolean isFieldOriented) {
    swerveDrive.move(vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond, isFieldOriented);
  }

  public void resetGyro() {
    swerveDrive.resetGyro();
  }

  public void setGyroOffset(Rotation2d offsetRads) {
    swerveDrive.setGyroOffset(offsetRads);
  }

  public Rotation2d getHeading() {
    return swerveDrive.getHeading();
  }

  public void xLockSwerveDrive() {
    ((TalonSwerveModule) swerveDrive.getSwerveModules()[0]).setAzimuthRotation2d(Rotation2d.fromDegrees(45));
    ((TalonSwerveModule) swerveDrive.getSwerveModules()[1]).setAzimuthRotation2d(Rotation2d.fromDegrees(-45));
    ((TalonSwerveModule) swerveDrive.getSwerveModules()[2]).setAzimuthRotation2d(Rotation2d.fromDegrees(-45));
    ((TalonSwerveModule) swerveDrive.getSwerveModules()[3]).setAzimuthRotation2d(Rotation2d.fromDegrees(45));
  }

  public void storeZeroPositions() {
    SwerveModule[] swerveModules = getSwerveModules();
    for (int i = 0; i < swerveModules.length; i++) {
      swerveModules[i].storeAzimuthZeroReference();
      LOG.trace("Storing azimuth {}", ((TalonSwerveModule) swerveModules[i]).getAzimuthTalon().getDeviceID());
    }
    LOG.info("Stored zeros");
  }

}
