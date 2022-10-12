package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.T1_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "Armtest", group = "OdomBot")
public class GrabberTest extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Servo s = hardwareMap.get(Servo.class, "claw"),
        lv = hardwareMap.get(Servo.class, "v4bl"),
        rv = hardwareMap.get(Servo.class, "v4bl");

    Motor ls = new Motor(hardwareMap, "leftSlide"), rs = new Motor(hardwareMap, "rightSlide");
    Grabber grabber = new Grabber(ls, rs, lv, rv, s);
    String grabberStat = "rest";
    while (opModeIsActive()) {
      if (gamepad1.dpad_up) {
        grabberStat = "high";
      }

      if (gamepad1.dpad_down) {
        grabberStat = "rest";
      }

      if (gamepad1.dpad_left) {
        grabberStat = "middle";
      }

      grabber.updateArmPos(grabberStat);
    }
  }
}
