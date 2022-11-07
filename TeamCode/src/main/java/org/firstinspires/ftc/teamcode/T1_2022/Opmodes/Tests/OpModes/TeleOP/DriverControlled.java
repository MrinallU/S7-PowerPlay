package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.T1_2022.Base;

@TeleOp(name = "TeleOp", group = "OdomBot")
public class DriverControlled extends Base {
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
      dt.updatePoseEstimate();

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

      if (gamepad2.dpad_up) {
        grabber.v4b.setPower(1);
      } else if (gamepad2.dpad_down) {
        grabber.v4b.setPower(-1);
      } else {
        grabber.v4b.setPower(0);
      }

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

      lbl2 = lb2;
      lb2 = gamepad2.left_bumper;
      if (lb2 && !lbl2) {
        armStat = "manual";
        grabber.manualPos -= 100;
      }

      spLL2 = sPL2;
      sPL2 = gamepad2.right_bumper;
      if (sPL2 && !spLL2) {
        armStat = "manual";
        grabber.manualPos += 100;
      }

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
      telemetry.addData("slideMode: ", armStat);
      if (Objects.equals(armStat, "manual")) {
        telemetry.addData("manualPos: ", grabber.manualPos);
      }
      telemetry.addData("Four Bar Pos: ", grabber.v4b.retMotorEx().getCurrentPosition());
      telemetry.addData(
          "Current Pos: ", dt.getPose().getX() + " " + dt.getPose().getY() + " " + dt.getAngle());
      telemetry.update();
    }
  }
}
