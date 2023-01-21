package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@Disabled
@TeleOp(name = "v4b_test", group = "Tests")
public class v4b_test extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Servo v4bLeft;
    Servo v4bRight;
    Motor rightSlide, leftSlide;
    Motor fLeftMotor = new Motor(hardwareMap, "front_left_motor");
    Motor bLeftMotor = new Motor(hardwareMap, "back_left_motor");
    Motor fRightMotor = new Motor(hardwareMap, "front_right_motor");
    Motor bRightMotor = new Motor(hardwareMap, "back_right_motor");


    v4bRight = hardwareMap.get(Servo.class, "v4bRight");
    v4bLeft = hardwareMap.get(Servo.class, "v4bLeft");
    Servo s = hardwareMap.get(Servo.class, "claw");

    rightSlide = new Motor(hardwareMap, "rightSlide");
    leftSlide = new Motor(hardwareMap, "leftSlide");

    TouchSensor t = hardwareMap.get(TouchSensor.class, "touch_sensor");


    Motor ls = new Motor(hardwareMap, "leftSlide"),
            rs = new Motor(hardwareMap, "rightSlide");


    Grabber arm = new Grabber(ls, rs, s, t);




    boolean buttonCurr = false, buttonLast = false;

    boolean buttonCurrB = false, buttonLastB = false;

    boolean middleCurr = false, middleLast = false;

    boolean clawLast = false, clawCurr = false;

    boolean clawOpen = false;




    /*Servo s = hardwareMap.get(Servo.class, "claw");

    Motor ls = new Motor(hardwareMap, "leftSlide"),
        rs = new Motor(hardwareMap, "rightSlide"),
        v4b = new Motor(hardwareMap, "v4b");
    TouchSensor touch = hardwareMap.get(TouchSensor.class, "touch_sensor");
    Grabber grabber = new Grabber(ls, rs, v4b, s, touch);
    double curPos = 0, curPos2 = 0;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;
    grabber.claw.setPosition(0);*/
    v4bRight.setPosition(0);
    v4bLeft.setPosition(1);
    waitForStart();
    while(opModeIsActive()){

//      middleLast = middleCurr;
//      middleCurr = gamepad1.y;
//      if(middleCurr && !middleLast){
//        v4bRight.setPosition(0);
//        v4bLeft.setPosition(1);
//        if(v4bRight.getPosition() < 0.1){
//          arm.resetClaw();
//          clawOpen = true;
//        }
//      }
//
//      buttonLast = buttonCurr;
//      buttonCurr = gamepad1.a;
//      if(buttonCurr && !buttonLast){
//        arm.raiseToPosition(arm.HIGH, 1);
//        v4bRight.setPosition(0.7);
//        v4bLeft.setPosition(0.3);
//
//      }
//
//      buttonLastB = buttonCurrB;
//      buttonCurrB = gamepad1.b;
//      if(buttonCurrB && !buttonLastB){
//        arm.raiseToPosition(0, 1);
//        v4bRight.setPosition(0.3);
//        v4bLeft.setPosition(0.7);
//      }
//
//      clawLast = clawCurr;
//      clawCurr = gamepad1.right_bumper;
//      if(clawCurr && !clawLast){
//        clawOpen = !clawOpen;
//        if(clawOpen){
//          if(arm.rightSlide.retMotorEx().getCurrentPosition() < 200){
//            arm.resetClaw();
//          }else{
//            arm.releaseCone();
//          }
//        }else{
//          arm.grabCone();
//        }
//      }

      if(gamepad1.dpad_up){
        fLeftMotor.setPower(1);
      }else{
        fLeftMotor.setPower(0);
      }


      if(gamepad1.dpad_down){
        fRightMotor.setPower(1);
      }else{
        fRightMotor.setPower(0);
      }


      if(gamepad1.dpad_left) {
        bRightMotor.setPower(1);
      }else {
        bRightMotor.setPower(0);
      }

      if(gamepad1.dpad_right){
        bLeftMotor.setPower(1);
      }else{
        bLeftMotor.setPower(0);
      }

      telemetry.addData("Front Right: ", fRightMotor.retMotorEx().getCurrentPosition());
      telemetry.addData("Front Left: ", fLeftMotor.retMotorEx().getCurrentPosition());
      telemetry.addData("BackRight: ", bRightMotor.retMotorEx().getCurrentPosition());
      telemetry.addData("Back Left:", bLeftMotor.retMotorEx().getCurrentPosition());

      telemetry.update();



    }

  //
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
