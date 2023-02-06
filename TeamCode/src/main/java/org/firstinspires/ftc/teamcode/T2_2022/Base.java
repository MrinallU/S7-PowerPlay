package org.firstinspires.ftc.teamcode.T2_2022;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera.Camera;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Drive;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Angle;
import org.firstinspires.ftc.teamcode.Utils.Motor;
import org.firstinspires.ftc.teamcode.Utils.PathGenerator;
import org.firstinspires.ftc.teamcode.Utils.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class Base extends LinearOpMode {
  // Sleep Times
  public ElapsedTime matchTime = new ElapsedTime();

  // Gyro and Angles
  public IMU gyro;

  public Drive dt = null;
  public Grabber grabber;
  public Camera camera;
  public Servo v4bRight, v4bLeft;

  // Constants and Conversions
  public double targetAngle, currAngle, drive, turn, strafe, multiplier = 1;
  public double initAng;
  public String driveType;
  public String armStat;

  // Positions and Bounds
  public double dpadTurnSpeed = 0.175, dpadDriveSpeed = 0.6;

  // Button Variables
  public boolean yP = false, yLP = false;
  public boolean aP2 = false, aLP2 = false;
  public boolean rP2 = false, rLP2 = false;
  public boolean rP = false, rLP = false;
  public boolean lP2 = false, lLP2 = false;
  public boolean yP2 = false, yLP2 = false;
  public boolean rSP2 = false, rSLP2 = false;
  public boolean bP2 = false, bLP2 = false;
  public boolean dpU2 = false, dpUL2 = false;
  public boolean dpD2 = false, dpDL2 = false;
  public boolean dpL2 = false, dpLL2 = false;
  public boolean dpR2 = false, dpRL2 = false;
  public boolean sPL2 = false, spLL2 = false;
  public boolean lb2 = false, lbl2 = false;
  public boolean rpB2 = false, rpBL2 = false;
  public boolean slowDrive = false, fastDrive = false;
  public boolean basicDrive = false;

  public void initHardware(int angle, OpMode m, Telemetry tele) throws InterruptedException {
    initHardware(0, 0, angle, m, tele);

    initAng = angle;
  }

  public void initHardware(int xPos, int yPos, int angle, OpMode m, Telemetry telemetry)
      throws InterruptedException {
    // Hubs
    List<LynxModule> allHubs;
    allHubs = hardwareMap.getAll(LynxModule.class);

    for (LynxModule hub : allHubs) {
      hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
    }

    // Motors
    Motor fLeftMotor = new Motor(hardwareMap, "front_left_motor", true);
    Motor bLeftMotor = new Motor(hardwareMap, "back_left_motor", true);
    Motor fRightMotor = new Motor(hardwareMap, "front_right_motor", true);
    Motor bRightMotor = new Motor(hardwareMap, "back_right_motor", true);

    Motor odoL = new Motor(hardwareMap, "enc_left");
    //Motor odoR = new Motor(hardwareMap, "enc_right");
    Motor odoN = new Motor(hardwareMap, "enc_x");

    Motor ls = new Motor(hardwareMap, "leftSlide", true),
        rs = new Motor(hardwareMap, "rightSlide", true);

    // Reverse the right side motors
    // Reverse left motors if you are using NeveRests
    fRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    bRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    // Servo
    v4bRight = hardwareMap.get(Servo.class, "v4bRight");
    v4bLeft = hardwareMap.get(Servo.class, "v4bLeft");
    Servo s = hardwareMap.get(Servo.class, "claw");

    // Gyro
    gyro = hardwareMap.get(IMU.class, "imu");

    // Camera
    camera = new Camera(hardwareMap);
    camera.switchToAprilTagDetection();


    // Sensors
    TouchSensor t = hardwareMap.get(TouchSensor.class, "touch_sensor");

    // Modules
    grabber = new Grabber(ls, rs, s, t);
    dt =
        new Drive(
            fLeftMotor,
            bLeftMotor,
            fRightMotor,
            bRightMotor,
            odoL,
            rs,
            odoN,
            grabber,
            gyro,
            m,
            xPos,
            yPos,
            angle,
            allHubs,
            telemetry);

    // reset constants
    targetAngle = currAngle = drive = turn = strafe = multiplier = 1;
    dpadTurnSpeed = 0.175;
    dpadDriveSpeed = 0.6;
    dt.initAng = angle;
    initAng = angle;
    yP = false;
    yLP = false;
    aP2 = false;
    aLP2 = false;
    rP2 = false;
    rLP2 = false;
    lP2 = false;
    lLP2 = false;
    yP2 = false;
    yLP2 = false;
    rSP2 = false;
    rSLP2 = false;
    rP = false;
    rLP = false;
    bP2 = false;
    bLP2 = false;
    dpU2 = false;
    dpUL2 = false;
    dpD2 = false;
    dpDL2 = false;
    dpL2 = false;
    dpLL2 = false;
    dpR2 = false;
    dpRL2 = false;
    slowDrive = false;
    fastDrive = false;
    basicDrive = false;
    sPL2 = false;
    spLL2 = false;
    rpB2 = false;
    rpBL2 = false;
    armStat = "rest";
  }

  public void SplinePathConstantHeading(
      ArrayList<Point> pts,
      double heading,
      double posError,
      double angleError,
      int lookAheadDist,
      double timeout) {
    Point curLoc = dt.getCurrentPosition();
    ArrayList<Point> wps = PathGenerator.interpSplinePath(pts, curLoc);
    dt.ChaseTheCarrot(wps, lookAheadDist, heading, posError, angleError, 0.05, 0.05, 0.01, timeout);
  }

  public void LinearPathConstantHeading(
      ArrayList<Point> pts,
      double heading,
      double posError,
      double angleError,
      int lookAheadDist,
      double timeout) {
    ArrayList<Point> wps = PathGenerator.generateLinearSpline(pts);
    dt.ChaseTheCarrot(wps, lookAheadDist, heading, posError, angleError, 0.05, 0.05, 0.01, timeout);
  }

  public void PlainPathConstantHeading(
      ArrayList<Point> pts,
      double heading,
      double posError,
      double angleError,
      int lookAheadDist,
      double timeout) {
    dt.ChaseTheCarrot(pts, lookAheadDist, heading, posError, angleError, 0.05, 0.05, 0.01, timeout);
  }

  public void PlainPathVaryingHeading(
      ArrayList<Point> pts, double posError, double angleError, int lookAheadDist, double timeout) {
    PlainPathConstantHeading(pts, Double.MAX_VALUE, posError, angleError, lookAheadDist, timeout);
  }

  public void LinearPathVaryingHeading(
      ArrayList<Point> pts, double posError, double angleError, int lookAheadDist, double timeout) {
    LinearPathConstantHeading(pts, Double.MAX_VALUE, posError, angleError, lookAheadDist, timeout);
  }

  public void SplinePathVaryingHeading(
      ArrayList<Point> pts, double posError, double angleError, int lookAheadDist, double timeout) {
    SplinePathConstantHeading(pts, Double.MAX_VALUE, posError, angleError, lookAheadDist, timeout);
  }

  public void turnTo(double targetAngle, long timeout, double powerCap, double minDifference) {
    dt.turnTo(targetAngle, timeout, powerCap, minDifference);
  }

  public void turnTo(double targetAngle, long timeout, double powerCap) {
    dt.turnTo(targetAngle, timeout, powerCap, 2);
  }

  public void turnTo(double targetAngle, long timeout) {
    turnTo(targetAngle, timeout, 0.7);
  }

  // Driver Controlled Movemement
  public void computeDrivePowers(Gamepad gamepad) {
    currAngle = 0;

    if (basicDrive) {
      driveType = "Robot Centric";

      if (gamepad.dpad_right) {
        dt.driveRobotCentric(0, dpadTurnSpeed, 0);
      } else if (gamepad.dpad_left) {
        dt.driveRobotCentric(0, -dpadTurnSpeed, 0);
      } else if (gamepad.dpad_up) {
        dt.driveFieldCentric(0, 0.01 * Angle.angleDifference(currAngle, targetAngle + 90), 0);
      } else {
        dt.driveRobotCentric(drive, turn, strafe);
      }

    } else {
      driveType = "Field Centric";
      if (gamepad.dpad_right) {
        dt.driveFieldCentric(-dpadDriveSpeed, 0, 0);
      } else if (gamepad.dpad_left) {
        dt.driveFieldCentric(dpadDriveSpeed, 0, 0);
      } else if (gamepad.dpad_up) {
        dt.driveFieldCentric(0, 0, -dpadDriveSpeed);
      } else if (gamepad.dpad_down) {
        dt.driveFieldCentric(0, 0, dpadDriveSpeed);
      } else {
        dt.driveFieldCentric(drive, turn, strafe);
      }
    }
  }

  // Misc Utility Functions
  public void update() {
    dt.resetCache();
    dt.updatePosition();
  }

  public String formatDegrees(double degrees) {
    return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
  }

  public double floor(double rawInput) {
    if (slowDrive) {
      return ((int) (rawInput * 5.5)) / 11.0;
    } else if (fastDrive) {
      return rawInput;
    }
    return ((int) (rawInput * 5.5)) / 11.0; // at slow
  }

  public double turnFloor(double rawInput) {
    return ((int) (rawInput * 15)) / 20.0;
  }

  public String formatAngle(AngleUnit angleUnit, double angle) {
    return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
  }

  @Override
  public abstract void runOpMode() throws InterruptedException;
}
