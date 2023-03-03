package org.firstinspires.ftc.teamcode.NewRobot.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.NewRobot.Base;

@TeleOp(name = "New_Robot_TeleOP", group = "OdomBot")
public class Test_TeleOp extends Base {
  int stage = 0;
  boolean first = true;
  boolean doneScoring = true;
  boolean cycleMode = true;
  boolean cont = true;
  boolean xLP2, xP2, aLP, aP;
  String mode = "high";

  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this, telemetry);
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
    matchTime.reset();
    dt.update();
    while (opModeIsActive()) {
      // Updates
      dt.resetCache();
      dt.update();
      currAngle = dt.getAngleImu();

      // Change Drive Mode
      yLP = yP;
      yP = gamepad1.y;
      if (!yLP && yP) {
        basicDrive = !basicDrive;
      }

      // Reset Angle
      if (gamepad1.x) {
        initAng = -dt.getAngleImu();
      }

      // Drive
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = floor(gamepad1.right_stick_x);
      strafe = floor(-gamepad1.right_stick_y * 1.1); // 1.1 counteracts imperfect strafing
      turn = turnFloor(gamepad1.left_stick_x);
      computeDrivePowers(gamepad1);

      bLP2 = bP2;
      bP2 = gamepad2.b;
      if (!bLP2 && bP2) {
        cycleMode = !cycleMode;
        stage = 0;
        first = false;
        cont = true;
        doneScoring = true;
        slideSystem.resetGrabber();
      }

      xLP2 = xP2;
      xP2 = gamepad2.x;
      xP2 = gamepad2.x;
      if (!xLP2 && xP2) {
        slideSystem.resetGrabber();
      }

      // the front claw automatically opens when extending...
      aLP = aP;
      aP = gamepad1.a;
      if (aP && !aLP) {
        if (cycleMode) {
          if (first) {
            first = false;
            slideSystem.initialGrab();
          } else {
            cont = false;
            slideSystem.setBackClawClawOpen();
            slideSystem.setFrontClawClose();
          }
        } else {
          if (stage == 0) {
            slideSystem.extendTransferMec();
            slideSystem.setClawJointOpen();
            cont = false;
          } else if (stage == 1) {
            slideSystem.setBackClawClawOpen();
            slideSystem.setFrontClawClose();
          } else if (stage == 2) {
            slideSystem.setFrontClawOpenFull();
          } else {
            slideSystem.setBackClawClawOpen();
          }
        }
        if (!cont) {
          doneScoring = false;
        }
      }

      dpDL2 = dpD2;
      dpD2 = gamepad2.dpad_down;
      if (dpD2 && !dpDL2) {
        mode = "low";
      }
      dpUL2 = dpU2;
      dpU2 = gamepad2.dpad_up;
      if (dpU2 && !dpUL2) {
        mode = "high";
      }
      dpLL2 = dpL2;
      dpL2 = gamepad2.dpad_left || gamepad2.dpad_right;
      if (dpL2 && !dpLL2) {
        mode = "mid";
      }

      if (!doneScoring) {
        if (cycleMode) {
          doneScoring = slideSystem.score();
        } else {
          if (stage == 0) {
            doneScoring = true;
          } else if (stage == 1) {
            doneScoring = slideSystem.scoreCircuitsStage1();
          } else if (stage == 2) {
            doneScoring = slideSystem.scoreCircuitsStage2(mode);
          } else {
            doneScoring = slideSystem.scoreCircuitsStage3();
          }
          if (doneScoring) {
            stage++;
            if (stage == 4) {
              stage = 1;
            }
          }
        }
      }

      // Display Values
      telemetry.addData("Drive Type", driveType);
      telemetry.addData("Odometry Info", dt.getCurrentPosition());
      telemetry.addData("Life Level:", mode);
      telemetry.addLine(dt.getTicks());
      telemetry.addData("Stage", stage);
      telemetry.update();
    }
  }
}
