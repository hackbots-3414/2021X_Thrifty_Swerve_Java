// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.VelocityMeasPeriod;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.geometry.Translation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    private static final Logger logger = LoggerFactory.getLogger(Constants.class);
    // Cool Strykeforce trick wiring Vcc to digital input 9 to sense Alpha vs. Beta
    private static final DigitalInput digitalInput = new DigitalInput(9);
    public static boolean isCompBot;
    public static final int kTalonConfigTimeout = 10; // ms

    public Constants() {
        isCompBot = digitalInput.get();
    }

    public static final class DriveConstants {

        public static final double kDeadbandXLock = 0.2;

        // TODO: verify diameter and run calibration
        // 500 cm calibration = actual / odometry
        public static final double kWheelDiameterInches = 3.0 * (584.0 / 501.0);

        // From: https://github.com/strykeforce/axis-config/
        public static final double kMaxSpeedMetersPerSecond = 3.889;

        public static final double kMaxOmega = (kMaxSpeedMetersPerSecond / Math.hypot(0.5461 / 2.0, 0.6477 / 2.0))
                / 2.0; // wheel locations below

        // From: https://github.com/strykeforce/axis-config/
        static final double kDriveMotorOutputGear = 22;
        static final double kDriveInputGear = 48;
        static final double kBevelInputGear = 15;
        static final double kBevelOutputGear = 45;
        public static final double kDriveGearRatio = (kDriveMotorOutputGear / kDriveInputGear)
                * (kBevelInputGear / kBevelOutputGear);

        static {
            logger.debug("kMaxOmega = {}", kMaxOmega);
        }

        public static Translation2d[] getWheelLocationMeters() {
            final double x = 0.5461 / 2.0; // front-back, was ROBOT_LENGTH
            final double y = 0.6477 / 2.0; // left-right, was ROBOT_WIDTH
            Translation2d[] locs = new Translation2d[4];
            locs[0] = new Translation2d(x, y); // left front
            locs[1] = new Translation2d(x, -y); // right front
            locs[2] = new Translation2d(-x, y); // left rear
            locs[3] = new Translation2d(-x, -y); // right rear
            return locs;
        }

        public static TalonSRXConfiguration getAzimuthTalonConfig() {
            // constructor sets encoder to Quad/CTRE_MagEncoder_Relative
            TalonSRXConfiguration azimuthConfig = new TalonSRXConfiguration();

            azimuthConfig.primaryPID.selectedFeedbackCoefficient = 1.0;
            azimuthConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.None;

            azimuthConfig.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
            azimuthConfig.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;

            azimuthConfig.continuousCurrentLimit = 10;
            azimuthConfig.peakCurrentDuration = 0;
            azimuthConfig.peakCurrentLimit = 0;
            azimuthConfig.slot0.kP = 10.0;
            azimuthConfig.slot0.kI = 0.0;
            azimuthConfig.slot0.kD = 100.0;
            azimuthConfig.slot0.kF = 0.0;
            azimuthConfig.slot0.integralZone = 0;
            azimuthConfig.slot0.allowableClosedloopError = 0;
            azimuthConfig.slot0.maxIntegralAccumulator = 0;
            azimuthConfig.motionCruiseVelocity = 800;
            azimuthConfig.motionAcceleration = 10_000;
            azimuthConfig.velocityMeasurementWindow = 64;
            azimuthConfig.voltageCompSaturation = 12;
            return azimuthConfig;
        }

        public static TalonFXConfiguration getDriveTalonConfig() {
            TalonFXConfiguration driveConfig = new TalonFXConfiguration();
            driveConfig.supplyCurrLimit.currentLimit = 0.04;
            driveConfig.supplyCurrLimit.triggerThresholdCurrent = 45;
            driveConfig.supplyCurrLimit.triggerThresholdTime = 40;
            driveConfig.supplyCurrLimit.enable = true;
            driveConfig.slot0.kP = 0.045;
            driveConfig.slot0.kI = 0.0005;
            driveConfig.slot0.kD = 0.000;
            driveConfig.slot0.kF = 0.047;
            driveConfig.slot0.integralZone = 500;
            driveConfig.slot0.maxIntegralAccumulator = 75_000;
            driveConfig.slot0.allowableClosedloopError = 0;
            driveConfig.velocityMeasurementPeriod = VelocityMeasPeriod.Period_100Ms;
            driveConfig.velocityMeasurementWindow = 64;
            driveConfig.voltageCompSaturation = 12;
            return driveConfig;
        }
    }

}
