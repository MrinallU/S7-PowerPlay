package org.firstinspires.ftc.teamcode.T1_2022.Modules;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public class Grabber {
  public final double CLAW_OPEN = 0.4,
      CLAW_CLOSE = 1,
      CLAW_DROP = 0.7,
      V4B_FRONT_LEFT = 1,
      V4B_FRONT_RIGHT = 1,
      V4B_BACK_LEFT = 0,
      V4B_BACK_RIGHT = 0,
      V4B_FRONT_THRESHOLD = 0.1;
  public final int HIGH = 0, HIGH_LEFT = 0, HIGH_RIGHT = 0;
  public final int MIDDLE = 0, MIDDLE_LEFT = 0, MIDDLE_RIGHT = 0;
  public final int LOW = 0, LOW_LEFT = 0, LOW_RIGHT = 0;
  public final int REST = 0;

  boolean armRested = true, v4bISFRONT = false;
  public String armStatusPrev = "rest";

  public Motor leftSlide, rightSlide;
  public Servo claw, v4bLeft, v4bRight;

  public Grabber(Motor l, Motor r, Servo ls, Servo rs, Servo g) {
    leftSlide = l;
    rightSlide = r;
    v4bLeft = ls;
    v4bRight = rs;
    claw = g;

    leftSlide.setDirection(DcMotorSimple.Direction.FORWARD);
    rightSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    leftSlide.resetEncoder(true);
    rightSlide.resetEncoder(true);
    leftSlide.toPosition();
    rightSlide.toPosition();

    v4bRight.setPosition(1);
    v4bLeft.setPosition(0);
    claw.setPosition(CLAW_CLOSE);
  }

  public void raiseTop() {
    setV4B_FRONT();
    // raiseToPosition(HIGH, 0.5);
    raiseToPosition(HIGH_LEFT, HIGH_RIGHT, 0.2);
    armRested = false;
  }

  public void raiseMiddle() {
    setV4B_FRONT();
    // raiseToPosition(MIDDLE, 0.5);
    raiseToPosition(MIDDLE_LEFT, MIDDLE_RIGHT, 0.2);
    armRested = false;
  }

  public void raiseLow() {
    setV4B_FRONT();
    // raiseToPosition(LOW, 0.5);
    raiseToPosition(LOW_LEFT, LOW_RIGHT, 0.2);
    armRested = false;
  }

  public void restArm() {
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
        armStatusPrev = armStatus;
      } else if (Objects.equals(armStatus, "rest")) {
        setV4B_BACK();
        armStatusPrev = "null";
        if (v4bRight.getPosition() <= V4B_FRONT_THRESHOLD) {
          restArm();
          armStatusPrev = armStatus;
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
    claw.setPosition(CLAW_OPEN);
  }

  public void releaseCone() {
    claw.setPosition(CLAW_DROP);
  }

  public void setToGrab() {claw.setPosition(CLAW_OPEN);}

  public void setV4B_FRONT() {
    v4bLeft.setPosition(V4B_FRONT_LEFT);
    v4bRight.setPosition(V4B_FRONT_RIGHT);
    v4bISFRONT = true;
  }

  public void setV4B_BACK() {
    v4bLeft.setPosition(V4B_BACK_LEFT);
    v4bRight.setPosition(V4B_BACK_RIGHT);
    v4bISFRONT = false;
  }

  public int getSlidePos() {
    return Math.max(leftSlide.encoderReading(), rightSlide.encoderReading());
  }
}
