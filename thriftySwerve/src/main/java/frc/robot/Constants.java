// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.sensors.SensorVelocityMeasPeriod;
import edu.wpi.first.math.geometry.Translation2d;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final int kTalonConfigTimeout = 10; // ms

  public static final class DriveConstants {
    public static final double kDeadbandAllStick = 0.10;
    public static final double kRobotLength = 0.6;
    public static final double kRobotWidth = 0.6;

    // TODO: verify diameter and run calibration
    // 500 cm calibration = actual / odometry
    public static final double kWheelDiameterInches = 3.0 * (554.0 / 500.0);

    // From: https://github.com/strykeforce/axis-config/
    public static final double kMaxSpeedMetersPerSecond = 3.889;
    public static final double kMaxGimpSpeedMetersPerSecond = 1;

    public static final double kMaxOmega =
        (kMaxSpeedMetersPerSecond / Math.hypot(kRobotLength / 2.0, kRobotWidth / 2.0))
            / 2.0; // wheel locations below
    public static final double kMaxGimpOmega =
        (kMaxGimpSpeedMetersPerSecond / Math.hypot(kRobotLength / 2.0, kRobotWidth / 2.0)) / 2.0;

    // From: https://github.com/strykeforce/axis-config/
    static final double kDriveMotorOutputGear = 21;
    static final double kDriveInputGear = 12;
    static final double kBevelInputGear = 45;
    static final double kBevelOutputGear = 15;
    public static final double kDriveGearRatio =
        (kDriveMotorOutputGear / kDriveInputGear) * (kBevelInputGear / kBevelOutputGear);

    public static Translation2d[] getWheelLocationMeters() {
      final double x = kRobotLength / 2.0; // front-back, was ROBOT_LENGTH
      final double y = kRobotWidth / 2.0; // left-right, was ROBOT_WIDTH
      Translation2d[] locs = new Translation2d[4];
      locs[0] = new Translation2d(x, y); // left front
      locs[1] = new Translation2d(x, -y); // right front
      locs[2] = new Translation2d(-x, y); // left rear
      locs[3] = new Translation2d(-x, -y); // right rear
      return locs;
    }

    public static SupplyCurrentLimitConfiguration getAzimuthCurrentLimit() {
      return new SupplyCurrentLimitConfiguration(true, 10, 15, 0.2);
    }

    public static TalonSRXConfiguration getAzimuthTalonConfig() {
      // constructor sets encoder to Quad/CTRE_MagEncoder_Relative
      TalonSRXConfiguration azimuthConfig = new TalonSRXConfiguration();

      azimuthConfig.primaryPID.selectedFeedbackCoefficient = 1.0;
      azimuthConfig.auxiliaryPID.selectedFeedbackSensor = FeedbackDevice.None;

      azimuthConfig.forwardLimitSwitchSource = LimitSwitchSource.Deactivated;
      azimuthConfig.reverseLimitSwitchSource = LimitSwitchSource.Deactivated;

      azimuthConfig.slot0.kP = 5.0;
      azimuthConfig.slot0.kI = 0.0;
      azimuthConfig.slot0.kD = 100.0;
      azimuthConfig.slot0.kF = 0.9;
      azimuthConfig.slot0.integralZone = 0;
      azimuthConfig.slot0.allowableClosedloopError = 0;
      azimuthConfig.slot0.maxIntegralAccumulator = 0;
      azimuthConfig.motionCruiseVelocity = 800;
      azimuthConfig.motionAcceleration = 10_000;
      azimuthConfig.velocityMeasurementWindow = 64;
      azimuthConfig.voltageCompSaturation = 12;
      azimuthConfig.neutralDeadband = 0.01;
      return azimuthConfig;
    }

    public static SupplyCurrentLimitConfiguration getDriveCurrentLimit() {
      return new SupplyCurrentLimitConfiguration(true, 40, 45, .04);
    }

    public static TalonSRXConfiguration getDriveTalonConfig() {
      TalonSRXConfiguration driveConfig = new TalonSRXConfiguration();
      driveConfig.slot0.kP = 0.045;
      driveConfig.slot0.kI = 0.0005;
      driveConfig.slot0.kD = 0.000;
      driveConfig.slot0.kF = 0.047;
      driveConfig.slot0.integralZone = 500;
      driveConfig.slot0.maxIntegralAccumulator = 75_000;
      driveConfig.slot0.allowableClosedloopError = 0;
      driveConfig.velocityMeasurementPeriod = SensorVelocityMeasPeriod.Period_100Ms;
      driveConfig.velocityMeasurementWindow = 64;
      driveConfig.voltageCompSaturation = 12;
      return driveConfig;
    }
  }
}
