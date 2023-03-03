package org.firstinspires.ftc.teamcode.NewRobot.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.Arrays;
import org.firstinspires.ftc.teamcode.NewRobot.Base;
import org.firstinspires.ftc.teamcode.Utils.Point;


@Autonomous(name = "State_Auto_Blue_Secondary", group = "Tests")
public class Test_Autonomous extends Base {

  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this, telemetry);
    sleep(1000);
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
    matchTime.reset();
    dt.update();

    ArrayList<Point> path1 = new ArrayList<>();
    path1.add(new Point(0, -6, 0));
    path1.add(new Point(50, -3, 0));
    path1.add(new Point(60, -16, 150));

    ArrayList<Point> path2 = new ArrayList<>();
    path2.add(new Point(55, -8, 90));

    ArrayList<Point> path3 = new ArrayList<>();
    path3.add(new Point(60, -16, 150));
    slideSystem.setBackClawClose();
    // 53, -16, 90
    // -38.5, 3.5, 180
//    path1.add(new Point(22, 22));
//    path1.addAll(
//        new ArrayList<>(
//            Arrays.asList(
//                new Point(41, 19),
//                new Point(53, 8.7),
//                new Point(60, -51),
//                new Point(38, -53),
//                new Point(15, -45.1),
//                new Point(0, 0))));
    waitForStart();

    slideSystem.extendTransferAutoInit();
    dt.raise1 = true;
    PlainPathVaryingHeading(path1,  1, 1, 5, 5000);
    dt.raise1 = false;

    // Cycle 1
    slideSystem.setBackClawClawOpen();
    sleep(500);
    slideSystem.retractVerticalSlides();
    slideSystem.setFrontClawOpenFull();
    PlainPathVaryingHeading(path2,  1, 1, 5, 3000);
    slideSystem.extendHorizontalSlidesAuto();
    sleep(1000);
    slideSystem.setFrontClawClose();
    sleep(300);
    slideSystem.setClawJointClose();
    slideSystem.closeTransferMec();
    sleep(500);
    slideSystem.retractHorizontalSlides();
    sleep(1000);
    slideSystem.setFrontClawOpen();
    sleep(300);
    slideSystem.setBackClawClose();
    slideSystem.extendTransferAutoInit();
    sleep(500);
    slideSystem.extendVerticalSlides();


    PlainPathVaryingHeading(path3,  1, 1, 5, 3000);
    slideSystem.setBackClawClawOpen();






    //PlainPathVaryingHeading(path3,
    // 1, 1, 5, 3000);

//
//    while (opModeIsActive()) {
//      // Updates
//      dt.update();
//
//      // Reset Angle
//      currAngle = dt.getAngleImu();
//      if (gamepad1.x) {
//        targetAngle = -currAngle - 180;
//      }
//
//      // Change Drive Mode
//      yLP = yP;
//      yP = gamepad1.y;
//      if (!yLP && yP) {
//        basicDrive = !basicDrive;
//      }
//
//      // Drive
//      slowDrive = gamepad1.left_bumper;
//      fastDrive = gamepad1.left_trigger > 0.05;
//      drive = floor(-gamepad1.right_stick_x);
//      strafe = floor(gamepad1.right_stick_y);
//      turn = turnFloor(gamepad1.left_stick_x);
//      computeDrivePowers(gamepad1);
//
//      // Display Values
//      telemetry.addData("Drive Type", driveType);
//      telemetry.addData("Odometry Info", dt.getCurrentPosition());
//      telemetry.update();
//    }
  }
}
