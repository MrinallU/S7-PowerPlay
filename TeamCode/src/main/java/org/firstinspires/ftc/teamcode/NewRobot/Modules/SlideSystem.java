package org.firstinspires.ftc.teamcode.NewRobot.Modules;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
      clawJoint;
  final double horizontalSlideExtendPos = 1000,
      horizontalSlideRetractPos = 0,
      verticalSlideExtendPos = 1,
      verticalSlideRetractPos = 0,
      frontClawOpen = 1,
      frontClawClose = 0,
      backClawOpen = 1,
      backClawClose = 0,
      clawJointExtend = 1,
      clawJointClose = 0,
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
      Servo clawJoint) {
    this.horizontalLeftSlide = horizontalLeftSlide;
    this.horizontalRightSlide = horizontalRightSlide;
    this.verticalLeftSlide = verticalLeftSlide;
    this.verticalRightSlide = verticalRightSlide;
    this.frontClaw = frontClaw;
    this.backClaw = backClaw;
    this.transferMecLeft = transferMecLeft;
    this.transferMecRight = transferMecRight;
    this.backClawLiftLeft = backClawLiftLeft;
    this.clawJoint = clawJoint;

    this.verticalLeftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    this.verticalRightSlide.setDirection(DcMotorSimple.Direction.FORWARD);
    setFrontClawOpen();
    setBackClawClawOpen();
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
        && !Objects.equals(lastCmd, "slidedelay")
        && !Objects.equals(lastCmd, "dropping")) {
      if (!Objects.equals(lastCmd, "grabbing")) {
        timer.reset();
        lastCmd = "grabbing";
      } else if (timer.milliseconds() < 500) { // half a second for the cone to grab
        return false;
      }
    }

    closeTransferMec();
    setClawJointClose();
    retractVerticalSlides();

    if (!Objects.equals(lastCmd, "transferring")
        && !Objects.equals(lastCmd, "score")
        && !Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "dropping")) {
        timer.reset();
        lastCmd = "dropping";
      } else if (timer.milliseconds() < 500) { // half a second for the transfer mec to close
        return false;
      }
    }

    retractHorizontalSlides();

    if (!Objects.equals(lastCmd, "transferring") && !Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "score")) {
        timer.reset();
        lastCmd = "score";
      } else if (timer.milliseconds() < 1500) { // two seconds for the slides to retract
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
    setFrontClawOpen();
    setClawJointOpen();
    extendTransferMec();

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
    setClawJointClose();

    if (!Objects.equals(lastCmd, "transferring") && !Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "score")) {
        timer.reset();
        lastCmd = "score";
      } else if (timer.milliseconds() < 1000) { // two seconds for the slides to retract
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
    setClawJointOpen();
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
    retractVerticalSlides();
    closeTransferMec();
    setClawJointClose();
    setFrontClawOpen();
    retractHorizontalSlides();
  }

  public void extendHorizontalSlides() {
    horizontalLeftSlide.setPosition(horizontalSlideExtendPos);
    horizontalRightSlide.setPosition(horizontalSlideExtendPos);
  }

  public void retractHorizontalSlides() {
    closeTransferMec();
    setClawJointClose();
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

    backClawLiftLeft.setPosition(backClawLiftExtend);
  }

  public void retractVerticalSlides() {
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

  public void setClawJointClose() {
    clawJoint.setPosition(clawJointClose);
  }

  public void setClawJointOpen() {
    clawJoint.setPosition(clawJointExtend);
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
