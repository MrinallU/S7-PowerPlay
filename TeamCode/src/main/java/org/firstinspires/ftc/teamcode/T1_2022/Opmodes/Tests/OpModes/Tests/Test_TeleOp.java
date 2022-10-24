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

      dpUL = dpU;
      dpU = gamepad1.dpad_up;
      if (!dpUL && dpU) {
        armStat = "high";
      }

      dpDL = dpD;
      dpD = gamepad1.dpad_down;
      if (!dpDL && dpD) {
        armStat = "rest";
      }

      dpLL  = dpL;
      dpL = gamepad1.dpad_left;
      if (!dpUL && dpU) {
        armStat = "middle";
      }

      aLP2 = aP2;
      aP2 = gamepad2.a;
      if(!aLP2 && aP2){
        grabber.grabCone();
      }

       bLP2 = bP2;
       bP2 = gamepad2.b;
       if(!bLP2 && bP2){
         grabber.releaseCone();
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
      telemetry.update();
    }
  }
}
