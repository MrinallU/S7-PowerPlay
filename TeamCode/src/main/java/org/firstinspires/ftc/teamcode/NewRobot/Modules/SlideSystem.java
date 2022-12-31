package org.firstinspires.ftc.teamcode.NewRobot.Modules;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.Objects;
import org.firstinspires.ftc.teamcode.Utils.Motor;

public class SlideSystem {
  Motor verticalLeftSlide, verticalRightSlide;
  Servo horizontalRightSlide,
      horizontalLeftSlide,
      frontClaw,
      backClaw,
      transferMecLeft,
      transferMecRight,
      backClawLiftLeft,
      backClawLiftRight;
  final double horizontalSlideExtendPos = 1000,
      horizontalSlideRetractPos = 0,
      verticalSlideExtendPos = 1,
      verticalSlideRetractPos = 0,
      frontClawOpen = 1,
      frontClawClose = 0,
      backClawOpen = 1,
      backClawClose = 0,
      transferMecExtend = 1,
      transferMecClose = 0,
      backClawLiftExtend = 1,
      backClawLiftRetract = 0;
  ElapsedTime timer = new ElapsedTime();
  String lastCmd = "null";

  public SlideSystem(
      Servo horizontalLeftSlide,
      Servo horizontalRightSlide,
      Motor verticalLeftSlide,
      Motor verticalRightSlide,
      Servo frontClaw,
      Servo backClaw,
      Servo transferMecLeft,
      Servo transferMecRight,
      Servo backClawLiftLeft,
      Servo backClawLiftRight) {
    this.horizontalLeftSlide = horizontalLeftSlide;
    this.horizontalRightSlide = horizontalRightSlide;
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
    this.backClawLiftRight.setPosition(backClawLiftRetract);
    this.backClawLiftLeft.setPosition(backClawLiftRetract);
    this.transferMecLeft.setPosition(transferMecClose);
    this.transferMecRight.setPosition(transferMecClose);
    this.horizontalLeftSlide.setPosition(horizontalSlideRetractPos);
    this.horizontalRightSlide.setPosition(horizontalSlideRetractPos);
  }

  public void initialGrab() {
    extendHorizontalSlides();
  }

  // everytime the claws are toggled (other than the first this is called) since it extends and
  // drops at the same time
  // keep running this method until the true parameter is returned.
  // todo: adjust the timers as necessary
  public boolean score() {
    if (!Objects.equals(lastCmd, "transferring")
        && !Objects.equals(lastCmd, "score")
        && !Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "dropping")) {
        timer.reset();
        lastCmd = "dropping";
      } else if (timer.milliseconds()
          < 500) { // half a second for the claws to grab or drop the cone
        return false;
      }
    }

    retractHorizontalSlides();
    retractVerticalSlides();

    if (!Objects.equals(lastCmd, "transferring") && !Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "score")) {
        timer.reset();
        lastCmd = "score";
      } else if (timer.milliseconds() < 2000) { // two seconds for the slides to retract
        return false;
      }
    }

    setFrontClawOpen();
    if (!Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "transferring")) {
        lastCmd = "transferring";
        timer.reset();
      } else if (timer.milliseconds()
          < 500) { // half a second for the bucket to fully grab onto the cone and for the front
        // claw to let go of the cone.
        return false;
      }
    }
    setBackClawClose();

    extendHorizontalSlides();
    if (!Objects.equals(lastCmd, "slidedelay")) {
      lastCmd = "slidedelay";
      timer.reset();
    } else if (timer.milliseconds()
        < 500) { // half a second for the transfer mechanism to extend to avoid collision
      return false;
    }

    extendVerticalSlides();
    lastCmd = "null";
    return true;
  }

  public boolean scoreCircuitsStage1() {
    if (!Objects.equals(lastCmd, "transferring")
        && !Objects.equals(lastCmd, "score")
        && !Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "dropping")) {
        timer.reset();
        lastCmd = "dropping";
      } else if (timer.milliseconds()
          < 500) { // half a second for the claws to grab or drop the cone
        return false;
      }
    }

    closeTransferMec();

    if (!Objects.equals(lastCmd, "transferring") && !Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "score")) {
        timer.reset();
        lastCmd = "score";
      } else if (timer.milliseconds() < 2000) { // two seconds for the slides to retract
        return false;
      }
    }

    setFrontClawOpen();
    if (!Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "transferring")) {
        lastCmd = "transferring";
        timer.reset();
      } else if (timer.milliseconds()
          < 500) { // half a second for the bucket to fully grab onto the cone and for the front
        // claw to let go of the cone.
        return false;
      }
    }
    setBackClawClose();

    extendTransferMec();
    if (!Objects.equals(lastCmd, "slidedelay")) {
      lastCmd = "slidedelay";
      timer.reset();
    } else if (timer.milliseconds()
        < 500) { // half a second for the transfer mechanism to extend to avoid collision
      return false;
    }

    extendVerticalSlides();
    lastCmd = "null";
    return true;
  }

  public boolean scoreCircuitsStage2() {
    if (!Objects.equals(lastCmd, "dropping")) {
      timer.reset();
      lastCmd = "dropping";
    } else if (timer.milliseconds() < 500) { // half a second for the claws to grab or drop the cone
      return false;
    }

    lastCmd = "null";
    retractVerticalSlides();
    return true;
  }

  public void resetGrabber() {
    // when you are done cycling invoke this to completely reset the robot.
    retractHorizontalSlides();
    retractVerticalSlides();
  }

  public void extendHorizontalSlides() {
    setFrontClawOpen();
    extendTransferMec();
    horizontalLeftSlide.setPosition(horizontalSlideExtendPos);
    horizontalRightSlide.setPosition(horizontalSlideExtendPos);
  }

  public void retractHorizontalSlides() {
    closeTransferMec();
    horizontalLeftSlide.setPosition(horizontalSlideRetractPos);
    horizontalRightSlide.setPosition(horizontalSlideRetractPos);
  }

  public void extendVerticalSlides() {
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

  public void retractVerticalSlides() {
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

  public void setFrontClawClose() {
    frontClaw.setPosition(frontClawClose);
  }

  public void setFrontClawOpen() {
    frontClaw.setPosition(frontClawOpen);
  }

  public void setBackClawClose() {
    backClaw.setPosition(backClawClose);
  }

  public void setBackClawClawOpen() {
    backClaw.setPosition(backClawOpen);
  }

  public void closeTransferMec() {
    transferMecLeft.setPosition(transferMecClose);
    transferMecRight.setPosition(transferMecClose);
  }

  public void extendTransferMec() {
    transferMecLeft.setPosition(transferMecExtend);
    transferMecRight.setPosition(transferMecExtend);
  }
}
