package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.T2_2022.Base;

@Autonomous(name = "Blue_Primary", group = "OdomBot")
public class BluePrimary extends Base {
  @Override
  public void runOpMode() throws InterruptedException {
    ElapsedTime timer = new ElapsedTime();
    initHardware(0, this, telemetry);
    sleep(500);
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    int location = 3;
    while (!isStarted() && !isStopRequested()) {
      camera.getLatestDetections();
      location = camera.getDetection();
      telemetry.addData("Status: ", "Initialized");
      telemetry.addData("Pos: ", location);
      telemetry.update();
    }

    waitForStart();
    matchTime.reset();

    if (location == 1) {
      timer.reset();
    }

    /*moveToPosition(5,  -4,  0, 5000);
          sleep(300);
          moveToPosition(38,  -7,  0, 5000);
          sleep(300);
          grabber.raiseToPosition(1500, 1);
          moveToPosition(24.5, -8, -90, 5000);
          sleep(300);
          int targetY = 0;
          ElapsedTime t = new ElapsedTime();
          while(t.milliseconds()<=2000&&Math.abs(-11.5-dt.getY())>2){
              dt.updateencodo();
              dt.resetCache();
              double error = -11.5 - dt.getY();
              double p = -0.05 * error;
              dt.driveFieldCentric(p , 0, 0);
          }
          dt.stopDrive();
          grabber.releaseCone();
          sleep(300);
          t.reset();
          while(t.milliseconds()<=2000&&Math.abs(-6-dt.getY())>2){
              dt.updateencodo();
              dt.resetCache();
              double error = -6 - dt.getY();
              double p = -0.05 * error;
              dt.driveFieldCentric(p , 0, 0);
          }
          dt.stopDrive();
          sleep(300);
          grabber.restArm();
          moveToPosition(30, -6, 90, 3000);
          sleep(200);
          grabber.resetClaw();

          if(location == 1){
              grabber.restArm();
              t.reset();
              while(t.milliseconds()<=800){
                  dt.resetCache();
                  dt.driveFieldCentric(-0.5, 0, 0);
              }
              dt.stopDrive();
          }else if(location == 2){
              grabber.restArm();
              t.reset();
              while(t.milliseconds() <= 600){
                  dt.resetCache();
                  dt.driveFieldCentric(-0.3, 0, 0);
              }
          }else {
              grabber.restArm();
              t.reset();
              while (t.milliseconds() <= 600) {
                  dt.resetCache();
                  dt.driveFieldCentric(0.5, 0, 0);
              }
              dt.stopDrive();
          }

      }
    }*/
  }
}
