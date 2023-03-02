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
          dropBucket,
      clawJoint;
  final double horizontalSlideExtendPos = 0.04,
          horizontalSlideHighPosition = 0.18,
          horizontalSlideRetractPos = 0,
          verticalSlideExtendPosHigh = 1200,
          verticalSlideExtendPosMid = 900,
          verticalSlideRetractPos = -100,
          frontClawOpenFull = 0.2,
          frontClawOpen = 0.16,
          frontClawClose = 0,
          backClawOpen = 0.5,
          backClawClose = 0.8,
          clawJointExtend = 0.73,
          clawJointClose = 0.891, // 0.87
          transferMecExtend = 0.84,
          transferMecClose = 0.41,
          transferMecInit = 0.44,
          backClawLiftExtend = 0.35,
          backClawLiftRetract = 1;
  public ElapsedTime timer = new ElapsedTime();
  public String lastCmd = "null";

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
    this.dropBucket = backClawLiftLeft;
    this.clawJoint = clawJoint;

    this.verticalLeftSlide.setDirection(DcMotorSimple.Direction.REVERSE);
    this.verticalRightSlide.setDirection(DcMotorSimple.Direction.FORWARD);
    this.horizontalLeftSlide.setDirection(Servo.Direction.REVERSE);
    this.transferMecLeft.setDirection(Servo.Direction.REVERSE);
    setFrontClawClose();
    setBackClawClawOpen();
    this.dropBucket.setPosition(backClawLiftRetract);
    this.transferMecLeft.setPosition(transferMecClose);
    this.transferMecRight.setPosition(transferMecClose);
    this.horizontalLeftSlide.setPosition(horizontalSlideRetractPos);
    this.horizontalRightSlide.setPosition(horizontalSlideRetractPos);
  }

  public SlideSystem(
          Servo horizontalLeftSlide,
          Servo horizontalRightSlide,
          Servo frontClaw,
          Servo backClaw,
          Servo transferMecLeft,
          Servo transferMecRight,
          Servo backClawLiftLeft,
          Servo clawJoint) {
    this.horizontalLeftSlide = horizontalLeftSlide;
    this.horizontalRightSlide = horizontalRightSlide;
    this.frontClaw = frontClaw;
    this.backClaw = backClaw;
    this.transferMecLeft = transferMecLeft;
    this.transferMecRight = transferMecRight;
    this.dropBucket = backClawLiftLeft;
    this.clawJoint = clawJoint;

    this.horizontalLeftSlide.setDirection(Servo.Direction.REVERSE);
    this.transferMecLeft.setDirection(Servo.Direction.REVERSE);
    setFrontClawClose();
    setBackClawClawOpen();
    initTransferMec();
    this.dropBucket.setPosition(backClawLiftRetract);
    this.horizontalLeftSlide.setPosition(horizontalSlideRetractPos);
    this.horizontalRightSlide.setPosition(horizontalSlideRetractPos);
  }

  public void initialGrab() {
    setFrontClawOpenFull();
    extendHorizontalSlides();
    setClawJointOpen();
    extendTransferMec();
  }

  // everytime the claws are toggled (other than the first this is called) since it extends and
  // drops at the same time
  // keep running this method until the true parameter is returned.
  // todo: adjust the timers as necessary
  public boolean score() {
    if (!Objects.equals(lastCmd, "transferring")
        && !Objects.equals(lastCmd, "score")
        && !Objects.equals(lastCmd, "slidedelay")
        && !Objects.equals(lastCmd, "dropping")
            && !Objects.equals(lastCmd, "openClaw")) {
      if (!Objects.equals(lastCmd, "grabbing")) {
        timer.reset();
        lastCmd = "grabbing";
      }
      if (timer.milliseconds() < 500) { // half a second for the cone to grab
        return false;
      }
    }

//    closeTransferMec();
//    setClawJointClose();
//    retractVerticalSlides();
//    retractHorizontalSlides();
//
//    if (!Objects.equals(lastCmd, "transferring")
//        && !Objects.equals(lastCmd, "score")
//        && !Objects.equals(lastCmd, "slidedelay")) {
//      if (!Objects.equals(lastCmd, "dropping")) {
//        timer.reset();
//        lastCmd = "dropping";
//      }
//      if (timer.milliseconds() < 800) { // 1 second for the slides to retract
//        return false;
//      }
//    }
//

//
//    if (!Objects.equals(lastCmd, "transferring") && !Objects.equals(lastCmd, "slidedelay")) {
//      if (!Objects.equals(lastCmd, "score")) {
//        timer.reset();
//        lastCmd = "score";
//      }
//      if (timer.milliseconds() < 300) { // 500ms for the transfer mec to retract
//        return false;
//      }
//    }

//    setFrontClawOpen();
//    if (!Objects.equals(lastCmd, "slidedelay")) {
//      if (!Objects.equals(lastCmd, "transferring")) {
//        lastCmd = "transferring";
//        timer.reset();
//      }
//      if (timer.milliseconds()
//          < 500) { // half a second for the bucket to fully grab onto the cone and for the front
//        // claw to let go of the cone.
//        return false;
//      }
//    }
//
//    setFrontClawClose();
//    setClawJointOpen();
//    extendTransferMec();
//    setBackClawClose();
//    extendHorizontalSlides();
//
//    if (!Objects.equals(lastCmd, "slidedelay")) {
//      lastCmd = "slidedelay";
//      timer.reset();
//    }
//    if (timer.milliseconds()
//            < 600) { // half a second for the transfer mechanism to extend to avoid collision
//      return false;
//    }
//
//    extendVerticalSlides();
//    setFrontClawOpen();
//
//    lastCmd = "null";
//    return true;


     // code for avoiding the ground junction.

    if (!Objects.equals(lastCmd, "transferring")
        && !Objects.equals(lastCmd, "score")
        && !Objects.equals(lastCmd, "slidedelay")
            && !Objects.equals(lastCmd, "openClaw")) {
      if (!Objects.equals(lastCmd, "dropping")) {
        timer.reset();
        lastCmd = "dropping";
        setClawJointClose();
        closeTransferMec();
        retractVerticalSlides();
      }
      if (timer.milliseconds() < 500) { // half a second for the transfer mec to close
        return false;
      }
    }



    if (!Objects.equals(lastCmd, "transferring") && !Objects.equals(lastCmd, "slidedelay")   && !Objects.equals(lastCmd, "openClaw")) {
      if (!Objects.equals(lastCmd, "score")) {
        timer.reset();
        lastCmd = "score";
        retractHorizontalSlides();
      }
      if (timer.milliseconds() < 700) { // one seconds for the slides to retract
        return false;
      }
    }


    if (!Objects.equals(lastCmd, "slidedelay") && !Objects.equals(lastCmd, "openClaw")) {
      if (!Objects.equals(lastCmd, "transferring")) {
        lastCmd = "transferring";
        timer.reset();
        setFrontClawOpen();
      }
      if (timer.milliseconds()
          < 500) { // half a second for the bucket to fully grab onto the cone and for the front
        // claw to let go of the cone.
        return false;
      }
    }


    if (!Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "openClaw")) {
        lastCmd = "openClaw";
        timer.reset();
        setFrontClawClose();
      }
      if (timer.milliseconds()
              < 500) { // half a second for the bucket to fully grab onto the cone and for the front
        // claw to let go of the cone.
        return false;
      }
    }




    if (!Objects.equals(lastCmd, "slidedelay")) {
      lastCmd = "slidedelay";
      timer.reset();
      setBackClawClose();
      extendHorizontalSlides();
      setClawJointOpen();
      extendTransferMec();
    }
    if (timer.milliseconds()
            < 800) { // half a second for the transfer mechanism to extend to avoid collision
      return false;
    }

    extendVerticalSlides();
    setFrontClawOpenFull();

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
      }
      if (timer.milliseconds()
          < 500) { // half a second for the claws to grab or drop the cone
        return false;
      }
    }



    if (!Objects.equals(lastCmd, "transferring") && !Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "score")) {
        timer.reset();
        lastCmd = "score";
        closeTransferMec();
        setClawJointClose();
      }
      if (timer.milliseconds() < 1000) { // two seconds for the slides to retract
        return false;
      }
    }


    if (!Objects.equals(lastCmd, "slidedelay")) {
      if (!Objects.equals(lastCmd, "transferring")) {
        lastCmd = "transferring";
        timer.reset();
        setFrontClawOpen();
      }
      if (timer.milliseconds()
          < 200) { // half a second for the bucket to fully grab onto the cone and for the front
        // claw to let go of the cone.
        return false;
      }
    }

    if (!Objects.equals(lastCmd, "slidedelay")) {
      lastCmd = "slidedelay";
      timer.reset();
      setBackClawClose();
      extendTransferMec();
      setClawJointOpen();
    }
    if (timer.milliseconds()
        < 500) { // half a second for the transfer mechanism to extend to avoid collision
      return false;
    }

//    extendVerticalSlides();
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
    extendVerticalSlides();
    return true;
  }

  public boolean scoreCircuitsStage3() {
    if (!Objects.equals(lastCmd, "dropping")) {
      timer.reset();
      lastCmd = "dropping";
    } else if (timer.milliseconds() < 1000) { // half a second for the claws to grab or drop the cone
      return false;
    }

    lastCmd = "null";
    retractVerticalSlides();
    return true;
  }

  public void resetGrabber() {
    // when you are done cycling invoke this to completely reset the robot.
    setFrontClawOpen();
    retractVerticalSlides();
    closeTransferMec();
    setClawJointClose();
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
    verticalLeftSlide.setTarget(verticalSlideExtendPosHigh);
    verticalLeftSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalLeftSlide.toPosition();
    verticalLeftSlide.setPower(1);

    verticalRightSlide.setTarget(verticalSlideExtendPosHigh);
    verticalRightSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalRightSlide.toPosition();
    verticalRightSlide.setPower(1);

    dropBucket.setPosition(backClawLiftExtend);
  }

  public void retractVerticalSlides() {
    dropBucket.setPosition(backClawLiftRetract);

    verticalLeftSlide.setTarget(verticalSlideRetractPos);
    verticalLeftSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalLeftSlide.toPosition();
    verticalLeftSlide.setPower(0.7);

    verticalRightSlide.setTarget(verticalSlideRetractPos);
    verticalRightSlide.retMotorEx().setTargetPositionTolerance(1);
    verticalRightSlide.toPosition();
    verticalRightSlide.setPower(0.7);
  }

  public void setFrontClawClose() {
    frontClaw.setPosition(frontClawClose);
  }

  public void setFrontClawOpen() {
    frontClaw.setPosition(frontClawOpen);
  }

  public void setFrontClawOpenFull() {
    frontClaw.setPosition(frontClawOpenFull);
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

  public void initTransferMec() {
    transferMecLeft.setPosition(transferMecInit);
    transferMecRight.setPosition(transferMecInit);
  }

  public void closeTransferMecInter() {
    transferMecLeft.setPosition(0.65);
    transferMecRight.setPosition(0.65);
  }

  public void extendTransferMec() {
    transferMecLeft.setPosition(transferMecExtend);
    transferMecRight.setPosition(transferMecExtend);
  }
}
