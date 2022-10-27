package org.firstinspires.ftc.teamcode.T1_2022.Modules;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public class Grabber {
  public final double CLAW_OPEN = 0.5,
      CLAW_OPEN_MIDDLE = 0.25,
      CLAW_CLOSE = 0,
      V4B_FRONT = 1,
      V4B_BACK = 0,
      V4B_FRONT_THRESHOLD = 5;
  public final int HIGH = 0, HIGH_LEFT = 0, HIGH_RIGHT = 0;
  public final int MIDDLE = 0, MIDDLE_LEFT = 0, MIDDLE_RIGHT = 0;
  public final int LOW = 0, LOW_LEFT = 0, LOW_RIGHT = 0;
  public final int REST = 0;

  boolean armRested = true, v4bISFRONT = false;
  public String armStatusPrev = "null";

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

    grabCone();
    setV4B_BACK();
  }

  public void raiseTop() {
    setV4B_FRONT();
    raiseToPosition(HIGH, 0.5);
    // raiseToPosition(HIGH_LEFT, HIGH_RIGHT, 0.2);
    armRested = false;
  }

  public void raiseMiddle() {
    setV4B_FRONT();
    raiseToPosition(MIDDLE, 0.5);
    // raiseToPosition(MIDDLE_LEFT, MIDDLE_RIGHT, 0.2);
    armRested = false;
  }

  public void raiseLow() {
    setV4B_FRONT();
    raiseToPosition(LOW, 0.5);
    // raiseToPosition(LOW_LEFT, LOW_RIGHT, 0.2);
    armRested = false;
  }

  public void restArm() {
    raiseToPosition(REST, REST, 0.5);
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
          }
        }
      }
    }
  }

  public void raiseToPosition(int pos, double power) {
    leftSlide.setTarget(pos);
    leftSlide.retMotorEx().setTargetPositionTolerance(3);
    leftSlide.toPosition();
    leftSlide.setPower(power);

    rightSlide.setTarget(pos);
    rightSlide.retMotorEx().setTargetPositionTolerance(3);
    rightSlide.toPosition();
    rightSlide.setPower(power);
    //    double diff = rightSlide.encoderReading() - pos;
    //    if (Math.abs(diff) > 3) {
    //      rightSlide.setPower(diff * 0.05);
    //      leftSlide.setPower(diff * 0.05);
    //    }
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

  public void raiseToPosition(int pos) {
    raiseToPosition(pos, 0.2);
  }

  public void grabCone() {
    claw.setPosition(CLAW_CLOSE);
  }

  public void releaseCone() {
    claw.setPosition(CLAW_OPEN);
  }

  public void setV4B_FRONT() {
    v4b.setTarget(V4B_FRONT);
    v4b.retMotorEx().setTargetPositionTolerance(3);
    v4b.toPosition();
    v4b.setPower(0.3);
    v4bISFRONT = true;
  }

  public void setV4B_BACK() {
    v4b.setTarget(V4B_BACK);
    v4b.retMotorEx().setTargetPositionTolerance(3);
    v4b.toPosition();
    v4b.setPower(0.3);
    v4bISFRONT = true;
  }
}
