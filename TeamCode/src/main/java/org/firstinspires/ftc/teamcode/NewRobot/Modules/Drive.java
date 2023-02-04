package org.firstinspires.ftc.teamcode.NewRobot.Modules;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.ArrayList;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.T2_2022.Base;
import org.firstinspires.ftc.teamcode.Utils.Angle;
import org.firstinspires.ftc.teamcode.Utils.Motor;
import org.firstinspires.ftc.teamcode.Utils.Point;

public class Drive extends Base {

  protected Motor fLeftMotor, bLeftMotor, fRightMotor, bRightMotor;
  protected Motor odoR, odoL, odoN;
  protected BNO055IMU gyro;
  protected Odometry odometry;
  protected OpMode opMode;
  protected List<LynxModule> allHubs;

  Telemetry output;
  public boolean dropSlides = false, dropSlides2 = false, dropSlides3 = false, dropSlides4 = false;

  public Drive(
      Motor fLeftMotor,
      Motor bLeftMotor,
      Motor fRightMotor,
      Motor bRightMotor,
      Motor odoL,
      Motor odoR,
      Motor odoN,
      BNO055IMU gyro,
      OpMode m,
      int xPos,
      int yPos,
      int angle,
      List<LynxModule> allHubs,
      Telemetry t) {
    this.fLeftMotor = fLeftMotor;
    this.fRightMotor = fRightMotor;
    this.bLeftMotor = bLeftMotor;
    this.bRightMotor = bRightMotor;
    this.odoL = odoL;
    this.odoR = odoR;
    this.odoN = odoN;
    this.gyro = gyro;
    this.opMode = m;
    this.allHubs = allHubs;
    this.output = t;
    odometry = new Odometry(xPos, yPos, angle);
  }

  // Very similar to the carrot chasing algo (https://arxiv.org/abs/2012.13227)
  public void ChaseTheCarrot(
      ArrayList<Point> wp,
      int switchTolerance,
      double heading,
      double error,
      double angleError,
      double normalMovementConstant,
      double finalMovementConstant,
      double turnConstant,
      double timeout) {
    ElapsedTime time = new ElapsedTime();
    int pt = 0;
    time.reset();
    while ((pt < wp.size() - 1
            || (Math.abs(getX() - wp.get(wp.size() - 1).xP) > error
                || Math.abs(getY() - wp.get(wp.size() - 1).yP) > error
                || (heading == Double.MAX_VALUE
                    ? Math.abs(wp.get(wp.size() - 1).ang - odometry.getAngle()) > angleError
                    : Math.abs(heading - odometry.getAngle()) > angleError)))
        && time.milliseconds() < timeout) {
      update();
      double x = getX();
      double y = getY();
      double theta = odometry.getAngle();

      Point destPt;
      while (getRobotDistanceFromPoint(wp.get(pt)) <= switchTolerance && pt != wp.size() - 1) {
        update();
        pt++;
      }

      /*
            splineAngle = Math.atan2(yDiff, xDiff);
            double dist = getRobotDistanceFromPoint(nxtP); // mtp 2.0
      double relAngToP =
          Angle.normalizeRadians(
              splineAngle - (Math.toRadians(theta) - Math.toRadians(90))); // mtp 2.0
      double relX = Math.sin(relAngToP) * dist, relY = Math.cos(relAngToP) * dist;
      double xPow = (relX / (Math.abs(relY) + Math.abs(relX))) * driveSpeedCap,
          yPow = (relY / (Math.abs(relX) + Math.abs(relY))) * driveSpeedCap;
       */
      destPt = wp.get(pt);
      double xDiff = destPt.xP - x;
      double yDiff = destPt.yP - y;
      double angleDiff =
          heading == Double.MAX_VALUE
              ? wp.get(wp.size() - 1).ang - odometry.getAngle()
              : heading - theta;
      double xPow, yPow, turnPow;

      turnPow = angleDiff * turnConstant;
      if (pt == wp.size() - 1) {
        xPow = xDiff * finalMovementConstant;
        yPow = yDiff * finalMovementConstant;
      } else {
        xPow = xDiff * normalMovementConstant;
        yPow = yDiff * normalMovementConstant;
      }

      driveFieldCentricAuto(-yPow, -turnPow, xPow);
    }
  }

  public void turnTo(double targetAngle, long timeout, double powerCap, double minDifference) {
    // GM0
    double currAngle = odometry.getAngle();
    ElapsedTime time = new ElapsedTime();
    while (Math.abs(currAngle - targetAngle) > minDifference
        && time.milliseconds() < timeout
        && ((LinearOpMode) opMode).opModeIsActive()) {
      resetCache();
      updatePosition();
      currAngle = odometry.getAngle();
      double angleDiff = Angle.normalize(currAngle - targetAngle);
      double calcP = Range.clip(angleDiff * 0.01, -powerCap, powerCap);
      driveFieldCentric(0, calcP, 0);
    }

    stopDrive();
  }

  public void updatePosition() {
    odometry.updatePosition(-odoL.encoderReading(), odoR.encoderReading(), -odoN.encoderReading());
  }

  public double getX() {
    return getCurrentPosition().xP;
  }

  public double getY() {
    return getCurrentPosition().yP;
  }

  public double getAngleOdo() {
    return getCurrentPosition().ang;
  }

  public Point getCurrentPosition() {
    updatePosition();
    return new Point(odometry.getX(), odometry.getY(), odometry.getAngle());
  }

  public Point getTicks() {
    updatePosition();
    return new Point(-odoL.encoderReading(), odoR.encoderReading(), -odoN.encoderReading());
  }

  public double getAngleImu() {
    Orientation angles =
        gyro.getAngularOrientation(
            AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); // ZYX is Original
    return Angle.normalize(angles.firstAngle + initAng);
  }

  // Driving
  public void driveFieldCentric(double drive, double turn, double strafe) {
    // https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#field-centric
    double fRightPow, bRightPow, fLeftPow, bLeftPow;
    double botHeading = -Math.toRadians(getAngleImu());
    System.out.println(drive + " " + turn + " " + strafe);

    double rotX = drive * Math.cos(botHeading) - strafe * Math.sin(botHeading);
    double rotY = drive * Math.sin(botHeading) + strafe * Math.cos(botHeading);

    double denominator = Math.max(Math.abs(strafe) + Math.abs(drive) + Math.abs(turn), 1);
    fLeftPow = (rotY + rotX + turn) / denominator;
    bLeftPow = (rotY - rotX + turn) / denominator;
    fRightPow = (rotY - rotX - turn) / denominator;
    bRightPow = (rotY + rotX - turn) / denominator;

    setDrivePowers(bLeftPow, fLeftPow, bRightPow, fRightPow);
  }

  public void driveFieldCentricAuto(double drive, double turn, double strafe) {
    // https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#field-centric
    double fRightPow, bRightPow, fLeftPow, bLeftPow;
    double botHeading = -Math.toRadians(getAngleOdo());
    System.out.println(drive + " " + turn + " " + strafe);

    double rotX = drive * Math.cos(botHeading) - strafe * Math.sin(botHeading);
    double rotY = drive * Math.sin(botHeading) + strafe * Math.cos(botHeading);

    double denominator = Math.max(Math.abs(strafe) + Math.abs(drive) + Math.abs(turn), 1);
    fLeftPow = (rotY + rotX + turn) / denominator;
    bLeftPow = (rotY - rotX + turn) / denominator;
    fRightPow = (rotY - rotX - turn) / denominator;
    bRightPow = (rotY + rotX - turn) / denominator;

    setDrivePowers(bLeftPow, fLeftPow, bRightPow, fRightPow);
  }

  public void driveRobotCentric(double drive, double turn, double strafe) {
    // https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#robot-centric-final-sample-code

    double fRightPow = 0, bRightPow = 0, fLeftPow = 0, bLeftPow = 0;

    fLeftPow = -drive + turn - strafe;
    bLeftPow = -drive + turn + strafe;
    fRightPow = drive + turn - strafe;
    bRightPow = drive + turn + strafe;

    double[] calculatedPower = scalePowers(bLeftPow, fLeftPow, bRightPow, fRightPow);
    fLeftPow = calculatedPower[0];
    bLeftPow = calculatedPower[1];
    fRightPow = calculatedPower[2];
    bRightPow = calculatedPower[3];

    setDrivePowers(bLeftPow, fLeftPow, bRightPow, fRightPow);
  }

  public void setDrivePowers(double bLeftPow, double fLeftPow, double bRightPow, double fRightPow) {
    //    if(gyro.getAngularOrientation().thirdAngle > 5){ // anti-tip
    //      dt.stopDrive();
    //      dt.setDrivePowers(-0.5, 0, -0.5, 0);
    //      return;
    //    }

    bLeftMotor.setPower(bLeftPow);
    fLeftMotor.setPower(fLeftPow);
    bRightMotor.setPower(bRightPow);
    fRightMotor.setPower(fRightPow);
  }

  public void stopDrive() {
    setDrivePowers(0, 0, 0, 0);
  }

  public double[] scalePowers(
      double bLeftPow, double fLeftPow, double bRightPow, double fRightPow) {
    double maxPow =
        Math.max(
            Math.max(Math.abs(fLeftPow), Math.abs(bLeftPow)),
            Math.max(Math.abs(fRightPow), Math.abs(bRightPow)));
    if (maxPow > 1) {
      fLeftPow /= maxPow;
      bLeftPow /= maxPow;
      fRightPow /= maxPow;
      bRightPow /= maxPow;
    }

    return new double[] {fLeftPow, bLeftPow, fRightPow, bRightPow};
  }

  // Misc. Functions / Overloaded Method Storage

  public double getRobotDistanceFromPoint(Point p2) {
    return Math.sqrt((p2.yP - getY()) * (p2.yP - getY()) + (p2.xP - getX()) * (p2.xP - getX()));
  }

  // BULK-READING FUNCTIONS
  public void resetCache() {
    // Clears cache of all hubs
    for (LynxModule hub : allHubs) {
      hub.clearBulkCache();
    }
  }

  @Override
  public void runOpMode() throws InterruptedException {}
}
