package org.firstinspires.ftc.teamcode.TestingRobot.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.TestingRobot.Base;
import org.firstinspires.ftc.teamcode.Utils.Point;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name="Test_Autonomous", group = "OdomBot")
public class Test_Autonomous extends Base {

  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this);
    sleep(1000);
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
    matchTime.reset();
    dt.updateencodo();
    dt.resetCache();

    ArrayList<Point> path1 = new ArrayList<>();
//        path1.add(new Point(9, 16));  path1.add(new Point(22, 22));
    path1.addAll(new ArrayList<>(
            Arrays.asList(
                    new Point(-29,0), new Point(-29, 23), new Point(-25, 38), new Point(-22, 49.5), new Point(25, 49.5),
                    new Point(-22, 49.5), new Point(25, 49.5),
                    new Point(-22, 49.5),  new Point(25, 49.5),
                    new Point(-22, 49.5),
                    new Point(25, 49.5),
                    new Point(-22, 49.5),
                    new Point(25, 49.5),
                    new Point(-22, 49.5)
                    //,new Point(-26, 26)//new Point(38, -53), new Point(-26, 39), new Point(-20, 55)
            )
    ));

    sleep(500);
    LinearPathConstantHeading(path1, 0, 1, 1, 1, 1, 1, 8, 100000);
    //moveToPosition(10,  70,  30, 10000);
//        moveToPosition(0,  0,  30, 10000);
//        moveToPosition(10,  70,  30, 10000);
//        moveToPosition(0,  0,  30, 10000);

    //moveToPosition(60,  0,  0, 10000);
//        moveToPosition(0,  0,  0, 10000);
//        moveToPosition(60,  0,  0, 10000);
//        moveToPosition(0,  0,  0, 10000);

    while (opModeIsActive()) {
      // Updates
      dt.resetCache();
      dt.updateencodo();

      // Reset Angle
      currAngle = dt.getAngle();
      if (gamepad1.x) {
        targetAngle = -currAngle - 180;
      }

      // Change Drive Mode
      yLP = yP;
      yP = gamepad1.y;
      if (!yLP && yP) {
        basicDrive = !basicDrive;
      }

      // Drive
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = floor(-gamepad1.right_stick_x) * multiplier;
      strafe = floor(gamepad1.right_stick_y) * multiplier;
      turn = turnFloor(gamepad1.left_stick_x) * multiplier;
      computeDrivePowers(gamepad1);


      // Display Values
      telemetry.addData("Drive Type", driveType);
      telemetry.addData("Odometry Info", dt.getCurrentPosition());
      telemetry.update();
    }
  }
}
