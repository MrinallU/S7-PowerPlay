package org.firstinspires.ftc.teamcode.NewRobot.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.NewRobot.Modules.SlideSystem;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "SystemTest", group = "OdomBot")
public class FullSlideSystemTest extends LinearOpMode {
  int stage = 0;
  boolean first = true;
  boolean doneScoring = true;
  boolean cycleMode = true;

  @Override
  public void runOpMode() throws InterruptedException {
    SlideSystem slideSystem;
    Motor vLeftS = new Motor(hardwareMap, "verticalLeftSlide");
    Motor vRightS = new Motor(hardwareMap, "verticalRightSlide");
    // Button Variables
    boolean yP = false, yLP = false;
    boolean aP = false, aLP = false;
    boolean rP2 = false, rLP2 = false;
    boolean lP2 = false, lLP2 = false;
    boolean yP2 = false, yLP2 = false;
    boolean rSP2 = false, rSLP2 = false;
    boolean bP2 = false, bLP2 = false;
    boolean xP2 = false, xLP2 = false;
    boolean slowDrive = false, fastDrive = false;
    boolean basicDrive = false;

    // Servo
    Servo fClaw = hardwareMap.get(Servo.class, "frontClaw");
    Servo bClaw = hardwareMap.get(Servo.class, "backClaw");
    Servo tl = hardwareMap.get(Servo.class, "transferMecLeft");
    Servo tr = hardwareMap.get(Servo.class, "transferMecRight");
    Servo cll = hardwareMap.get(Servo.class, "backClawLiftLeft");
    Servo hLeftS = hardwareMap.get(Servo.class, "horizontalLeftSlide");
    Servo hRightS = hardwareMap.get(Servo.class, "horiztonalRightSlide");
    Servo clawJoint = hardwareMap.get(Servo.class, "clawJoint");
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    slideSystem =
        new SlideSystem(hLeftS, hRightS, vLeftS, vRightS, fClaw, bClaw, tl, tr, cll, clawJoint);
    waitForStart();

    while (opModeIsActive()) {
      // Always reset grabber before changing mode!!
      bLP2 = bP2;
      bP2 = gamepad2.b;
      if (!bLP2 && bP2) {
        cycleMode = !cycleMode;
        stage = 0;
        first = false;
      }

      xLP2 = xP2;
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
            slideSystem.setBackClawClawOpen();
            slideSystem.setFrontClawClose();
          }
        } else {
          if (stage == 0) {
            slideSystem.extendTransferMec();
          } else if (stage % 2 != 0) {
            slideSystem.setBackClawClawOpen();
            slideSystem.setFrontClawClose();
          } else {
            slideSystem.setBackClawClawOpen();
          }
        }
        doneScoring = false;
      }

      if (!doneScoring) {
        if (cycleMode) {
          doneScoring = slideSystem.score();
        } else {
          if (stage == 0) {
            doneScoring = true;
          } else if (stage % 2 != 0) {
            doneScoring = slideSystem.scoreCircuitsStage1();
          } else {
            doneScoring = slideSystem.scoreCircuitsStage2();
          }
          if (doneScoring) {
            stage++;
          }
        }
      }
      // Display Values
      telemetry.addData("Stage", stage);
      telemetry.update();
    }
  }
}
