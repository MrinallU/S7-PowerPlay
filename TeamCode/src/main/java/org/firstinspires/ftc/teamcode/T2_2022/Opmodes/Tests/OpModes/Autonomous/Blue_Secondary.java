package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes.Autonomous;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.util.ArrayList;
import org.firstinspires.ftc.teamcode.T2_2022.Base;
import org.firstinspires.ftc.teamcode.Utils.Point;

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
  ArrayList<Point> p1 = new ArrayList<>();

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

    grabber.raiseMiddle();
    v4bRight.setPosition(v4bRightOut);
    v4bLeft.setPosition(v4bLeftOut);

    p1.add(new Point(0, 4));
    dt.ChaseTheCarrot(p1, 100, 0, 0.5, 1, 0.04, 0.040, 0.01, 1, 0.01, 0, 500);
    p1.clear();
    p1.add(new Point(-40, 3));
    dt.ChaseTheCarrot(p1, 100, 0, 0.5, 1, 0.04, 0.040, 0.01, 1, 0.01, 0, 500);
    // sleep(100);

    grabber.releaseCone();
    sleep(200);
    v4bLeft.setPosition(v4bLIn);
    v4bRight.setPosition(v4bRIn);
    grabber.raiseStack();
    sleep(400);
    // sleep(300);

    // cycle 1
    p1.clear();
    p1.add(new Point(-60, 3));
    // -60
    p1.add(new Point(-48, 32));
    dt.ChaseTheCarrot(p1, 18.7, 0, 0.5, 1, 0.04, 0.030, 0.02, 0.7, 0.01, 0, 2200);
    grabber.grabCone();
    sleep(300);
    grabber.raiseTop();
    sleep(100);

    p1.clear();
    p1.add(new Point(-48, 3));
    dt.ChaseTheCarrot(p1, 3, 0, 0.5, 1, 0.04, 0.030, 0.02, 0.5, 0.01, 0, 2200);
    dt.stopDrive();

    //
    //    p1.clear();
    //    p1.add(new Point(1, -49));
    //    p1.add(new Point(4, -40));
    //    PlainPathConstantHeading(p1, 0, 1, 1, 9, 5000);
    //    grabber.releaseCone();
    //    sleep(200);
    //    v4bLeft.setPosition(v4bLIn);
    //    v4bRight.setPosition(v4bRIn);
    //    grabber.raiseStack2();
    //    sleep(400);
    //
    //
    //    // cycle 2
    //    p1.clear();
    //    p1.add(new Point(3, -61));
    //    //-60
    //    p1.add(new Point(33, -48));
    //    dt.ChaseTheCarrot(p1, 18.7, 0, 0.5, 1, 0.04, 0.030, 0.02, 0.7,0.01, 0, 2200);
    //    grabber.grabCone();
    //    grabber.raiseMiddle();
    //    v4bRight.setPosition(v4bRightOut);
    //    v4bLeft.setPosition(v4bLeftOut);
    //
    //    p1.clear();
    //    p1.add(new Point(1, -49));
    //    p1.add(new Point(4, -40));
    //    PlainPathConstantHeading(p1, 0, 1, 1, 9, 5000);
    //    grabber.releaseCone();
    //    sleep(200);
    //    v4bLeft.setPosition(v4bLIn);
    //    v4bRight.setPosition(v4bRIn);
    //    grabber.raiseStack3();
    //    sleep(400);
    //
    //    // cycle 3
    //    p1.clear();
    //    p1.add(new Point(3, -61));
    //    //-60
    //    p1.add(new Point(33, -48));
    //    dt.ChaseTheCarrot(p1, 18.7, 0, 0.5, 1, 0.04, 0.030, 0.02, 0.7,0.01, 0, 2200);
    //    grabber.grabCone();
    //    grabber.raiseMiddle();
    //    v4bRight.setPosition(v4bRightOut);
    //    v4bLeft.setPosition(v4bLeftOut);
    //
    //    p1.clear();
    //    p1.add(new Point(1, -49));
    //    p1.add(new Point(4, -40));
    //    PlainPathConstantHeading(p1, 0, 1, 1, 9, 5000);
    //    grabber.releaseCone();
    //    sleep(200);
    //    v4bLeft.setPosition(v4bLIn);
    //    v4bRight.setPosition(v4bRIn);
    //    grabber.raiseStack4();
    //    sleep(400);
    //
    //    // cycle 4
    //    p1.clear();
    //    p1.add(new Point(3, -61));
    //    //-60
    //    p1.add(new Point(33, -48));
    //    dt.ChaseTheCarrot(p1, 18.7, 0, 0.5, 1, 0.04, 0.030, 0.02, 0.7,0.01, 0, 2200);
    //    grabber.grabCone();
    //    grabber.raiseMiddle();
    //    v4bRight.setPosition(v4bRightOut);
    //    v4bLeft.setPosition(v4bLeftOut);
    //
    //    p1.clear();
    //    p1.add(new Point(1, -49));
    //    p1.add(new Point(4, -40));
    //    PlainPathConstantHeading(p1, 0, 1, 1, 9, 5000);
    //    grabber.releaseCone();
    //    sleep(200);
    //    v4bLeft.setPosition(v4bLIn);
    //    v4bRight.setPosition(v4bRIn);
    //    grabber.restArm();
    //    sleep(400);
    //    // cycle 5
    //    p1.clear();
    //    p1.add(new Point(3, -61));
    //    //-60
    //    p1.add(new Point(33, -48));
    //    dt.ChaseTheCarrot(p1, 18.7, 0, 0.5, 1, 0.04, 0.030, 0.02, 0.7,0.01, 0, 2200);
    //    grabber.grabCone();
    //    grabber.raiseMiddle();
    //    v4bRight.setPosition(v4bRightOut);
    //    v4bLeft.setPosition(v4bLeftOut);
    //
    //    p1.clear();
    //    p1.add(new Point(1, -49));
    //    p1.add(new Point(4, -40));
    //    PlainPathConstantHeading(p1, 0, 1, 1, 9, 5000);
    //    grabber.releaseCone();
    //    sleep(200);
    //    v4bLeft.setPosition(v4bLIn);
    //    v4bRight.setPosition(v4bRIn);
    //    grabber.restArm();
    //    sleep(400);
    //
    //    //  Park
    //    p1.clear();
    //    p1.add(new Point(3, -61));
    //    //-60
    //    p1.add(new Point(33, -48));
    //    dt.ChaseTheCarrot(p1, 18.7, 0, 0.5, 1, 0.04, 0.030, 0.02, 0.7,0.01, 0, 2200);
    //    //PlainPathConstantHeading(p1, 0, 0.5, 1, 19, 5000);
    //
    //    //    dt.NormalMTP(3, -51, 0, true, 3000);
    //    //    sleep(200);
    //    //    if (location == 2) {
    //    //      // dt.NormalMTP(3, -51, 0, true, 3000);
    //    //    } else if (location == 3) {
    //    //      dt.NormalMTP(-8, -51, 0, true, 3000);
    //    //    } else {
    //    //      dt.NormalMTP(17, -51, 0, true, 3000);
    //    //    }
    //
    //    //    v4bRight.setPosition(v4bRIn);
    //    //    v4bLeft.setPosition(v4bLIn);
    //    //    grabber.restArm();
    //    dt.stopDrive();
    sleep(300000);
  }
}
