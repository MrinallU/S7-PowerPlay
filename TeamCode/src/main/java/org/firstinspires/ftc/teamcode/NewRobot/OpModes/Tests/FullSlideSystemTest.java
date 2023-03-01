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
  boolean cont = true;

  @Override
  public void runOpMode() throws InterruptedException {
    SlideSystem slideSystem;
//    Motor vLeftS = new Motor(hardwareMap, "verticalLeftSlide");
//    Motor vRightS = new Motor(hardwareMap, "verticalRightSlide");
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
    Servo fClaw = hardwareMap.get(Servo.class, "claw");
    Servo bClaw = hardwareMap.get(Servo.class, "block");
    Servo tl = hardwareMap.get(Servo.class, "intakeLeft");
    Servo tr = hardwareMap.get(Servo.class, "intakeRight");
    Servo cll = hardwareMap.get(Servo.class, "chain");
    Servo hLeftS = hardwareMap.get(Servo.class, "leftSlide");
    Servo hRightS = hardwareMap.get(Servo.class, "rightSlide");
    Servo clawJoint = hardwareMap.get(Servo.class, "wrist");

    telemetry.addData("Status", "Initialized");
    telemetry.update();

    slideSystem =
        new SlideSystem(hLeftS, hRightS, fClaw, bClaw, tl, tr, cll, clawJoint);
    waitForStart();

    while (opModeIsActive()) {
      bLP2 = bP2;
      bP2 = gamepad2.b;
      if (!bLP2 && bP2) {
        cycleMode = !cycleMode;
        stage = 0;
        first = false;
        cont = true;
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
          } else if (stage % 2 != 0) {
            slideSystem.setBackClawClawOpen();
            slideSystem.setFrontClawClose();
          } else {
            slideSystem.setBackClawClawOpen();
          }
        }
        if(!cont) {
          doneScoring = false;
        }
      }

      telemetry.addLine(String.valueOf(doneScoring));

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
      telemetry.addLine(String.valueOf(cycleMode));
      telemetry.addLine(String.valueOf(doneScoring));
      telemetry.addData("Stage", stage);
      telemetry.addData("time", slideSystem.timer.milliseconds());
      telemetry.addLine(slideSystem.lastCmd);
      telemetry.update();
    }
  }
}
