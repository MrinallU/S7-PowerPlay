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
    Servo s = hardwareMap.get(Servo.class, "claw");

    Motor ls = new Motor(hardwareMap, "leftSlide"),
        rs = new Motor(hardwareMap, "rightSlide"),
        v4b = new Motor(hardwareMap, "v4b");
    TouchSensor touch = hardwareMap.get(TouchSensor.class, "touch_sensor");
    Grabber grabber = new Grabber(ls, rs, v4b, s, touch);
    double curPos = 0, curPos2 = 0;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;
    grabber.claw.setPosition(0);
    waitForStart();



    while (opModeIsActive()) {
      if(gamepad2.dpad_up){
        grabber.v4b.setPower(0.2);

      }else if(gamepad2.dpad_down){
        grabber.v4b.setPower(-0.2);

      }else{
        grabber.v4b.setPower(-0.025);
      }
    }
//
//    while (opModeIsActive()) {
//      if (gamepad1.dpad_up && !lU) {
//        curPos += 10;
//      }
//
//      //      if (gamepad1.a && !lA) {
//      //        curPos2 += 0.1;
//      //      }
//
//      //      if (gamepad1.b && !lB) {
//      //        curPos2 -= 0.1;
//      //      }
//
//      if (gamepad1.dpad_down && !lD) {
//        curPos -= 10;
//      }
//
//      if (gamepad1.dpad_left && !lL) {
//        grabber.v4b.setTarget(curPos);
//        grabber.v4b.retMotorEx().setTargetPositionTolerance(3);
//        grabber.v4b.toPosition();
//        grabber.v4b.setPower(0.3);
//      }
//
//      lU = gamepad1.dpad_up;
//      lA = gamepad1.a;
//      lB = gamepad1.b;
//      lD = gamepad1.dpad_down;
//      lL = gamepad1.dpad_left;
//
//      telemetry.addData("pos", curPos);
//      //      telemetry.addData("posRight", curPos);
//
//      telemetry.update();
//    }
  }
}
