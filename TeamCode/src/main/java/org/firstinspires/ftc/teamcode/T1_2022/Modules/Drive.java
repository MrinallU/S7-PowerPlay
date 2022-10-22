package org.firstinspires.ftc.teamcode.T1_2022.Modules;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VoltageUnit;
import org.firstinspires.ftc.teamcode.T1_2022.Base;
import org.firstinspires.ftc.teamcode.Utils.Angle;
import org.firstinspires.ftc.teamcode.Utils.Motor;
import org.firstinspires.ftc.teamcode.Utils.Point;

public class Drive extends Base {

  protected Motor fLeftMotor, bLeftMotor, fRightMotor, bRightMotor;
  protected BNO055IMU gyro;
  protected Odometry odometry;
  protected OpMode opMode;
  protected List<LynxModule> allHubs;

  public Drive(
      Motor fLeftMotor,
      Motor bLeftMotor,
      Motor fRightMotor,
      Motor bRightMotor,
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
    this.gyro = gyro;
    this.opMode = m;
    this.allHubs = allHubs;
    odometry = new Odometry(xPos, yPos, angle);
  }

  public double getAngle() {
    Orientation angles =
        gyro.getAngularOrientation(
            AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); // ZYX is Original
    return Angle.normalize(angles.firstAngle + initAng);
  }

  // Driving
  public void driveFieldCentric(double drive, double turn, double strafe) {
    // https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#field-centric
    double fRightPow, bRightPow, fLeftPow, bLeftPow;
    double botHeading = -Math.toRadians(gyro.getAngularOrientation().firstAngle);

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
    return Math.sqrt(
        (p2.yP - odometry.getY()) * (p2.yP - odometry.getY())
            + (p2.xP - odometry.getX()) * (p2.xP - odometry.getX()));
  }

  // BULK-READING FUNCTIONS
  public void resetCache() {
    // Clears cache of all hubs
    for (LynxModule hub : allHubs) {
      hub.clearBulkCache();
    }
  }

  private double getVoltage() {
    double voltage = Double.MIN_VALUE;
    for (LynxModule hub : allHubs) {
      voltage = Math.max(voltage, hub.getInputVoltage(VoltageUnit.VOLTS));
    }

    return voltage;
  }

  @Override
  public void runOpMode() throws InterruptedException {}
}
