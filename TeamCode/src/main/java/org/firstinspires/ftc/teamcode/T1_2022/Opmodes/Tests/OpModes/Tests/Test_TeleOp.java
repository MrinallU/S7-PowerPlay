package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.T1_2022.Base;

@TeleOp(name = "Test_TeleOp", group = "OdomBot")
public class Test_TeleOp extends Base {
  boolean fourBarLast = false, fourBarCurr = false, fourBarUp = false;

  // TeleOp Variables
  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this);
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
    matchTime.reset();

    while (opModeIsActive()) {
      // Updates
      dt.resetCache();
      currAngle = dt.getAngle();

      // Change Drive Mode
      yLP = yP;
      yP = gamepad1.y;
      if (!yLP && yP) {
        basicDrive = !basicDrive;
      }

      dpUL2 = dpU2;
      dpU2 = gamepad2.y;
      if (!dpUL2 && dpU2) {
        armStat = "high";
      }

      dpDL2 = dpD2;
      dpD2 = gamepad2.a;
      if (!dpDL2 && dpD2) {
        armStat = "rest";
      }

      dpLL2 = dpL2;
      dpL2 = gamepad2.x;
      if (!dpLL2 && dpL2) {
        armStat = "middle";
      }

      if (gamepad2.dpad_up) {
        grabber.v4b.setPower(0.15);
      } else if (gamepad2.dpad_down) {
        grabber.v4b.setPower(-0.4);

      } else {
        grabber.v4b.setPower(0);
      }

      //      fourBarLast = fourBarCurr;
      //      fourBarCurr = gamepad2.left_bumper;
      //      if (fourBarCurr && !fourBarLast) {
      //        fourBarUp = !fourBarUp;
      //        if (fourBarUp) {
      //          grabber.setV4B_FRONT();
      //        } else {
      //          grabber.setV4B_BACK();
      //        }
      //      }

      rLP = rP;
      rP = gamepad1.right_bumper;
      if (!rLP && rP) {
        if (Objects.equals(grabber.clawStatus, "closed")) {
          // telemetry.addData("works", true);
          if (Objects.equals(grabber.armStatusPrev, "rest")) {
            grabber.resetClaw();
            telemetry.addData("works", true);
          } else {
            grabber.releaseCone();
            telemetry.addData("works", true);
          }
        } else {
          grabber.grabCone();
        }
      }

      //      rLP = rP;
      //      rP = gamepad1.right_bumper;
      //      if (!rLP && rP) {
      //        grabber.resetClaw();
      //      }

      // Grabber
      grabber.updateArmPos(armStat);

      // Drive
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = floor(gamepad1.right_stick_x * 0.8);
      strafe = floor(-gamepad1.right_stick_y * 0.8);
      turn = turnFloor(gamepad1.left_stick_x * 0.8);
      computeDrivePowers(gamepad1);

      // Display Values
      telemetry.addData("Drive Type", driveType);
      telemetry.addData("touch sensor", grabber.touchSensor.isPressed());
      telemetry.addData("claw", grabber.clawStatus);
      telemetry.addData("claw", gamepad1.right_bumper);
      telemetry.addData("rs Pos: ", grabber.rightSlide.retMotorEx().getCurrentPosition());
      telemetry.addData("rs: ", armStat);
      telemetry.addData("Four Bar Pos: ", grabber.v4b.retMotorEx().getCurrentPosition());
      telemetry.update();
    }
  }
}
