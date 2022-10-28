package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.T1_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "claw_test", group = "Tests")
public class ClawTest extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Servo s = hardwareMap.get(Servo.class, "claw");
        //lv = hardwareMap.get(Servo.class, "v4bl"),
        //rv = hardwareMap.get(Servo.class, "v4br");

    Motor ls = new Motor(hardwareMap, "leftSlide"), rs = new Motor(hardwareMap, "rightSlide");
    Motor fbr = new Motor(hardwareMap, "fourBar");
    Grabber grabber = new Grabber(ls, rs, fbr, s);
    double curPos = 1;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;

    waitForStart();
    while (opModeIsActive()) {
      if (gamepad1.dpad_up && !lU) {
        curPos += 0.1;
      }

      if (gamepad1.dpad_down && !lD) {
        curPos -= 0.1;
      }

      if (gamepad1.dpad_left && !lL) {
        grabber.claw.setPosition(curPos);
      }

      lU = gamepad1.dpad_up;
      lD = gamepad1.dpad_down;
      lL = gamepad1.dpad_left;

      telemetry.addData("pos", curPos);

      telemetry.update();
    }
  }
}
