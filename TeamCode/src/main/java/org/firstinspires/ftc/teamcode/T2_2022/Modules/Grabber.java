package org.firstinspires.ftc.teamcode.T2_2022.Modules;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public class Grabber {
  public final double CLAW_OPEN_ELEVATED = 0.3, CLAW_OPEN_REST = 0.35, CLAW_CLOSE = 0.12;
  public int manualPos = 0;
  public final int HIGH = 3000, MIDDLE = 860, MIDDLE_AUTO = 2150, MIDDLE_LOCK = 1900, LOW = 100, REST = 0, STACK = 400, STACK_UP = 1300;
  public boolean armRested = true, v4bExtended=false, v4bMoving=false, clawToggle = false;
  public String armStatusPrev = "rest", clawStatus;
  public Motor leftSlide, rightSlide, v4b;
  public Servo claw;
  public TouchSensor touchSensor;

  public Grabber(Motor l, Motor r, Servo g, TouchSensor t) {
    leftSlide = l;
    rightSlide = r;

    claw = g;
    touchSensor = t;

    rightSlide.setDirection(DcMotorSimple.Direction.FORWARD);
    leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    //v4b.setDirection(DcMotorSimple.Direction.FORWARD);
    leftSlide.resetEncoder(true);
    rightSlide.resetEncoder(true);
    //v4b.resetEncoder(true);
    clawStatus = "open";
    v4bExtended = false;
    v4bMoving = false;
    clawToggle = false;
    grabCone();
  }

  public void raiseTop() {
    raiseToPosition(HIGH, 1);
    armRested = false;
  }

  public void raiseMiddle() {
    raiseToPosition(MIDDLE, 1);
    armRested = false;
  }

  public void raiseMiddleAuto() {
    raiseToPosition(MIDDLE_AUTO, 1);
    armRested = false;
  }

  public void raiseLow() {
    raiseToPosition(LOW, 1);
    armRested = false;
  }

  public void stackUp(){
    raiseToPosition(STACK_UP, 1);
    armRested = false;
  }

  public void lock(){
    raiseToPosition(MIDDLE_LOCK, 1);
    armRested = false;
  }

  public void raiseStack2(){
    raiseToPosition(STACK - 65, 1);
    armRested = false;
  }

  public void raiseStack() {
    raiseToPosition(STACK, 1);
  }

  public void restArm() {
    raiseToPosition(REST, 1);
    armRested = true;
  }

  public void updateArmPos(String armStatus, Gamepad gamepad) {
    if (Objects.equals(armStatus, "high")) {
      raiseTop();
      extendV4b(gamepad);

      armStatusPrev = armStatus;
    } else if (Objects.equals(armStatus, "low")) {
      extendV4b(gamepad);
      raiseLow();
      armStatusPrev = armStatus;
    } else if (Objects.equals(armStatus, "middle")) {
      extendV4b(gamepad);
      raiseMiddle();
      armStatusPrev = armStatus;
    } else if (Objects.equals(armStatus, "rest")) {
      retractV4b();
      restArm();
    }
  }

  public void resetGrabber() {
    leftSlide.setPower(0);
    rightSlide.setPower(0);
    leftSlide.resetEncoder(true);
    rightSlide.resetEncoder(true);
    v4b.resetEncoder(true);
  }

  public void raiseToPosition(int pos, double power) {
    leftSlide.setTarget(pos);
    leftSlide.retMotorEx().setTargetPositionTolerance(1);
    leftSlide.toPosition();
    leftSlide.setPower(power);

    rightSlide.setTarget(pos);
    rightSlide.retMotorEx().setTargetPositionTolerance(1);
    rightSlide.toPosition();
    rightSlide.setPower(power);
  }

  public void raiseToPosition(int pos1, int pos2, double power) {
    leftSlide.setTarget(pos1);
    leftSlide.retMotorEx().setTargetPositionTolerance(1);
    leftSlide.toPosition();
    leftSlide.setPower(power);

    rightSlide.setTarget(pos2);
    rightSlide.retMotorEx().setTargetPositionTolerance(1);
    rightSlide.toPosition();
    rightSlide.setPower(power);
  }

  public void grabCone() {
    claw.setPosition(CLAW_CLOSE);
    clawStatus = "closed";
  }

  public void releaseCone() {
    claw.setPosition(CLAW_OPEN_ELEVATED);
    clawStatus = "open";
  }

  public void resetClaw() {
    claw.setPosition(CLAW_OPEN_REST);
    clawStatus = "open";
  }

  public void extendV4b(Gamepad gamepad) {
    if (v4b.encoderReading() < 190 && !gamepad.dpad_up) {
      v4bMoving = true;
      v4b.setPower(0.4);
    } else if(!gamepad.dpad_up){
      v4b.setPower(0);
      v4bMoving = false;
      v4bExtended = true;
    }
  }

  public void retractV4b() {
    if (v4b.encoderReading() > 185) {
      v4b.setPower(-0.55);
      clawStatus = "close";
      clawToggle = false;
    } else {
      v4bMoving = false;
      v4bExtended = false;
      v4b.setPower(0);
      v4b.resetEncoder(true);
      if(rightSlide.retMotorEx().getCurrentPosition() <= 100){
        if(!clawToggle){resetClaw(); clawToggle = true;}
      }

    }
  }
}
