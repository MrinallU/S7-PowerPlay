package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.teamcode.T1_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "v4b_test", group = "Tests")
public class v4b_test extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Servo s = hardwareMap.get(Servo.class, "claw"),
        lv = hardwareMap.get(Servo.class, "v4bl"),
        rv = hardwareMap.get(Servo.class, "v4br");

    Motor ls = new Motor(hardwareMap, "leftSlide"), rs = new Motor(hardwareMap, "rightSlide");
    TouchSensor t = hardwareMap.get(TouchSensor.class, "touch_sensor");
    Grabber grabber = new Grabber(ls, rs, lv, rv, s, t);
    double curPos = 0, curPos2 = 0;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;
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

      if (gamepad1.dpad_left && !lL) {
        grabber.v4bLeft.setPosition(curPos);
        grabber.v4bRight.setPosition(curPos2);
      }

      lU = gamepad1.dpad_up;
      lA = gamepad1.a;
      lB = gamepad1.b;
      lD = gamepad1.dpad_down;
      lL = gamepad1.dpad_left;

      telemetry.addData("posLeft", curPos);
      telemetry.addData("posRight", curPos);

      telemetry.update();
    }
  }
}
