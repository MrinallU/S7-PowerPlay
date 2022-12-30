package org.firstinspires.ftc.teamcode.NewRobot.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.NewRobot.Base;

@Disabled
@TeleOp(name = "New_Robot_TeleOP", group = "OdomBot")
public class Test_TeleOp extends Base {

  boolean clawToggle = false, first=true, doneScoring =true;
  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this);
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
    matchTime.reset();
    dt.updateOdometry();
    while (opModeIsActive()) {
      // Updates
      dt.resetCache();
      dt.updateOdometry();

      currAngle = dt.getAngle();

      // Change Drive Mode
      yLP = yP;
      yP = gamepad1.y;
      if (!yLP && yP) {
        basicDrive = !basicDrive;
      }

      // Drive
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = floor(gamepad1.right_stick_x);
      strafe = floor(-gamepad1.right_stick_y);
      turn = turnFloor(gamepad1.left_stick_x);
      computeDrivePowers(gamepad1);


      // the front claw automatically opens when extending...
      aLP = aP;
      aP = gamepad1.a;
      if(aP&&!aLP){
        if(first){
          first = false;
          grabber.initialGrab();
        }else {
          if (!clawToggle) {
            grabber.setFrontClawClose();
          }else{
            grabber.setBackClawClawOpen();
            grabber.setFrontClawClose();
          }
          doneScoring = false;
          clawToggle = !clawToggle;
        }
      }

      if(!doneScoring){
        doneScoring = grabber.score();
      }
      // Display Values
      telemetry.addData("Drive Type", driveType);
      telemetry.addData("Odometry Info", dt.getCurrentPosition());
      telemetry.update();
    }
  }
}
