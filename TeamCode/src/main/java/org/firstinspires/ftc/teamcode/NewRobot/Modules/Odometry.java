package org.firstinspires.ftc.teamcode.NewRobot.Modules;

import org.firstinspires.ftc.teamcode.Utils.Angle;
import org.firstinspires.ftc.teamcode.Utils.Rotation2d;

public class Odometry {
  // Constants
  public final double ENCODER_WHEEL_DIAMETER = 1.37795;
  private final double ENCODER_TICKS_PER_REVOLUTION = 8154;
  private final double ENCODER_WHEEL_CIRCUMFERENCE = Math.PI * 2.0 * (ENCODER_WHEEL_DIAMETER * 0.5);
  private final double ENCODER_WIDTH =
          12.9665; // DISTANCE BETWEEN FRONT FACING ENCODER WHEELS IN INCHES
  private boolean verbose = false;
  public String outStr = "";

  // Variables
  private double xPos, yPos;
  public Rotation2d angle;
  private double lastLeftEnc = 0, lastRightEnc = 0, lastNormalEnc = 0;

  public Odometry(double xPos, double yPos, double angle) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.angle = new Rotation2d(Angle.degrees_to_radians(angle));
  }

  public Odometry(double angle) {
    this.xPos = 0;
    this.yPos = 0;
    this.angle = new Rotation2d(Angle.degrees_to_radians(angle));
  }

  public Odometry() {
    this(0);
  }

  // Pose Exponential Odometry
  public void updatePosition(double l, double r, double n) {
    // GM0
    double dR = r - lastRightEnc;
    double dL = l - lastLeftEnc;
    double dN = n - lastNormalEnc;
    lastNormalEnc = n;
    lastLeftEnc = l;
    lastRightEnc = r;
    double rightDist = dR * ENCODER_WHEEL_CIRCUMFERENCE / ENCODER_TICKS_PER_REVOLUTION;
    double leftDist = -dL * ENCODER_WHEEL_CIRCUMFERENCE / ENCODER_TICKS_PER_REVOLUTION;
    double dyR = 0.5 * (rightDist + leftDist);
    double headingChangeRadians = (rightDist - leftDist) / ENCODER_WIDTH;
    double dxR = -dN * ENCODER_WHEEL_CIRCUMFERENCE / ENCODER_TICKS_PER_REVOLUTION;
    double avgHeadingRadians = angle.getRadians() + headingChangeRadians / 2.0;
    double cos = Math.cos(avgHeadingRadians);
    double sin = Math.sin(avgHeadingRadians);

    double dx = dxR * sin + dyR * cos;
    double dy = -dxR * cos + dyR * sin;
    angle = new Rotation2d(headingChangeRadians+angle.getRadians());
    double dtheta = Angle.normalizeRadians(headingChangeRadians);

    // rather than assuming the robot travels in straight lines between updates
    // a pose exponential assumes non-linearity helping us to reduce drift.
    // uses a special matrix to solve for non-linear pose. This helps to account for
    // the varying loop times in the control hub.
    double sinTheta = Math.sin(dtheta);
    double cosTheta = Math.cos(dtheta);
    double s;
    double c;
    if (Math.abs(dtheta) < 1E-9) {
      // taylor series approximarion for low vals of theta
      s = 1.0 - 1.0 / 6.0 * dtheta * dtheta;
      c = 0.5 * dtheta;
    } else {
      s = sinTheta / dtheta;
      c = (1 - cosTheta) / dtheta;
    }
    dx = dx * s - dy * c;
    dy = dx * c + dy * s;
    double rotatedDx = dx * angle.getCos() - dy * angle.getSin();
    double rotatedDy = dx * angle.getSin() + dy * angle.getCos();
    xPos += rotatedDx; yPos += rotatedDy;
    angle.plus(new Rotation2d(cosTheta, sinTheta));
  }

  public double normalizeAngle(double rawAngle) {
    double scaledAngle = rawAngle % 360;
    if (scaledAngle < 0) {
      scaledAngle += 360;
    }

    if (scaledAngle > 180) {
      scaledAngle -= 360;
    }

    return scaledAngle;
  }

  public String displayPositions() {
    return outStr;
  }

  public double getX() {
    return xPos;
  }

  public double getY() {
    return yPos;
  }

  public double getAngle() {
    return Angle.normalize(angle.getDegrees());
  }

  private String format(double num) {
    return String.format("%.3f", num);
  }
}
