package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.T1_2022.Base;
import org.firstinspires.ftc.teamcode.T1_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "Test_TeleOp", group = "OdomBot")
public class Test_TeleOp extends Base {

  // TeleOp Variables

  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this);
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    Motor fLeftMotor = new Motor(hardwareMap, "front_left_motor");
    Motor bLeftMotor = new Motor(hardwareMap, "back_left_motor");
    Motor fRightMotor = new Motor(hardwareMap, "front_right_motor");
    Motor bRightMotor = new Motor(hardwareMap, "back_right_motor");



    Servo s = hardwareMap.get(Servo.class, "claw");
            //lv = hardwareMap.get(Servo.class, "v4bl"),
            //rv = hardwareMap.get(Servo.class, "v4br");

    Motor ls = new Motor(hardwareMap, "leftSlide"), rs = new Motor(hardwareMap, "rightSlide");
    Motor fbr = new Motor(hardwareMap, "fourBar");
    Grabber grabber = new Grabber(ls, rs, fbr, s);

    fbr.resetEncoder(true);
    fRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    bRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

    boolean lastClaw = false, currClaw = false, clawOpen = false;
    boolean lastClawDrop = false, currClawDrop = false, clawOpen2 = false;
    boolean lastSlide = false, currSlide = false;
    boolean lastSlideUp = false, currSlideUp = false;
    double fLeftPow, bLeftPow, fRightPow, bRightPow;

    grabber.conePickUpPos();
    grabber.v4bRight.setPosition(1);
    grabber.v4bLeft.setPosition(0);


    waitForStart();
    matchTime.reset();

    while (opModeIsActive()) {
      // Updates
      dt.resetCache();
      currAngle = dt.getAngle();

      // Change Drive Mode
      yLP = yP;
      yP = gamepad1.y;
      if (!yLP && yP) {
        basicDrive = !basicDrive;
      }

      //      dpUL = dpU;
      //      dpU = gamepad1.dpad_up;
      //      if (!dpUL && dpU) {
      //        grabber.updateArmPos("high");
      //      }
      //
      //      dpDL = dpD;
      //      dpD = gamepad1.dpad_down;
      //      if (!dpDL && dpD) {
      //        grabber.updateArmPos("rest");
      //      }
      //
      //      dpLL  = dpL;
      //      dpL = gamepad1.dpad_left;
      //      if (!dpUL && dpU) {
      //        grabber.updateArmPos("middle");
      //      }
      //
      //      dpRL = dpR;
      //      dpR = gamepad1.dpad_right;
      //      if (!dpRL && dpR) {
      //        grabber.updateArmPos("middle");
      //      }

      // Drive
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = gamepad1.right_stick_y;
      strafe = gamepad1.right_stick_x;
      turn = gamepad1.left_stick_x;

      fLeftPow = -drive + turn - strafe;
      bLeftPow = -drive + turn + strafe;
      fRightPow = drive + turn - strafe;
      bRightPow = drive + turn + strafe;
      computeDrivePowers(gamepad1);


      lastSlide = currSlide;
      currSlide = gamepad1.a;
      if(currSlide && !lastSlide){
        grabber.raiseToPosition(0, 0.3);
        grabber.setV4B_BACK();

      }



      lastSlideUp = currSlideUp;
      currSlideUp = gamepad1.b;
      if(currSlideUp && !lastSlideUp){
        grabber.raiseToPosition(1000, 0.3);
        grabber.setV4B_FRONT();

      }

      lastClaw = currClaw;
      currClaw = gamepad1.right_bumper;
      if(currClaw && !lastClaw){
        clawOpen = !clawOpen;
        if(clawOpen){
          grabber.grabCone();
        }else{
          grabber.conePickUpPos();
        }
      }

      lastClawDrop = currClawDrop;
      currClawDrop = gamepad1.left_bumper;
      if(currClawDrop && !lastClawDrop){
        clawOpen2 = !clawOpen2;
        if(clawOpen2){
          grabber.releaseCone();
        }else{
          grabber.grabCone();
        }
      }



      // Display Values
      telemetry.addData("Drive Type", driveType);
      telemetry.addData("Right Slide:", grabber.rightSlide.retMotorEx().getCurrentPosition());
      telemetry.addData("Left Slide:", grabber.leftSlide.retMotorEx().getCurrentPosition());
      telemetry.addData("Four Bar Pos: ", grabber.fourBar.retMotorEx().getCurrentPosition());
      telemetry.addLine("UPLIFT IS ASS / Warning - Condition Is Always True");
      telemetry.update();
    }
  }
}
