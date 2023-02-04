package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes.Autonomous;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.T2_2022.Base;

/*
** make slides go down while moving.
1. program slides + 4 bar (testing program for time) (30 min)
2. auto pathing (splines (30 min)).
3. Just go to normal mtp if doestn work (30 min)

4 Cone:
deposit 4
cycle 5*4
waits 0.3*5 (grab --> raise 4 bar out) (drop 4bar in --> slides down)
park 2.5 s (possible reduction to 1.5) (500 strafe) 1s drive.
 */
@Autonomous(name = "T5_Auto", group = "OdomBot")
public class Blue_Secondary extends Base {

  double v4bRightOut = 0.8, v4bLeftOut = 0.2, v4bRIn = 0, v4bLIn = 1;

  @SuppressLint("SuspiciousIndentation")
  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this, telemetry);
    sleep(1000);
    grabber.grabCone();

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

    //    dt.NormalMTP(3, -51, 0, true, 3000);
    //    sleep(200);
    //    if (location == 2) {
    //      // dt.NormalMTP(3, -51, 0, true, 3000);
    //    } else if (location == 3) {
    //      dt.NormalMTP(-8, -51, 0, true, 3000);
    //    } else {
    //      dt.NormalMTP(17, -51, 0, true, 3000);
    //    }

    v4bRight.setPosition(v4bRIn);
    v4bLeft.setPosition(v4bLIn);
    grabber.restArm();
    dt.stopDrive();
  }
}
