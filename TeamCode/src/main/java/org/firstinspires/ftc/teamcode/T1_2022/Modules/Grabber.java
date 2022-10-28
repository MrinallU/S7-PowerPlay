package org.firstinspires.ftc.teamcode.T1_2022.Modules;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public class Grabber {
  public final double CLAW_OPEN_ELEVATED = 0.3,
      CLAW_OPEN_REST = 0.4,
      CLAW_CLOSE = 0,
      V4B_FRONT = 275,
      V4B_BACK = 3,
      V4B_FRONT_THRESHOLD = 5;
  public final int HIGH = 1000, MIDDLE = 700, LOW = 400, REST = 0;

  boolean armRested = true, v4bISFRONT = false;
  public String armStatusPrev = "null", clawStatus = "closed";

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

    resetClaw();
  }

  public void raiseTop() {
    grabCone();
    setV4B_FRONT();
    raiseToPosition(HIGH, 0.5);
    armRested = false;
  }

  public void raiseMiddle() {
    grabCone();
    setV4B_FRONT();
    raiseToPosition(MIDDLE, 0.5);
    armRested = false;
  }

  public void raiseLow() {
    grabCone();
    setV4B_FRONT();
    raiseToPosition(LOW, 0.5);
    armRested = false;
  }

  public void restArm() {
    grabCone();
    raiseToPosition(REST, 0.5);
    armRested = true;
  }

  public void updateArmPos(String armStatus) {
    if (!Objects.equals(armStatusPrev, armStatus)) {
      if (Objects.equals(armStatus, "high")) {
        raiseTop();
        armStatusPrev = armStatus;
      } else if (Objects.equals(armStatus, "low")) {
        raiseLow();
        armStatusPrev = armStatus;
      } else if (Objects.equals(armStatus, "middle")) {
        raiseMiddle();
      } else if (Objects.equals(armStatus, "rest")) {
        setV4B_BACK();
        if (Math.abs(v4b.retMotorEx().getTargetPosition() - v4b.encoderReading())
            <= V4B_FRONT_THRESHOLD) {
          if (!touchSensor.isPressed()) {
            restArm();
            armStatusPrev = "null";
          } else {
            leftSlide.setPower(0);
            rightSlide.setPower(0);
            leftSlide.resetEncoder(true);
            rightSlide.resetEncoder(true);
            armStatusPrev = armStatus;
            resetClaw();
          }
        }
      }
    }
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
    leftSlide.retMotorEx().setTargetPositionTolerance(3);
    leftSlide.toPosition();
    leftSlide.setPower(power);

    rightSlide.setTarget(pos2);
    rightSlide.retMotorEx().setTargetPositionTolerance(3);
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
    v4b.setTarget(V4B_FRONT);
    v4b.retMotorEx().setTargetPositionTolerance(3);
    v4b.toPosition();
    v4b.setPower(0.5);
    v4bISFRONT = true;
  }

  public void setV4B_BACK() {
    v4b.setTarget(V4B_BACK);
    v4b.retMotorEx().setTargetPositionTolerance(3);
    v4b.toPosition();
    v4b.setPower(0.5);
    v4bISFRONT = true;
  }
}
