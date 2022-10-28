package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.T1_2022.Base;

@TeleOp(name = "Test_TeleOp", group = "OdomBot")
public class Test_TeleOp extends Base {

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
      dpU2 = gamepad2.dpad_up;
      if (!dpUL2 && dpU2) {
        armStat = "high";
      }

      dpDL2 = dpD2;
      dpD2 = gamepad2.dpad_down;
      if (!dpDL2 && dpD2) {
        armStat = "rest";
      }

      dpLL2 = dpL2;
      dpL2 = gamepad2.dpad_left;
      if (!dpLL2 && dpL2) {
        armStat = "middle";
      }

      rLP = rP;
      rP = gamepad1.right_bumper;
      if (!rLP2 && rP2) {
        if (Objects.equals(grabber.clawStatus, "closed")) {
          if (Objects.equals(grabber.armStatusPrev, "rest")) {
            grabber.resetClaw();
          } else {
            grabber.releaseCone();
          }
        } else {
          grabber.grabCone();
        }
      }

      // Grabber
      grabber.updateArmPos(armStat);

      // Drive
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = floor(gamepad1.right_stick_x);
      strafe = floor(-gamepad1.right_stick_y);
      turn = turnFloor(gamepad1.left_stick_x);
      computeDrivePowers(gamepad1);

      // Display Values
      telemetry.addData("Drive Type", driveType);
      telemetry.addData("touch sensor", grabber.touchSensor.isPressed());
      telemetry.update();
    }
  }
}
