package org.firstinspires.ftc.teamcode.T1_2022;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.T1_2022.Modules.Drive;
import org.firstinspires.ftc.teamcode.T1_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Angle;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public abstract class Base extends LinearOpMode {
  // Sleep Times
  public ElapsedTime matchTime = new ElapsedTime();

  // Gyro and Angles
  public BNO055IMU gyro;

  public Drive dt = null;
  public Grabber grabber;

  // Constants and Conversions
  public double targetAngle, currAngle, drive, turn, strafe, multiplier = 1;
  public double initAng = 0;
  public String driveType;
  public String armStat = "rest";

  // Positions and Bounds
  public double dpadTurnSpeed = 0.175, dpadDriveSpeed = 0.2;

  // Button Variables
  public boolean yP = false, yLP = false;
  public boolean aP2 = false, aLP2 = false;
  public boolean rP2 = false, rLP2 = false;
  public boolean lP2 = false, lLP2 = false;
  public boolean yP2 = false, yLP2 = false;
  public boolean rSP2 = false, rSLP2 = false;
  public boolean bP2 = false, bLP2 = false;
  public boolean dpU = false, dpUL = false;
  public boolean dpD = false, dpDL = false;
  public boolean dpL = false, dpLL = false;
  public boolean dpR = false, dpRL = false;
  public boolean slowDrive = false, fastDrive = false;
  public boolean basicDrive = false;

  public void initHardware(int angle, OpMode m) throws InterruptedException {
    initHardware(0, 0, angle, m);
    initAng = angle;
  }

  public void initHardware(int xPos, int yPos, int angle, OpMode m) throws InterruptedException {
    // Hubs
    List<LynxModule> allHubs;
    allHubs = hardwareMap.getAll(LynxModule.class);

    for (LynxModule hub : allHubs) {
      hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
    }

    // Motors
    Motor fLeftMotor = new Motor(hardwareMap, "front_left_motor");
    Motor bLeftMotor = new Motor(hardwareMap, "back_left_motor");
    Motor fRightMotor = new Motor(hardwareMap, "front_right_motor");
    Motor bRightMotor = new Motor(hardwareMap, "back_right_motor");

    Motor ls = new Motor(hardwareMap, "leftSlide"), rs = new Motor(hardwareMap, "rightSlide");

    // Reverse the right side motors
    // Reverse left motors if you are using NeveRests
    fLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    bLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    // Servo
    Servo s = hardwareMap.get(Servo.class, "claw"),
        lv = hardwareMap.get(Servo.class, "v4bl"),
        rv = hardwareMap.get(Servo.class, "v4br");

    // Gyro
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
    parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
    parameters.calibrationDataFile =
        "BNO055IMUCalibration.json"; // see the calibration sample opmode
    parameters.loggingEnabled = true;
    parameters.loggingTag = "IMU";
    parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

    gyro = hardwareMap.get(BNO055IMU.class, "imu");
    gyro.initialize(parameters);

    // Sensors
    TouchSensor t = hardwareMap.get(TouchSensor.class, "touch_sensor");

    // Modules
    dt =
        new Drive(
            fLeftMotor, bLeftMotor, fRightMotor, bRightMotor, gyro, m, xPos, yPos, angle, allHubs);

    grabber = new Grabber(ls, rs, lv, rv, s, t);

    // reset constants
    targetAngle = currAngle = drive = turn = strafe = multiplier = 1;
    dpadTurnSpeed = 0.175;
    dpadDriveSpeed = 0.2;
    initAng = angle;
    yP = false; yLP = false;
    aP2 = false; aLP2 = false;
    rP2 = false; rLP2 = false;
    lP2 = false; lLP2 = false;
    yP2 = false; yLP2 = false;
    rSP2 = false; rSLP2 = false;
    bP2 = false; bLP2 = false;
    dpU = false; dpUL = false;
    dpD = false; dpDL = false;
    dpL = false; dpLL = false;
    dpR = false; dpRL = false;
    slowDrive = false; fastDrive = false;
    basicDrive = false;
    armStat = "rest";
  }

  public void initHardware(OpMode m) throws InterruptedException {
    initHardware(0, m);
  }

  // Autonomous Movement (Note that you do not have to insert the current position into any of the
  // weighpoints)

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
        dt.driveFieldCentric(0, dpadTurnSpeed, 0);
      } else if (gamepad.dpad_left) {
        dt.driveFieldCentric(0, -dpadTurnSpeed, 0);
      } else if (gamepad.dpad_up) {
        dt.driveFieldCentric(-dpadDriveSpeed, 0, 0);
      } else if (gamepad.dpad_down) {
        dt.driveFieldCentric(dpadDriveSpeed, 0, 0);
      } else {
        dt.driveFieldCentric(drive, turn, strafe);
      }
    }
  }

  // Misc Utility Functions
  public String formatDegrees(double degrees) {
    return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
  }

  public double floor(double rawInput) {
    if (slowDrive) {
      return ((int) (rawInput * 5.5)) / 11.0;
    } else if (fastDrive) {
      return rawInput;
    }
    return ((int) (rawInput * 9)) / 11.0;
  }

  public double turnFloor(double rawInput) {
    return ((int) (rawInput * 15)) / 20.0;
  }

  public String formatAngle(AngleUnit angleUnit, double angle) {
    return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
  }

  // Other Functions
  public double normalizeThreeDigits(double d) {
    return (int) (d * 1000) / 1000.;
  }

  @Override
  public abstract void runOpMode() throws InterruptedException;
}
