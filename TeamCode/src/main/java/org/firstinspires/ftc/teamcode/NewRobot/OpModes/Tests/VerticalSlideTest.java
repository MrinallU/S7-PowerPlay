package org.firstinspires.ftc.teamcode.NewRobot.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.NewRobot.Modules.SlideSystem;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "VerticalSlideTest", group = "OdomBot")
public class VerticalSlideTest extends LinearOpMode {
  int stage = 0;
  boolean first = true;
  boolean doneScoring = true;
  boolean cycleMode = true;

  @Override
  public void runOpMode() throws InterruptedException {
    // initHardware(0, this);
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    int curPos = 0, curPos2 = 0;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;
    SlideSystem slideSystem;
    Motor vLeftS = new Motor(hardwareMap, "verticalLeftSlide");
    Motor vRightS = new Motor(hardwareMap, "verticalRightSlide");
    vRightS.setDirection(DcMotorSimple.Direction.FORWARD);
    vLeftS.setDirection(DcMotorSimple.Direction.REVERSE);
    // Servo
    Servo fClaw = hardwareMap.get(Servo.class, "frontClaw");
    Servo bClaw = hardwareMap.get(Servo.class, "backClaw");
    Servo tl = hardwareMap.get(Servo.class, "transferMecLeft");
    Servo tr = hardwareMap.get(Servo.class, "transferMecRight");
    Servo cll = hardwareMap.get(Servo.class, "backClawLiftLeft");
    Servo hLeftS = hardwareMap.get(Servo.class, "horizontalLeftSlide");
    Servo hRightS = hardwareMap.get(Servo.class, "horiztonalRightSlide");
    Servo clawJoint = hardwareMap.get(Servo.class, "clawJoint");

    slideSystem =
        new SlideSystem(hLeftS, hRightS, vLeftS, vRightS, fClaw, bClaw, tl, tr, cll, clawJoint);

    waitForStart();

    while (opModeIsActive()) {
      if (gamepad1.dpad_up && !lU) {
        curPos += 100;
      }

      if (gamepad1.a && !lA) {
        curPos2 += 100;
      }

      if (gamepad1.b && !lB) {
        curPos2 -= 100;
      }

      if (gamepad1.dpad_down && !lD) {
        curPos -= 100;
      }

      if (gamepad1.dpad_left && !lL) {
        vLeftS.setTarget(curPos);
        vLeftS.retMotorEx().setTargetPositionTolerance(1);
        vLeftS.toPosition();
        vLeftS.setPower(1);

        vRightS.setTarget(curPos2);
        vRightS.retMotorEx().setTargetPositionTolerance(1);
        vRightS.toPosition();
        vRightS.setPower(1);
      }

      lU = gamepad1.dpad_up;
      lA = gamepad1.a;
      lB = gamepad1.b;
      lD = gamepad1.dpad_down;
      lL = gamepad1.dpad_left;

      telemetry.addData("left slide pos ", curPos);
      telemetry.addData("right slide pos ", curPos2);
      telemetry.update();
    }
  }
}
