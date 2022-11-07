package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "slide_test", group = "Tests")
public class SlideTest extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Servo s = hardwareMap.get(Servo.class, "claw");

    Motor ls = new Motor(hardwareMap, "leftSlide"),
        rs = new Motor(hardwareMap, "rightSlide"),
        v4b = new Motor(hardwareMap, "v4b");
    TouchSensor touch = hardwareMap.get(TouchSensor.class, "touch_sensor");
    Grabber grabber = new Grabber(ls, rs, v4b, s, touch);
    int curPos = 0, curPos2 = 0;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;

    waitForStart();

    while (opModeIsActive()) {
      if (gamepad1.dpad_up && !lU) {
        curPos += 10;
      }

      if (gamepad1.a && !lA) {
        curPos2 += 10;
      }

      if (gamepad1.b && !lB) {
        curPos2 -= 10;
      }

      if (gamepad1.dpad_down && !lD) {
        curPos -= 10;
      }

      if (gamepad1.dpad_left && !lL) {
        grabber.raiseToPosition(curPos, curPos2, 0.9);
      }

      lU = gamepad1.dpad_up;
      lA = gamepad1.a;
      lB = gamepad1.b;
      lD = gamepad1.dpad_down;
      lL = gamepad1.dpad_left;

      telemetry.addData("left slide pos ", curPos);
      telemetry.addData("right slide pos ", curPos2);
      telemetry.addData("touch sensor", grabber.touchSensor.isPressed());
      telemetry.update();
    }
  }
}
