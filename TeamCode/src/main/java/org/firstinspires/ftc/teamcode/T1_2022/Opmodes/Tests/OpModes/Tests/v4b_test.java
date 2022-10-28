package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.T1_2022.Modules.Grabber;
import org.firstinspires.ftc.teamcode.Utils.Motor;

@TeleOp(name = "v4b_test", group = "Tests")
public class v4b_test extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Servo s = hardwareMap.get(Servo.class, "claw");
        //lv = hardwareMap.get(Servo.class, "v4bl"),
        //rv = hardwareMap.get(Servo.class, "v4br");

    Motor ls = new Motor(hardwareMap, "leftSlide"), rs = new Motor(hardwareMap, "rightSlide");
    Motor fbr = new Motor(hardwareMap, "fourBar");
    Grabber grabber = new Grabber(ls, rs, fbr, s);

    double curPos = 0, curPos2 = 0;
    boolean lU = false, lD = false, lL = false, lA = false, lB = false;
    boolean lastBar = false, currBar = false, barUp = false;
    //grabber.v4bRight.setPosition(1);
    //grabber.v4bLeft.setPosition(0);

    waitForStart();







    while (opModeIsActive()) {
      lastBar = currBar;
      currBar = gamepad1.a;
      if(currBar && !lastBar){
        barUp = !barUp;
        if(barUp){
          grabber.setV4B_FRONT();
        }else{
          grabber.setV4B_BACK();
        }
      }
      //encoder pos: 275

      /*lastBar = currBar;
      currBar = gamepad1.a;
      if(currBar && !lastBar) {
        barUp = !barUp;
        if(barUp){
          barSupport.setTarget(60);
          barSupport.retMotorEx().setTargetPositionTolerance(3);
          barSupport.toPosition();
          barSupport.setPower(0.5);
          grabber.v4bLeft.setPosition(1);
        }
        else{
          barSupport.setTarget(0);
          barSupport.retMotorEx().setTargetPositionTolerance(3);
          barSupport.toPosition();
          barSupport.setPower(0.3);
          grabber.v4bLeft.setPosition(0);
        }
      }*/
      /*lastBar = currBar;
      currBar = gamepad1.a;
      if(currBar && !lastBar){
        barUp = !barUp;
        if(barUp){
          grabber.v4bRight.setPosition(0.08);
          grabber.v4bLeft.setPosition(0.92);
        }else{
          grabber.v4bRight.setPosition(1);
          grabber.v4bLeft.setPosition(0);
        }
      }

      if (gamepad1.dpad_left && !lL) {
        //grabber.v4bLeft.setPosition(curPos);
        //grabber.v4bRight.setPosition(curPos2);
      }

      lU = gamepad1.dpad_up;
      lA = gamepad1.a;
      lB = gamepad1.b;
      lD = gamepad1.dpad_down;
      lL = gamepad1.dpad_left;*/



      telemetry.addData("Encoder Pos: ", fbr.retMotorEx().getCurrentPosition());

      telemetry.update();
    }
  }
}
