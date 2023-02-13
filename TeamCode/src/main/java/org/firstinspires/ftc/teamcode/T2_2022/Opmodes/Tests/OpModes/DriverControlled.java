package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.T2_2022.Base;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "T5_TeleOp", group = "OdomBot")
public class DriverControlled extends Base {
  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(-90, this, telemetry);
    ElapsedTime timer = new ElapsedTime();
    Servo v4bRight, v4bLeft, claw;
    double v4bRightOut = 0.8, v4bLeftOut = 0.2;
    Motor ls = new Motor(hardwareMap, "leftSlide"), rs = new Motor(hardwareMap, "rightSlide");
    v4bRight = hardwareMap.get(Servo.class, "v4bRight");
    v4bLeft = hardwareMap.get(Servo.class, "v4bLeft");
    claw = hardwareMap.get(Servo.class, "claw");
    TouchSensor t = hardwareMap.get(TouchSensor.class, "touch_sensor");
    Grabber grabber = new Grabber(ls, rs, claw, t);
    claw.setPosition(grabber.CLAW_CLOSE); // 0 open
    v4bRight.setPosition(0);
    v4bLeft.setPosition(1);

    double multiplier = 0.9;

    int[] STACK_HEIGHTS = {0, 250, 330, 480, 575};
    int stack_counter = 4;

    v4bRight.setPosition(0);
    v4bLeft.setPosition(1);

    boolean buttonCurr = false, buttonLast = false;

    boolean barDown = false;

    boolean buttonCurrB = false, buttonLastB = false;

    boolean middleCurr = false, middleLast = false;

    boolean clawLast = false, clawCurr = false;

    boolean midCurr = false, midLast = false;

    boolean resetXLast = false, resetXCurr = false;

    boolean clawOpen = false;

    boolean up = false;

    // odometry.updatePosition();

    // dt.updatePosition();
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
    matchTime.reset();

    while (opModeIsActive()) {
      // Updates

      dt.resetCache();
      currAngle = dt.getAngleImu();

      dt.updatePosition();

      // Change Drive Mode
      yLP = yP;
      yP = gamepad1.y;
      if (!yLP && yP) {
        basicDrive = !basicDrive;
      }

      resetXLast = resetXCurr;
      resetXCurr = gamepad1.x;
      if (!resetXLast && resetXCurr) {
        dt.initAng = Math.toRadians(dt.getAngleImu());
      }

      if (gamepad1.left_bumper) {
        multiplier = 1;
      } else {
        multiplier = 0.85;
      }

      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = -gamepad1.right_stick_x * multiplier;
      strafe = gamepad1.right_stick_y * multiplier;
      turn = gamepad1.left_stick_x * multiplier;
      computeDrivePowers(gamepad1);

      middleLast = middleCurr;
      middleCurr = gamepad2.dpad_down;
      if (middleCurr && !middleLast) {
        v4bRight.setPosition(0);
        v4bLeft.setPosition(1);
        if (v4bRight.getPosition() < 0.1) {
          claw.setPosition(grabber.CLAW_OPEN_REST);
          clawOpen = true;
        }
      }

      //
      //
      //
      //
      //
      //
      buttonLast = buttonCurr;
      buttonCurr = gamepad2.y;
      if (buttonCurr && !buttonLast) {
        up = true;
        grabber.raiseToPosition(2550, 1);
        v4bRight.setPosition(v4bRightOut);
        v4bLeft.setPosition(v4bLeftOut);
      }

      midLast = midCurr;
      midCurr = gamepad2.x;
      if (midCurr && !midLast) {
        up = true;
        grabber.raiseToPosition(1600, 1);
        v4bRight.setPosition(v4bRightOut);
        v4bLeft.setPosition(v4bLeftOut);
      }

      bLP2 = bP2;
      bP2 = gamepad2.b;
      if (bP2 && !bLP2) {
        up = true;
        grabber.raiseToPosition(600, 1);
        v4bRight.setPosition(v4bRightOut);
        v4bLeft.setPosition(v4bLeftOut);
      }

      buttonLastB = buttonCurrB;
      buttonCurrB = gamepad2.dpad_up;
      if (buttonCurrB && !buttonLastB) {
        // grabber.raiseToPosition(0, 1);
        v4bRight.setPosition(0.3);
        v4bLeft.setPosition(0.7);
      }

      dpDL2 = dpD2;
      dpD2 = gamepad2.a;
      if (dpD2 && !dpDL2) {
        up = false;
        grabber.raiseToPosition(0, 1);
        v4bRight.setPosition(0.2);
        v4bLeft.setPosition(0.8);
      }

      clawLast = clawCurr;
      clawCurr = gamepad1.right_bumper;
      if (clawCurr && !clawLast) {
        clawOpen = !clawOpen;
        if (!clawOpen) {
          if (up) {
            grabber.releaseCone();
          } else {
            grabber.resetClaw();
          }
        } else {
          grabber.grabCone();
        }
      }

      if (gamepad2.dpad_right) {
        v4bRight.setPosition(v4bRight.getPosition() + 0.02);
        v4bLeft.setPosition(v4bLeft.getPosition() - 0.02);
      }

      if (gamepad2.dpad_left) {
        v4bRight.setPosition(v4bRight.getPosition() - 0.02);
        v4bLeft.setPosition(v4bLeft.getPosition() + 0.02);
      }

      // Drive
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      if (up) {
        multiplier = 0.75;
      } else {
        multiplier = 0.85;
      }
      drive = -gamepad1.right_stick_x * multiplier;
      strafe = -gamepad1.right_stick_y * multiplier;
      turn = gamepad1.left_stick_x * multiplier;
      computeDrivePowers(gamepad1);

      // Display Values
      telemetry.addData("Drive Type", driveType);
      telemetry.addData("encLeft, encRight, encNormal ", dt.getTicks());
      telemetry.addData("Odo Tick Inch Count: ", dt.getCurrentPosition());
      // telemetry.addData("Four Bar Pos: ", grabber.v4b.retMotorEx().getCurrentPosition());
      telemetry.addData("Stack Pickup Height: ", stack_counter + 1);
      telemetry.addData("V4bPositions ", v4bLeftOut + v4bRightOut);
      telemetry.addData("heading", dt.getAngleImu());
      telemetry.addData("yaw", dt.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
      telemetry.addData("pitch", dt.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES));
      telemetry.addData("roll", dt.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES));

      telemetry.update();
    }

    /*dpUL2 = dpU2;
    dpU2 = gamepad2.y;
    if (!dpUL2 && dpU2) {
      armStat = "high";
    }

    dpDL2 = dpD2;
    dpD2 = gamepad2.a;
    if (!dpDL2 && dpD2) {
      armStat = "rest";
    }

    bLP2 = bP2;
    bP2 = gamepad2.b;
    if (bP2 && !bLP2) {
      armStat = "low";
    }

    dpLL2 = dpL2;
    dpL2 = gamepad2.x;
    if (!dpLL2 && dpL2) {
      armStat = "middle";
    }

    // replace manual control in favour for automated control
    if (gamepad2.dpad_up){
      grabber.v4b.setPower(0.6);
    } else if (gamepad2.dpad_down) {
      grabber.v4b.setPower(-0.6);
    } else {
      grabber.v4b.setPower(0);
    }

    rLP = rP;
    rP = gamepad1.right_bumper;
    if (!rLP && rP && !grabber.v4bMoving) {
      if (Objects.equals(grabber.clawStatus, "closed")) {
        if (grabber.rightSlide.retMotorEx().getCurrentPosition() > 200) {
          grabber.releaseCone();
          telemetry.addData("works", true);
        } else {
          grabber.resetClaw();
          telemetry.addData("works", true);
        }
      } else {
        grabber.grabCone();
      }
    }*/

    /*lbl2 = lb2;
    lb2 = gamepad2.left_bumper;
    if (lb2 && !lbl2) {
      armStat = "stack";
      grabber.raiseToPosition(STACK_HEIGHTS[stack_counter], 1);
      if(stack_counter >= 1){
        stack_counter -= 1;
      }

    }*/

    /*rpBL2 = rpB2;
    rpB2 = gamepad2.right_bumper;
    if(rpB2 && !rpBL2){
      armStat = "stackUp";
      grabber.stackUp();
    }*/

    // Grabber
    // grabber.updateArmPos(armStat, gamepad2);

  }
}
