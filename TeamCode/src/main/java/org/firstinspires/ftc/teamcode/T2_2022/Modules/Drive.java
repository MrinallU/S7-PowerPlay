package org.firstinspires.ftc.teamcode.T2_2022.Modules;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.util.ArrayList;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.T2_2022.Base;
import org.firstinspires.ftc.teamcode.Utils.Angle;
import org.firstinspires.ftc.teamcode.Utils.Motor;
import org.firstinspires.ftc.teamcode.Utils.PathGenerator;
import org.firstinspires.ftc.teamcode.Utils.Point;

public class Drive extends Base {

  protected Motor fLeftMotor, bLeftMotor, fRightMotor, bRightMotor;
  protected Motor odoR, odoL, odoN;
  protected BNO055IMU gyro;
  protected Odometry odometry;
  protected OpMode opMode;
  protected List<LynxModule> allHubs;

  ElapsedTime time = new ElapsedTime();
  double prevTime = 0;
  public double yEncPos = 0, xEncPosv = 0;


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
          List<LynxModule> allHubs) {

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
    odometry = new Odometry(xPos, yPos, angle);
  }

  public void NormalMTP(double tx, double ty, double tang, double timeout){
    ElapsedTime time = new ElapsedTime();
    time.reset();
    updatePosition();
    while ((Math.abs(getX() - tx) > 1
            || Math.abs(getY() - ty) > 1
            || Math.abs(tang - getAngle()) > 1)
            && time.milliseconds() < timeout) {
      resetCache();
      updatePosition();
      double x = getX();
      double y = getY();
      double theta = getAngle();

      double xDiff = tx - x;
      double yDiff = ty - y;
      double angDiff = theta - tang;
//      double xPow = xDiff*0.04;
//      double yPow = yDiff*0.04;
      double xPow;
      double yPow;
      if (Math.abs(getRobotDistanceFromPoint(new Point(tx, ty))) > 4) {
        xPow = xDiff * 0.02;
        yPow = yDiff * 0.02;
      }else{
        xPow = xDiff * 0.017;
        yPow = yDiff * 0.017;
      }

      if(Math.abs(xDiff)<1){xPow = 0;}
      if(Math.abs(yDiff)<1){yPow = 0;}
      if(Math.abs(angDiff)<1){angDiff=0; }

      driveFieldCentric(-yPow, 0.003 * angDiff,  xPow);
    }
    stopDrive();
  }

  // Kinda Like:
  // https://www.ri.cmu.edu/pub_files/pub3/coulter_r_craig_1992_1/coulter_r_craig_1992_1.pdf
  // Helpful explanation:
  // https://www.chiefdelphi.com/t/paper-implementation-of-the-adaptive-pure-pursuit-controller/166552
  public void traversePath(
          ArrayList<Point> wp,
          boolean plain,
          double heading,
          double driveSpeedCap,
          boolean limitPower,
          double powerLowerBound,
          double xError,
          double yError,
          double angleError,
          int lookAheadDist,
          double timeout) {
    ElapsedTime time = new ElapsedTime();
    int lastLhInd = 0;
    time.reset();
    while ((lastLhInd < wp.size() - 1
            || (Math.abs(getX() - wp.get(wp.size() - 1).xP) > xError
            || Math.abs(getY() - wp.get(wp.size() - 1).yP) > yError
            || (heading == Double.MAX_VALUE? Math.abs(wp.get(wp.size()-1).ang - getAngle()) > angleError : Math.abs(heading - getAngle()) > angleError)))
            && time.milliseconds() < timeout) {
      resetCache();
      updatePosition();
      double x = getX();
      double y = getY();
      double theta = getAngle();

      // find point which fits the look ahead criteria
      Point nxtP = null;
      int i = 0, cnt = 0, possInd = -1;
      double maxDist = -1;
      for (Point p : wp) {
        resetCache();
        updatePosition();
        double ptDist = getRobotDistanceFromPoint(p);
        if (Math.abs(ptDist) <= lookAheadDist
                && i > lastLhInd
                && Math.abs(ptDist) > maxDist) {
          nxtP = p;
          possInd = i;
          maxDist = Math.abs(ptDist);
        }
        i++;
      }

      if (possInd == -1) {
        possInd = lastLhInd;
        nxtP = wp.get(lastLhInd);
      }
      if (nxtP == null) {
        stop();
        break;
      }


      resetCache();
      updatePosition();
      // assign powers to follow the look-ahead point
      double xDiff = nxtP.xP - x;
      double yDiff = nxtP.yP - y;
      double angDiff, splineAngle;

      splineAngle = Math.atan2(yDiff, xDiff);
      if (heading == Double.MAX_VALUE) {
        angDiff = theta - Angle.normalize(Math.toDegrees(splineAngle));
      } else {
        angDiff = theta - heading;
      }

      if (Math.abs(angDiff) < angleError) angDiff = 0;

      double dist = getRobotDistanceFromPoint(nxtP); // mtp 2.0
      double relAngToP =
              Angle.normalizeRadians(
                      splineAngle - (Math.toRadians(theta) - Math.toRadians(90))); // mtp 2.0
      double relX = Math.sin(relAngToP) * dist, relY = Math.cos(relAngToP) * dist;
      double xPow = (relX / (Math.abs(relY) + Math.abs(relX))) * driveSpeedCap,
              yPow = (relY / (Math.abs(relX) + Math.abs(relY))) * driveSpeedCap;

      updatePosition();

      if(!plain) {

        if (Math.abs(getRobotDistanceFromPoint(wp.get(wp.size()-1))) < 5) {
          xPow = xDiff*0.017;
          yPow = -yDiff*0.017;
        }else{
          xPow = xDiff*0.03;
          yPow = -yDiff*0.03;
        }
//        if (Math.abs(getRobotDistanceFromPoint(nxtP)) > 4) {
//          xPow = xDiff * 0.02;
//          yPow = yDiff * 0.02;
//        }else{
//          xPow = xDiff * 0.017;
//          yPow = yDiff * 0.017;
//        }
      }else{
        if (possInd!=wp.size()-1) {
          xPow = xDiff * 0.04;
          yPow = yDiff * 0.04;
        } else {
          xPow = xDiff * 0.02;
          yPow = yDiff * 0.02;
        }
      }

//      if (limitPower) {
//        if (Math.abs(yDiff) > 20) {
//          if (yPow < 0) {
//            yPow = Math.min(-powerLowerBound, yPow);
//          } else {
//            yPow = Math.max(powerLowerBound, yPow);
//          }
//        }
//        if (Math.abs(xDiff) > 20) {
//          if (xPow < 0) {
//            xPow = Math.min(-powerLowerBound, xPow);
//          } else {
//            xPow = Math.max(powerLowerBound, xPow);
//          }
//        }
//      }

      resetCache();
      updatePosition();
//      xPow = Range.clip(xPow, -0.4, 0.4);
//      yPow = Range.clip(yPow, -0.4, 0.4);

      if(Math.abs(xDiff)<xError){xPow = 0;}
      if(Math.abs(yDiff)<yError){
        yPow = 0;
      }

//      resetCache();
//      updateencodo();
      //System.out.println(xPow + " " + yPow);
      driveFieldCentric(-yPow, 0.01 * angDiff,  xPow);
      lastLhInd = possInd;
    }
    stopDrive();
  }

  public void traversePath(
          ArrayList<Point> wp,
          double heading,
          double driveSpeedCap,
          double powLb,
          double xError,
          double yError,
          double angleError,
          int lookAheadDist,
          double timeout) {
    traversePath(
            wp,
            false,
            heading,
            driveSpeedCap,
            true,
            powLb,
            xError,
            yError,
            angleError,
            lookAheadDist,
            timeout);
  }

  public void traversePath(
          ArrayList<Point> wp,
          double heading,
          double xError,
          double yError,
          double angleError,
          int lookAheadDist,
          double timeout) {
    traversePath(wp, false, heading, 1, false, -1, xError, yError, angleError, lookAheadDist, timeout);
  }

  public void moveToPosition(
          double targetXPos,
          double targetYPos,
          double targetAngle,
          double xAccuracy,
          double yAccuracy,
          double angleAccuracy,
          double timeout,
          double powerlB,
          int lh) {
    ArrayList<Point> pt = new ArrayList<>();
    pt.add(getCurrentPosition());
    pt.add(new Point(targetXPos, targetYPos));
    ArrayList<Point> wps = PathGenerator.generateLinearSpline(pt);
    traversePath(wps, targetAngle, 1, powerlB, xAccuracy, yAccuracy, angleAccuracy, lh, timeout);
    stopDrive();
  }

  public void turnTo(double targetAngle, long timeout, double powerCap, double minDifference) {
    // GM0
    double currAngle = getAngle();
    ElapsedTime time = new ElapsedTime();
    while (Math.abs(currAngle - targetAngle) > minDifference
            && time.milliseconds() < timeout
            && ((LinearOpMode) opMode).opModeIsActive()) {
      resetCache();
      updatePosition();
      currAngle = getAngle();
      double angleDiff = Angle.normalize(currAngle - targetAngle);
      double calcP = Range.clip(angleDiff * 0.01, -powerCap, powerCap);
      driveFieldCentric(0,calcP,0);
    }

    stopDrive();
  }

  public void updatePosition() {
    odometry.updatePosition(
            -odoL.encoderReading(), odoR.encoderReading(), -odoN.encoderReading(), getAngle());
  }

  public double getX() {
    return getCurrentPosition().xP;
  }

  public double getY() {
    return getCurrentPosition().yP;
  }

  public Point getCurrentPosition() {
    updatePosition();
    return new Point(odometry.getX(), odometry.getY(), getAngle());
  }

  public Point getTicks() {
    updatePosition();
    return new Point(-odoL.encoderReading(), odoR.encoderReading(), -odoN.encoderReading());
  }

  public double getAngle() {
    Orientation angles =
            gyro.getAngularOrientation(
                    AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); // ZYX is Original
    return Angle.normalize(angles.firstAngle + 0);
  }

  // Driving
  public void driveFieldCentric(double drive, double turn, double strafe) {
    // https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#field-centric
    double fRightPow, bRightPow, fLeftPow, bLeftPow;
    double botHeading = -Math.toRadians(getAngle());
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