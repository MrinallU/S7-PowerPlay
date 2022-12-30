package org.firstinspires.ftc.teamcode.T3_2022.Modules;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public class Grabber {
  public final double CLAW_OPEN_ELEVATED = 0.4, CLAW_OPEN_REST = 0.4, CLAW_CLOSE = 0.2;
  public final int HIGH = 2500, MIDDLE = 1650, MIDDLE_AUTO = 1700, LOW = 700, REST = 0, STACK = 600;
  boolean armRested = true, v4bExtended;
  public final ElapsedTime GrabberTimer = new ElapsedTime();
  public String armStatusPrev = "rest", clawStatus;
  public Motor leftSlide, rightSlide, v4b;
  public Servo claw, v4bLeft, v4bRight;
  public TouchSensor touchSensor;

  public Grabber(Motor l, Motor r, Servo vbL, Servo vbR, Servo g, TouchSensor t) {
    leftSlide = l;
    rightSlide = r;
    v4bLeft = vbL;
    v4bRight = vbR;
    claw = g;
    touchSensor = t;

    rightSlide.setDirection(DcMotorSimple.Direction.FORWARD);
    leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    v4b.setDirection(DcMotorSimple.Direction.FORWARD);
    leftSlide.resetEncoder(true);
    rightSlide.resetEncoder(true);
    v4b.resetEncoder(true);
    clawStatus = "open";
    v4bExtended = false;
    resetClaw();
    GrabberTimer.reset();
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

  public void raiseStack() {
    raiseToPosition(STACK, 1);
  }

  public void restArm() {
    raiseToPosition(REST, 1);
    armRested = true;
  }

  public void updateArmPos(String armStatus) {
    if (Objects.equals(armStatus, "high")) {
      raiseTop();
      extendV4b();
      armStatusPrev = armStatus;
    } else if (Objects.equals(armStatus, "low")) {
      extendV4b();
      raiseLow();
      armStatusPrev = armStatus;
    } else if (Objects.equals(armStatus, "middle")) {
      extendV4b();
      raiseMiddle();
      armStatusPrev = armStatus;
    } else if (Objects.equals(armStatus, "rest")) {
      retractV4b();
      restArm();
      armStatusPrev = armStatus;
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

  public void extendV4b() {
    v4bLeft.setPosition(0.5);
    v4bRight.setPosition(0.5);
  }

  public void retractV4b() {
    if (Objects.equals(clawStatus, "open")) {
      grabCone();
      GrabberTimer.reset();
    }
    if (GrabberTimer.seconds() > 2) {
      v4bLeft.setPosition(0);
      v4bRight.setPosition(0);
    }
  }
}
