package org.firstinspires.ftc.teamcode.NewRobot.Modules;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Objects;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public class Grabber {
  Motor horizontalLeftSlide, horiztonalRightSlide, verticalLeftSlide, verticalRightSlide;
  Servo frontClaw, backClaw, transferMecLeft, transferMecRight, backClawLiftLeft, backClawLiftRight;

  final double horizontalSlideExtendPos = 1000, horizontalSlideRetractPos = 0,
          verticalSlideExtendPos = 1000, verticalSlideRetractPos = 0,
          frontClawOpen=1, frontClawClose=0,
          backClawOpen=1, backClawClose=0,
          transferMecExtend=1, transferMecClose=0,
          backClawLiftExtend=1, backClawLiftRetract=0;
  ElapsedTime timer = new ElapsedTime();
  String lastCmd = "null";

  public Grabber(Motor horizontalLeftSlide, Motor horiztonalRightSlide, Motor verticalLeftSlide, Motor verticalRightSlide,
  Servo frontClaw, Servo backClaw, Servo transferMecLeft, Servo transferMecRight, Servo backClawLiftLeft, Servo backClawLiftRight){
    this.horizontalLeftSlide = horizontalLeftSlide;
    this.horiztonalRightSlide = horiztonalRightSlide;
    this.verticalLeftSlide = verticalLeftSlide;
    this.verticalRightSlide = verticalRightSlide;
    this.frontClaw = frontClaw;
    this.backClaw = backClaw;
    this.transferMecLeft = transferMecLeft;
    this.transferMecRight = transferMecRight;
    this.backClawLiftLeft = backClawLiftLeft;
    this.backClawLiftRight = backClawLiftRight;
    setFrontClawOpen();
    setBackClawClawOpen();
    backClawLiftRight.setPosition(backClawLiftRetract);
    backClawLiftLeft.setPosition(backClawLiftRetract);
    transferMecLeft.setPosition(transferMecClose);
    transferMecRight.setPosition(transferMecClose);
  }

  public void initialGrab(){
    extendHorizontalSlides();
  }

  // everytime the claws are toggled (other than the first this is called) since it extends and drops at the same time
  // keep running this method until the true parameter is returned.
  // todo: adjust the timers as necessary
  public boolean score(){
    retractHorizontalSlides();
    retractVerticalSlides();

    if(!Objects.equals(lastCmd, "transferring")) {
      if (!Objects.equals(lastCmd, "score")) {
        timer.reset();
        lastCmd = "score";
      } else if (timer.milliseconds() < 2000) {
        return false;
      }
    }

    setBackClawClose();
    if(!Objects.equals(lastCmd, "transferring")){
      lastCmd = "transferring";
      timer.reset();
    }else if (timer.milliseconds() < 500) {
      return false;
    }
    setFrontClawOpen();

    extendHorizontalSlides();
    extendVerticalSlides();
    lastCmd = "null";
    return true;
  }

  public void resetGrabber(){
    // when you are done cycling invoke this to completely reset the robot.
    retractHorizontalSlides();
    retractVerticalSlides();
  }

  public void extendHorizontalSlides(){
    setFrontClawOpen();
    horizontalLeftSlide.setTarget(horizontalSlideExtendPos);
    horizontalLeftSlide.retMotorEx().setTargetPositionTolerance(1);
    horizontalLeftSlide.toPosition();
    horizontalLeftSlide.setPower(1);

    horiztonalRightSlide.setTarget(horizontalSlideExtendPos);
    horiztonalRightSlide.retMotorEx().setTargetPositionTolerance(1);
    horiztonalRightSlide.toPosition();
    horiztonalRightSlide.setPower(1);

    transferMecLeft.setPosition(transferMecExtend);
    transferMecRight.setPosition(transferMecExtend);
  }

  public void retractHorizontalSlides(){
    transferMecLeft.setPosition(transferMecClose);
    transferMecRight.setPosition(transferMecClose);

    horizontalLeftSlide.setTarget(horizontalSlideRetractPos);
    horizontalLeftSlide.retMotorEx().setTargetPositionTolerance(1);
    horizontalLeftSlide.toPosition();
    horizontalLeftSlide.setPower(1);

    horiztonalRightSlide.setTarget(horizontalSlideRetractPos);
    horiztonalRightSlide.retMotorEx().setTargetPositionTolerance(1);
    horiztonalRightSlide.toPosition();
    horiztonalRightSlide.setPower(1);
  }

  public void extendVerticalSlides(){
    verticalLeftSlide.setTarget(verticalSlideExtendPos);
    verticalLeftSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalLeftSlide.toPosition();
    verticalLeftSlide.setPower(1);

    verticalRightSlide.setTarget(verticalSlideExtendPos);
    verticalRightSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalRightSlide.toPosition();
    verticalRightSlide.setPower(1);

    backClawLiftRight.setPosition(backClawLiftExtend);
    backClawLiftLeft.setPosition(backClawLiftExtend);
  }

  public void retractVerticalSlides(){
    backClawLiftRight.setPosition(backClawLiftRetract);
    backClawLiftLeft.setPosition(backClawLiftRetract);

    verticalLeftSlide.setTarget(verticalSlideRetractPos);
    verticalLeftSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalLeftSlide.toPosition();
    verticalLeftSlide.setPower(1);

    verticalRightSlide.setTarget(verticalSlideRetractPos);
    verticalRightSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalRightSlide.toPosition();
    verticalRightSlide.setPower(1);
  }

  public void setFrontClawClose(){
    frontClaw.setPosition(frontClawClose);
  }

  public void setFrontClawOpen(){
    frontClaw.setPosition(frontClawOpen);
  }

  public void setBackClawClose(){
    backClaw.setPosition(backClawClose);
  }

  public void setBackClawClawOpen(){
    backClaw.setPosition(backClawOpen);
  }
}
