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

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  private static final Logger LOG = LoggerFactory.getLogger(DriveSubsystem.class);
  private final SwerveDrive swerveDrive;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    TalonSwerveModule.Builder moduleBuilder = new TalonSwerveModule.Builder().driveGearRatio(DriveConstants.kDriveGearRatio).wheelDiameterInches(DriveConstants.kWheelDiameterInches).driveMaximumMetersPerSecond(DriveConstants.kMaxSpeedMetersPerSecond);

    TalonSwerveModule[] swerveModules = new TalonSwerveModule[4];
    Translation2d[] wheelLocations = DriveConstants.getWheelLocationMeters();

    for (int i = 0; i < 4; i++) {
      TalonSRX turningMotor = new TalonSRX(i + 10);
      turningMotor.configFactoryDefault(kTalonConfigTimeout);
      turningMotor.configAllSettings(DriveConstants.getAzimuthTalonConfig(), kTalonConfigTimeout);
      turningMotor.enableCurrentLimit(true);
      turningMotor.enableVoltageCompensation(true);
      turningMotor.setNeutralMode(NeutralMode.Coast);
      if (i==1||i==3) {
        turningMotor.setSensorPhase(true);
        turningMotor.setInverted(true);
      }

      TalonFX driveMotor = new TalonFX(i + 20, Constants.kCanivoreName);
      driveMotor.configFactoryDefault(kTalonConfigTimeout);
      driveMotor.configAllSettings(DriveConstants.getDriveTalonConfig(), kTalonConfigTimeout);
      driveMotor.enableVoltageCompensation(true);
      driveMotor.setNeutralMode(NeutralMode.Brake);

      swerveModules[i] = moduleBuilder.azimuthTalon(turningMotor).driveTalon(driveMotor).wheelLocationMeters(wheelLocations[i]).build();

      swerveModules[i].loadAndSetAzimuthZeroReference();
    }

    swerveDrive = new SwerveDrive(swerveModules);
    storeZeroPositions();
    swerveDrive.resetGyro();
    swerveDrive.setGyroOffset(Rotation2d.fromDegrees(180));
  }

  public SwerveDriveKinematics getSwerveDriveKinematics() {
    return swerveDrive.getKinematics();
  }

  public SwerveModule[] getSwerveModules() {
    return swerveDrive.getSwerveModules();
  }

  public void resetOdometry(Pose2d pose) {
    swerveDrive.resetOdometry(pose);
    LOG.info("reset odometry with pose = {}", pose);
  }

  public Pose2d getPoseMeters() {
    return swerveDrive.getPoseMeters();
  }

  @Override
  public void periodic() {}

  public void drive(double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond) {
    swerveDrive.drive(vxMetersPerSecond, vyMetersPerSecond, omegaRadiansPerSecond, true);
  }

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

  public void storeZeroPositions() {
    SwerveModule[] swerveModules = getSwerveModules();
    for (int i = 0; i < swerveModules.length; i++) {
      swerveModules[i].storeAzimuthZeroReference();
      LOG.trace("Storing azimuth {}", ((TalonSwerveModule) swerveModules[i]).getAzimuthTalon().getDeviceID());
    }
    LOG.info("Stored zeros");
  }
}