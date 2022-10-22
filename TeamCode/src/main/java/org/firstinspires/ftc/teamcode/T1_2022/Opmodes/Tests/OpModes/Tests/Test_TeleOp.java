package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
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

      //      dpUL = dpU;
      //      dpU = gamepad1.dpad_up;
      //      if (!dpUL && dpU) {
      //        grabber.updateArmPos("high");
      //      }
      //
      //      dpDL = dpD;
      //      dpD = gamepad1.dpad_down;
      //      if (!dpDL && dpD) {
      //        grabber.updateArmPos("rest");
      //      }
      //
      //      dpLL  = dpL;
      //      dpL = gamepad1.dpad_left;
      //      if (!dpUL && dpU) {
      //        grabber.updateArmPos("middle");
      //      }
      //
      //      dpRL = dpR;
      //      dpR = gamepad1.dpad_right;
      //      if (!dpRL && dpR) {
      //        grabber.updateArmPos("middle");
      //      }

      // Drive
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = floor(gamepad1.right_stick_x);
      strafe = floor(-gamepad1.right_stick_y);
      turn = turnFloor(gamepad1.left_stick_x);
      computeDrivePowers(gamepad1);

      // Display Values
      telemetry.addData("Drive Type", driveType);
      telemetry.update();
    }
  }
}
