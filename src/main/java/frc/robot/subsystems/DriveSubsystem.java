// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
//
// Pulled from Strykeforce/infiniterecharge - many thanks to Strykeforce and their thirdcoast drive
//
package frc.robot.subsystems;

import static frc.robot.Constants.kTalonConfigTimeout;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.strykeforce.swerve.SwerveDrive;
import org.strykeforce.swerve.SwerveModule;
import org.strykeforce.swerve.TalonSwerveModule;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  private static final Logger LOG = LoggerFactory.getLogger(DriveSubsystem.class);
  private final SwerveDrive swerveDrive;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    var moduleBuilder = new TalonSwerveModule.Builder().driveGearRatio(DriveConstants.kDriveGearRatio)
        .wheelDiameterInches(DriveConstants.kWheelDiameterInches)
        .driveMaximumMetersPerSecond(DriveConstants.kMaxSpeedMetersPerSecond);

    AHRS ahrs = new AHRS(Port.kOnboard);

    TalonSwerveModule[] swerveModules = new TalonSwerveModule[4];
    Translation2d[] wheelLocations = DriveConstants.getWheelLocationMeters();

    for (int i = 0; i < 4; i++) {
      var azimuthTalon = new TalonSRX(i + 10);
      azimuthTalon.configFactoryDefault(kTalonConfigTimeout);
      azimuthTalon.configAllSettings(DriveConstants.getAzimuthTalonConfig(), kTalonConfigTimeout);
      azimuthTalon.enableCurrentLimit(true);
      azimuthTalon.enableVoltageCompensation(true);
      azimuthTalon.setNeutralMode(NeutralMode.Coast);
      if (i==0||i==2) {
        azimuthTalon.setSensorPhase(true);
        azimuthTalon.setInverted(true);
      }

      LOG.trace("Constructing azimuth {}", azimuthTalon.getDeviceID());

      var driveTalon = new TalonFX(i + 20, Constants.kCanivoreName);
      driveTalon.configFactoryDefault(kTalonConfigTimeout);
      driveTalon.configAllSettings(DriveConstants.getDriveTalonConfig(), kTalonConfigTimeout);
      driveTalon.enableVoltageCompensation(true);
      driveTalon.setNeutralMode(NeutralMode.Coast);

      swerveModules[i] = moduleBuilder.azimuthTalon(azimuthTalon).driveTalon(driveTalon).wheelLocationMeters(wheelLocations[i]).build();

      swerveModules[i].loadAndSetAzimuthZeroReference();
      
      LOG.trace("Loaded azimuth {} at {}", ((TalonSwerveModule) swerveModules[i]).getAzimuthTalon().getDeviceID(), ((TalonSwerveModule) swerveModules[i]).getAzimuthTalon().getSelectedSensorPosition());
    }

    swerveDrive = new SwerveDrive(swerveModules);
    swerveDrive.resetGyro();
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
  public SwerveModule[] getSwerveModules() {
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
    // swerveDrive.periodic();
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
    // setGyroOffset(offsetRads);
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
      LOG.trace("Storing azimuth {} at {}", ((TalonSwerveModule) swerveModules[i]).getAzimuthTalon().getDeviceID(), ((TalonSwerveModule) swerveModules[i]).getAzimuthTalon().getSelectedSensorPosition());
    }
    LOG.info("Stored zeros");
  }

  public void testAllMotors(double speed) {
    SwerveModule[] swerveModules = getSwerveModules();
    for (int i = 0; i < swerveModules.length; i++) {
      ((TalonSwerveModule) swerveModules[i]).getAzimuthTalon().set(ControlMode.Position, 2048);
    }
  }
  
  public void setSwerveModuleYaw(int ticks, int motor) {
    // ticks is the position of the motor, 4096 being 360 degrees.
    // motor is the index of the motor, in can id order starting from 0.
    SwerveModule[] swerveModules = getSwerveModules();
    ((TalonSwerveModule) swerveModules[motor]).getAzimuthTalon().set(ControlMode.Position, ticks);
  }
  
  public void setSwerveModuleVelocity(double velocity, int motor) {
    SwerveModule[] swerveModules = getSwerveModules();
    ((TalonSwerveModule) swerveModules[motor]).getDriveTalon().set(ControlMode.Velocity, velocity);
    // TODO: figure out how to drive talonFX motors directly
  }
}