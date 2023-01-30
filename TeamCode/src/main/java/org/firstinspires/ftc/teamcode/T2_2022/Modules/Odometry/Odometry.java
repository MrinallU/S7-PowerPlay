package org.firstinspires.ftc.teamcode.T2_2022.Modules.Odometry;

import org.firstinspires.ftc.teamcode.Utils.Angle;

public class Odometry {
  // Constants
  public final double ENCODER_WHEEL_DIAMETER = 1.37795;
  private final double ENCODER_TICKS_PER_REVOLUTION = 8154;
  private final double ENCODER_WHEEL_CIRCUMFERENCE = Math.PI * 2.0 * (ENCODER_WHEEL_DIAMETER * 0.5);
  private final double ENCODER_WIDTH =
      12.9665; // DISTANCE BETWEEN FRONT FACING ENCODER WHEELS IN INCHES
  public Pose2d robotPose = new Pose2d(new Translation2d(0, 0), new Rotation2d(0));
  private boolean verbose = false;
  public String outStr = "";

  // Variables
  private double xPos, yPos, angle;
  private double lastLeftEnc = 0, lastRightEnc = 0, lastNormalEnc = 0;

  public Odometry(double xPos, double yPos, double angle) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.angle = angle;
  }

  public Odometry(double angle) {
    this.xPos = 0;
    this.yPos = 0;
    this.angle = angle;
  }

  public Odometry() {
    this(0);
  }

  // Pose Exponential Odometry
  public void updatePosition(double l, double r, double n) {
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
    double avgHeadingRadians = angle + headingChangeRadians / 2.0;
    double cos = Math.cos(avgHeadingRadians);
    double sin = Math.sin(avgHeadingRadians);

    double dx = dxR * sin + dyR * cos;
    double dy = -dxR * cos + dyR * sin;
    angle += headingChangeRadians;
    double dtheta = Angle.normalizeRadians(headingChangeRadians);

    // rather than assuming the robot travels in straight lines between updates
    // a pose exponential assumes non-linearity helping us to reduce drift.
    // uses a special matrix to solve for non-linear pose.
    double sinTheta = Math.sin(dtheta);
    double cosTheta = Math.cos(dtheta);
    double s;
    double c;
    if (Math.abs(dtheta) < 1E-9) { // approximation for low values of theta
      s = 1.0 - 1.0 / 6.0 * dtheta * dtheta;
      c = 0.5 * dtheta;
    } else {
      s = sinTheta / dtheta;
      c = (1 - cosTheta) / dtheta;
    }

    // Initialize the Twist
    Transform2d transform =
        new Transform2d(
            new Translation2d(dx * s - dy * c, dx * c + dy * s),
            new Rotation2d(cosTheta, sinTheta));
    Pose2d newPose =
        robotPose.transformBy(transform); // Use the twist to transform the robot location
    robotPose = new Pose2d(newPose.getTranslation(), new Rotation2d(angle));
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
    return robotPose.getTranslation().getX();
  }

  public double getY() {
    return robotPose.getTranslation().getY();
  }

  public double getAngle() {
    return Angle.normalize(robotPose.getRotation().getDegrees());
  }

  public void resetOdometry(double xPos, double yPos, double angle) {
    this.xPos = xPos;
    this.yPos = yPos;
    this.angle = angle;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  private String format(double num) {
    return String.format("%.3f", num);
  }
}
