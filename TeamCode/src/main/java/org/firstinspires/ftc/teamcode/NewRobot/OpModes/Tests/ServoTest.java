package org.firstinspires.ftc.teamcode.NewRobot.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Utils.Motor;
/*
todo check for overexertion
 */
@TeleOp(name = "Servo_Tests", group = "OdomBot")
public class ServoTest extends LinearOpMode {
  int stage = 0;
  boolean first = true;
  boolean doneScoring = true;
  boolean cycleMode = true;

  @Override
  public void runOpMode() throws InterruptedException {
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    double curPos = 1, curPos2 = 0;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;
    //Motor vLeftS = new Motor(hardwareMap, "verticalLeftSlide");
   // Motor vRightS = new Motor(hardwareMap, "verticalRightSlide");

    // Servo
    Servo fClaw = hardwareMap.get(Servo.class, "v4bLeft");
    Servo fClawR = hardwareMap.get(Servo.class, "v4bRight");
//    Servo bClaw = hardwareMap.get(Servo.class, "backClaw");
//    Servo tl = hardwareMap.get(Servo.class, "transferMecLeft");
//    Servo tr = hardwareMap.get(Servo.class, "transferMecRight");
//    Servo cll = hardwareMap.get(Servo.class, "backClawLiftLeft");
//    Servo hLeftS = hardwareMap.get(Servo.class, "horizontalLeftSlide");
//    Servo hRightS = hardwareMap.get(Servo.class, "horizontalRightSlide");

    // Servo clawJoint = hardwareMap.get(Servo.class, "clawJoint");
    fClaw.setPosition(0);
    fClawR.setPosition(1);
    waitForStart();

    while (opModeIsActive()) {
      if (gamepad1.dpad_up && !lU) {
        curPos += 0.1;
      }

      if (gamepad1.a && !lA) {
        curPos2 += 0.1;
      }

      if (gamepad1.b && !lB) {
        curPos2 -= 0.1;
      }

      if (gamepad1.dpad_down && !lD) {
        curPos -= 0.1;
      }

     // if (gamepad1.dpad_left && !lL) {
        fClaw.setPosition(1);
        fClawR.setPosition(0);
      //}

      lU = gamepad1.dpad_up;
      lA = gamepad1.a;
      lB = gamepad1.b;
      lD = gamepad1.dpad_down;
      lL = gamepad1.dpad_left;

      telemetry.addData("left pos ", curPos);
      telemetry.addData("right pos ", curPos2);
      telemetry.update();
    }
  }
}
