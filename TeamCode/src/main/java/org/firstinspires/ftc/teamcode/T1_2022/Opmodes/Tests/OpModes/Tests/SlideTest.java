package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.T1_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "slide_test", group = "Tests")
public class SlideTest extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Servo s = hardwareMap.get(Servo.class, "claw");


    Motor ls = new Motor(hardwareMap, "leftSlide"), rs = new Motor(hardwareMap, "rightSlide");
    Motor fbr = new Motor(hardwareMap, "fourBar");
    Grabber grabber = new Grabber(ls, rs, fbr, s);
    int curPos = 0, curPos2 = 0;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;
    boolean currSlide = false, lastSlide = false;

    boolean currThing = false, lastThing = false;
    //grabber.v4bRight.setPosition(1);
    //grabber.v4bLeft.setPosition(0);
    grabber.setV4B_BACK();
    waitForStart();


    while (opModeIsActive()) {



      lastSlide = currSlide;
      currSlide = gamepad1.a;
      if(currSlide && !lastSlide){
        grabber.raiseToPosition(0, 0.3);
        grabber.setV4B_BACK();


      }



      lastThing = currThing;
      currThing = gamepad1.b;
      if(currThing && !lastThing){
        grabber.raiseToPosition(600, 0.3);
        grabber.setV4B_FRONT();
      }



      /*if (gamepad1.dpad_up && !lU) {
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
      lL = gamepad1.dpad_left;*/

      telemetry.addData("Slide Pos: ", grabber.rightSlide.retMotorEx().getCurrentPosition());
      telemetry.addData("Left Pos: ", grabber.leftSlide.retMotorEx().getCurrentPosition());
      telemetry.addData("right slide pos ", curPos2);
      telemetry.update();
    }
  }
}
