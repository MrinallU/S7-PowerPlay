package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.firstinspires.ftc.teamcode.T2_2022.Base;
import org.firstinspires.ftc.teamcode.Utils.Point;

@Autonomous(name = "T5_Auto", group = "OdomBot")
public class Blue_Secondary extends Base {

  double  v4bRightOut = 0.84, v4bLeftOut = 0.16;

  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this);
    sleep(1000);

    ArrayList<Point> path1 = new ArrayList<>();
    ArrayList<Point> path2 = new ArrayList<>();

    //        path1.add(new Point(9, 16));  path1.add(new Point(22, 22));
    path1.addAll(
            new ArrayList<>(
                    Arrays.asList(
                            new Point(28.5, -10),
                            new Point(25.5, -6)
                            // ,new Point(-26, 26)//new Point(38, -53), new Point(-26, 39), new Point(-20, 55)
                    )));
    path2.addAll(
            new ArrayList<>(
                    Arrays.asList(
                            new Point(30, -8),
                            new Point(36, 10 )
//                            new Point(23, -8)
                            // ,new Point(-26, 26)//new Point(38, -53), new Point(-26, 39), new Point(-20, 55)
                    )));

    telemetry.addData("Status", "Initialized");
    telemetry.update();
    int location = 3;
    while(!isStarted() && !isStopRequested()){
      camera.getLatestDetections();
      location = camera.getDetection();
      telemetry.addData("Status: ", "Initialized");
      telemetry.addData("Pos: ", location);
      telemetry.update();
    }

    waitForStart();
    matchTime.reset();





    //26.25, -13.5
    moveToPosition(5,  -4,  0, 5000);
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
    grabber.raiseStack();
    moveToPosition(31.5, -8, 90, 3000);
    sleep(300);
    grabber.resetClaw();
    t.reset();
    while(t.milliseconds()<=2000&&Math.abs(10.5-dt.getY())>2 || Math.abs(31.5 - dt.getX())>2 || Math.abs(90 - dt.getAngle()) > 2){
      dt.updateencodo();
      dt.resetCache();
      double error = 10.5 - dt.getY();
      double Xerror = 31.5 - dt.getX();
      double angError = 90 - dt.getAngle();

      double p = -0.05 * error;
      double py = 0.05* Xerror;
      double apAng = -0.006 * angError;


      dt.driveFieldCentric(p, apAng, py);
    }
    dt.stopDrive();
    grabber.grabCone();
    sleep(250);
    v4bRight.setPosition(v4bRightOut);
    v4bLeft.setPosition(v4bLeftOut);
    grabber.raiseMiddle();
    t.reset();
    while(t.milliseconds()<=2000&&Math.abs(-6-dt.getY())>2){ //First Cycle
      dt.updateencodo();
      dt.resetCache();
      double error = -6 - dt.getY();
      double p = -0.05 * error;


      dt.driveFieldCentric(p, 0, 0);
    }
    dt.stopDrive();    sleep(100);
    moveToPosition(20.5, -8, 90, 5000);
    sleep(300);
    grabber.releaseCone();
    sleep(300);
    v4bRight.setPosition(0);
    v4bLeft.setPosition(1);
    sleep(300);
    grabber.raiseStack2();
    moveToPosition(30, -6, 90, 3000);
    sleep(200);
    grabber.resetClaw();
    sleep(200);
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
      while(t.milliseconds() <= 300){
        dt.resetCache();
        dt.driveFieldCentric(-0.3, 0, 0);
      }
    }else{
      grabber.restArm();
      t.reset();
      while(t.milliseconds()<=600){
        dt.resetCache();
        dt.driveFieldCentric(0.5, 0, -0.15);
      }
      dt.stopDrive();

    }
    /*t.reset();
    while(t.milliseconds()<=2000&&Math.abs(10.5-dt.getY())>2 || Math.abs(30 - dt.getX())>2 || Math.abs(90 - dt.getAngle()) > 2){
      dt.updateencodo();
      dt.resetCache();
      double error = 10.5 - dt.getY();
      double Xerror = 30 - dt.getX();
      double angError = 90 - dt.getAngle();

      double p = -0.05 * error;
      double py = 0.05* Xerror;
      double apAng = -0.006 * angError;


      dt.driveFieldCentric(p, apAng, py);
    }
    dt.stopDrive();
    dt.stopDrive();

    grabber.grabCone();
    sleep(250);
    v4bRight.setPosition(v4bRightOut);
    v4bLeft.setPosition(v4bLeftOut);
    grabber.raiseMiddle();
    t.reset();
    while((t.milliseconds()<=2000)&&(Math.abs(-6-dt.getY()) > 2 || Math.abs(30 - dt.getX()) > 2 || Math.abs(90 - dt.getAngle()) > 2)){
      dt.updateencodo();
      dt.resetCache();
      double error = -6 - dt.getY();
      double xError = 30 - dt.getX();
      double angError = 90 - dt.getAngle();
      double p = -0.05 * error;


      dt.driveFieldCentric(p, 0.05*xError, -0.006*angError);
    }
    dt.stopDrive();    sleep(100);
    moveToPosition(21, -8, 90, 5000);
    sleep(300);
    grabber.releaseCone();
    sleep(300);
    v4bRight.setPosition(0);
    v4bLeft.setPosition(1);
    sleep(300);
    grabber.raiseToPosition(240, 1);
    moveToPosition(30, -6, 90, 3000);
    sleep(200);
    grabber.resetClaw();
    t.reset();
    while(t.milliseconds()<=2000&&Math.abs(11-dt.getY())>2 || Math.abs(30 - dt.getX())>2 || Math.abs(90 - dt.getAngle()) > 2){
      dt.updateencodo();
      dt.resetCache();
      double error = 11 - dt.getY();
      double Xerror = 30 - dt.getX();
      double angError = 90 - dt.getAngle();

      double p = -0.05 * error;
      double py = 0.05* Xerror;
      double apAng = -0.006 * angError;


      dt.driveFieldCentric(p, apAng, py);
    }
    dt.stopDrive();

    grabber.grabCone();
    sleep(250);
    v4bRight.setPosition(v4bRightOut);
    v4bLeft.setPosition(v4bLeftOut);
    grabber.raiseMiddle();
    t.reset();
    while((t.milliseconds()<=2000)&&(Math.abs(-6-dt.getY()) > 2 || Math.abs(30 - dt.getX()) > 2 || Math.abs(90 - dt.getAngle()) > 2)) {
      dt.updateencodo();
      dt.resetCache();
      double error = -6 - dt.getY();
      double xError = 30 - dt.getX();
      double angError = 90 - dt.getAngle();

      double p = -0.05 * error;


      dt.driveFieldCentric(p, 0.05 * xError, -0.006 * angError);
    }
    dt.stopDrive();
    sleep(100);
    moveToPosition(21, -8, 90, 5000);
    sleep(300);
    grabber.releaseCone();
    sleep(300);
    v4bRight.setPosition(0);
    v4bLeft.setPosition(1);
    sleep(300);
    grabber.raiseStack2();
    moveToPosition(32, -6, 90, 3000);
    sleep(200);
    grabber.grabCone();
    if(location == 1){
      grabber.restArm();
      t.reset();
      while(t.milliseconds()<=750){
        dt.resetCache();
        dt.driveFieldCentric(-0.5, 0, 0);
      }
      dt.stopDrive();
    }else if(location == 2){
      grabber.restArm();
    }else{
      grabber.restArm();
      t.reset();
      while(t.milliseconds()<=600){
        dt.resetCache();
        dt.driveFieldCentric(0.5, 0, 0);
      }
      dt.stopDrive();

    }
    /*t.reset();
    while(t.milliseconds()<=2000&&Math.abs(10.5-dt.getY())>2){
      dt.updateencodo();
      dt.resetCache();
      double error = 10.5 - dt.getY();
      double p = -0.05 * error;


      dt.driveFieldCentric(p, 0, 0);
    }
    dt.stopDrive();*/



    /*moveToPosition(33, -4, 110, 5000);
    sleep(200);
    moveToPosition(39, -5.5, 110, 5000);
    sleep(300);*/



    /*moveToPosition(-33,  5,  0, 5000);
    sleep(300);
    moveToPosition(-25,  14,  0, 5000);
    sleep(300);*/


    //        moveToPosition(0,  0,  0, 10000);
    //        moveToPosition(60,  0,  0, 10000);
    //        moveToPosition(0,  0,  0, 10000);
//    ElapsedTime timer = new ElapsedTime();
//    dt.driveFieldCentric(0.05,0, 0);
//    while (timer.milliseconds()<2000){
//      dt.resetCache();
//      dt.updateencodo();
//
//      telemetry.addData("Odometry Info", dt.getCurrentPosition());
//      telemetry.update();
//    }
//
//    dt.stopDrive();




    //LinearPathConstantHeading(path1, 0, 1, 1, 1, 1, 1, 8, 100000);
//    moveToPosition(10,  0,  0, 10000);
    //        moveToPosition(0,  0,  30, 10000);
    //        moveToPosition(10,  70,  30, 10000);
    //        moveToPosition(0,  0,  30, 10000);

//   moveToPosition(-28,  12,  0, 10000);
//   sleep(500);
//   moveToPosition(0,  0,  0, 10000);
//   sleep(500);
//   moveToPosition(-28,  12,  0, 10000);
//   sleep(500);
//   moveToPosition(0,  0,  0, 10000);

//    while (opModeIsActive()) {
//      // Updates
//      dt.resetCache();
//      dt.updateencodo();
//
//      // Reset Angle
//      currAngle = dt.getAngle();
//      if (gamepad1.x) {
//        targetAngle = -currAngle - 180;
//      }

      // Change Drive Mode
//      yLP = yP;
//      yP = gamepad1.y;
//      if (!yLP && yP) {
//        basicDrive = !basicDrive;
//      }
//
//      // Drive
//      slowDrive = gamepad1.left_bumper;
//      fastDrive = gamepad1.left_trigger > 0.05;
//      drive = floor(-gamepad1.right_stick_x) * multiplier;
//      strafe = floor(gamepad1.right_stick_y) * multiplier;
//      turn = turnFloor(gamepad1.left_stick_x) * multiplier;
//      computeDrivePowers(gamepad1);
//
//      // Display Values
//      telemetry.addData("Drive Type", driveType);
//      telemetry.addData("Odometry Info", dt.getCurrentPosition());
//      telemetry.update();
    dt.stopDrive();
    }
  }
