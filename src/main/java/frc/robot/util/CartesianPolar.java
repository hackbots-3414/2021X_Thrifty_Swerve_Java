// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.util;

/* Add your docs here. */
public class CartesianPolar {
    public double cartesianToTheta(double x, double y) {
        // returns angle of x and y coords in encoder ticks (4096 ticks == 360 deg)
        return (Math.atan2(y, x) * (2048 / Math.PI)) + 1024;
    }
    public double cartesianToMagnitude(double x, double y) {
        return Math.pow((Math.pow(x, 2) + Math.pow(y, 2)), 0.5);
    }
}
