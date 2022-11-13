package org.firstinspires.ftc.teamcode.T2_2022.Modules;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public class Grabber {
  public final double CLAW_OPEN_ELEVATED = 0.5, CLAW_OPEN_REST = 0.506, CLAW_CLOSE = 0.1;
  public int manualPos = 0;
  public final int HIGH = 2400, MIDDLE = 1650, MIDDLE_AUTO = 1700, LOW = 700, REST = 0, STACK = 600;
  boolean armRested = true, v4bISFRONT = false, manualControlV4b = false;
  public String armStatusPrev = "rest", clawStatus;
  public Motor leftSlide, rightSlide, v4b;
  public Servo claw;
  public TouchSensor touchSensor;

  public Grabber(Motor l, Motor r, Motor vb, Servo g, TouchSensor t) {
    leftSlide = l;
    rightSlide = r;
    v4b = vb;
    claw = g;
    touchSensor = t;

    rightSlide.setDirection(DcMotorSimple.Direction.FORWARD);
    leftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    v4b.setDirection(DcMotorSimple.Direction.FORWARD);
    leftSlide.resetEncoder(true);
    rightSlide.resetEncoder(true);
    v4b.resetEncoder(true);
    clawStatus = "open";
    resetClaw();
  }

  public void raiseTop() {
    //    grabCone();
    // setV4B_FRONT();
    raiseToPosition(HIGH, 1);
    armRested = false;
  }

  public void raiseStack() {
    raiseToPosition(STACK, 1);
  }

  public void raiseMiddle() {
    // grabCone();
    // setV4B_FRONT();
    raiseToPosition(MIDDLE, 1);
    armRested = false;
  }

  public void raiseMiddleAuto() {
    // grabCone();
    // setV4B_FRONT();
    raiseToPosition(MIDDLE_AUTO, 1);
    armRested = false;
  }

  public void raiseLow() {
    //    grabCone();
    // setV4B_FRONT();
    raiseToPosition(LOW, 1);
    armRested = false;
  }

  public void restArm() {
    grabCone();
    raiseToPosition(REST, 1);
    armRested = true;
  }

  public void updateArmPos(String armStatus) {
    if (Objects.equals(armStatus, "high")) {
      setV4B_FRONT();
      raiseTop();
      armStatusPrev = armStatus;
    } else if (Objects.equals(armStatus, "low")) {
      setV4B_FRONT();
      raiseLow();
      armStatusPrev = armStatus;
    } else if (Objects.equals(armStatus, "middle")) {
      setV4B_FRONT();
      raiseMiddle();
    } else if (Objects.equals(armStatus, "rest")) {
      setV4B_BACK();
      if (!v4bISFRONT) {
        if (Math.abs(rightSlide.encoderReading()) > 2) {
          restArm();
        } else {
          resetClaw();
          leftSlide.setPower(0);
          rightSlide.setPower(0);
          leftSlide.resetEncoder(true);
          rightSlide.resetEncoder(true);
          v4b.resetEncoder(true);
        }
        armStatusPrev = armStatus;
      }
    }
    //    else if (Objects.equals(armStatus, "manual")) {
    //      raiseToPosition(manualPos, 0.5);
    //      armStatusPrev = armStatus;
    //    }
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

  public void setV4B_FRONT() {
    if (v4b.encoderReading() < 200) {
      v4b.setPower(1);
    } else {
      v4b.setPower(0);
      v4bISFRONT = true;
    }
  }

  public void setV4B_BACK() {
    if (v4b.encoderReading() > 150) {
      v4b.setPower(-1);
    } else {
      v4bISFRONT = false;
      v4b.setPower(0);
      v4b.resetEncoder(true);
    }
  }
}
