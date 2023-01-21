/*package org.firstinspires.ftc.teamcode.T2_2022.Opmodes.Tests.OpModes.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.T2_2022.Base;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera.Camera;
import org.firstinspires.ftc.teamcode.Utils.Point;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.openftc.easyopencv.OpenCvCamera;

import java.util.ArrayList;
import java.util.Arrays;

@Autonomous(name = "Red_Primary", group = "OdomBot")
public class RedPrimary extends Base {

  @Override
  public void runOpMode() throws InterruptedException {
    ElapsedTime timer = new ElapsedTime();
    OpenCvCamera camera1 = null;
    initHardware(0, this);
    Servo v4bRight = hardwareMap.get(Servo.class, "v4bRight");
    Servo v4bLeft = hardwareMap.get(Servo.class, "v4bLeft");
    sleep(500);

    int location = 3;
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    sleep(500);
    while(!isStarted() && !isStopRequested()){
      camera.getLatestDetections();
      location = camera.getDetection();
      telemetry.addData("Status: ", "Initialized");
      telemetry.addData("Pos: ", location);
      telemetry.update();
    }





    boolean reseetAngleLast = false, resetAngleCurr = false;


    waitForStart();

    matchTime.reset();
    dt.resetCache();






    /*timer.reset();
    while (timer.milliseconds() <= 280) {
      dt.driveFieldCentric(0.25, 0, 0, 1);
    }
    dt.stopDrive();

    sleep(200);

    timer.reset();
    while (timer.milliseconds() <= 1700) {
      dt.driveFieldCentric(0, -90, 0.4, 1);
    }
    dt.stopDrive();

    sleep(500);

    grabber.raiseMiddleAuto();

    sleep(1300);

    timer.reset();
    while(timer.milliseconds() <= 560){
      dt.driveFieldCentric(0.1, -90, 0, 1);
    }
    dt.stopDrive();

    grabber.claw.setPosition(0.5);
    sleep(400);

    timer.reset();
    while(timer.milliseconds() <= 750){
      dt.driveFieldCentric(-0.1, -90, 0, 1);
    }
    dt.stopDrive();
    sleep(500);

    grabber.raiseStack();
    sleep(350);

    timer.reset();
    while(timer.milliseconds() <= 700){
      dt.driveFieldCentric(0, -90, 0.5, 1);
    }
    dt.stopDrive();

    sleep(500);

    timer.reset();
    while(timer.milliseconds() <= 430){
      dt.driveFieldCentric(0, -90, -0.3, 1);
    }
    dt.stopDrive();

    turnTo(90, 2000);
    dt.stopDrive();

    timer.reset();
    while(timer.milliseconds() <= 1050){
      dt.driveFieldCentric(-0.3, 90, 0, 1);
    }
    dt.stopDrive();
    turnTo(90, 1500);
    sleep(300);
    grabber.grabCone();
    sleep(1000);

    grabber.stackUp();
    sleep(500);

    timer.reset();
    while(timer.milliseconds() <= 400){
      dt.driveFieldCentric(0.4, 90, 0, 1);
    }
    dt.stopDrive();

    turnTo(180, 2500);
    sleep(300);

    timer.reset();
    while(timer.milliseconds() <= 330){
      dt.driveFieldCentric(0, 180, -0.2, 1);
    }
    dt.stopDrive();
    sleep(500);
    grabber.claw.setPosition(0.5);
    sleep(300);




    timer.reset();
    while(timer.milliseconds() <= 330){
      dt.driveFieldCentric(0, 180, 0.2, 1);
    }
    dt.stopDrive();

    turnTo(90, 2000);
    sleep(400);
    grabber.raiseStack2();

    timer.reset();
    while(timer.milliseconds() <= 750){
      dt.driveFieldCentric(-0.4, 90, 0, 1);
    }
    dt.stopDrive();
    turnTo(90, 1500);
    sleep(300);
    grabber.grabCone();
    sleep(1000);

    grabber.stackUp();
    sleep(500);


    timer.reset();
    while(timer.milliseconds() <= 550){
      dt.driveFieldCentric(0.4, 90, 0, 1);
    }
    dt.stopDrive();

    turnTo(180, 2500);
    sleep(300);

    timer.reset();
    while(timer.milliseconds() <= 300){
      dt.driveFieldCentric(0, 180, -0.2, 1);
    }
    dt.stopDrive();
    sleep(500);
    grabber.releaseCone();
    sleep(300);

    timer.reset();
    while(timer.milliseconds() <= 300){
      dt.driveFieldCentric(0, 180, 0.2, 1);
    }
    dt.stopDrive();

    sleep(500);
    turnTo(0, 2500);*/














    /*ArrayList<Point> path1 = new ArrayList<>();

    path1.addAll(new ArrayList<>(
            Arrays.asList(
                    new Point(10, -10), new Point(42, -10), new Point(42, 20)
            )
    ));
    sleep(500);

    LinearPathConstantHeading(path1, 0, 0.5, 0.3, 3, 3, 2, 8, 4000);
    sleep(500);
    turnTo(0, 4000);
    grabber.raiseMiddleAuto();

    path1.clear();
    path1.addAll(new ArrayList<>(
            Arrays.asList(
                    new Point(52,24)
            )
    ));
    LinearPathConstantHeading(path1, 0, 0.4, 0.4, 3, 3, 2, 8, 4000);
    sleep(500);
    grabber.releaseCone();
    sleep(700);
    grabber.restArm();
    dt.xEncPosv=0; dt.yEncPos=0;
    path1.clear();
    path1.addAll(new ArrayList<>(
            Arrays.asList(
                    new Point(-11,0),   new Point(-11,27), new Point(30,28), new Point(30, 72)
            )
    ));
    LinearPathConstantHeading(path1, 0, 0.4, 0.4, 3, 3, 2, 8, 4000);
    turnTo(90, 2000, 0.7);*/





    /*while(opModeIsActive()){
      dt.updatePosition();
      dt.resetCache();
      slowDrive = gamepad1.left_bumper;
      fastDrive = gamepad1.left_trigger > 0.05;
      drive = floor(gamepad1.right_stick_x * 0.8);
      strafe = floor(-gamepad1.right_stick_y * 0.8);
      turn = turnFloor(gamepad1.left_stick_x * 0.8);
      computeDrivePowers(gamepad1);

      telemetry.addData("POS: ", dt.getX() + " " + dt.getY());
      telemetry.addData("IMU Angle", dt.getAngle());
      telemetry.update();
    }*/

//    timer.reset();
//    while (timer.milliseconds() <= 605) {
//      dt.driveFieldCentric(0.15, 0, 0, 1);
//    }
//
//    timer.reset();
//    while (timer.milliseconds() <= 1200) {
//      dt.driveFieldCentric(0, 0, 0.3, 1);
//    }
//    dt.stopDrive();
//    sleep(300);
//    turnTo(0, 2000);
//    sleep(300);
//
//    timer.reset();
//    while (timer.milliseconds() <= 1025) {
//      dt.driveFieldCentric(0.2, 0, 0, 1);
//    }
//    dt.stopDrive();
//    sleep(900);
//
//    // Raise slide and drop
//    grabber.raiseMiddleAuto();
//    sleep(800);
//
//    timer.reset();
//    while (timer.milliseconds() <= 900) {
//      dt.driveFieldCentric(0, 0, 0.1, 1);
//    }
//    dt.stopDrive();
//
//    grabber.releaseCone();
//    sleep(500);
//
//
//    timer.reset();
//    while (timer.milliseconds() <= 800) {
//      dt.driveFieldCentric(0, 0, -0.1, 1);
//    }
//    dt.stopDrive();
//
//
//
//    grabber.raiseStack();
//    sleep(500);
//    timer.reset();
//    while (timer.milliseconds() <= 600) {
//      dt.driveFieldCentric(-0.4, 0, -0, 1);
//    }
//    dt.stopDrive();
//
//    sleep(500);
//
//    timer.reset();
//    while (timer.milliseconds() <= 1150) {
//      dt.driveFieldCentric(0, 0, 0.4, 1);
//    }
//    dt.stopDrive();
//    sleep(300);
//
//    timer.reset();
//    while(timer.milliseconds() <= 730){
//      dt.driveFieldCentric(0, 0, -0.2, 1);
//    }
//    dt.stopDrive();

//    sleep(200);
//
//    turnTo(90,2500);
//
//    sleep(200);
//    grabber.claw.setPosition(0.5);
//
//    timer.reset();
//    while(timer.milliseconds() <= 1370){
//      dt.driveFieldCentric(-0.3, 90, 0, 1);
//    }




//    dt.stopDrive();
//    sleep(200);
//    turnTo(90, 1000);
//    sleep(300);
//
//    grabber.grabCone();
//    sleep(800);
//    grabber.stackUp();
//    sleep(400);
//
//    timer.reset();
//    while(timer.milliseconds() <= 700){
//      dt.driveFieldCentric(0.3, 90, 0.08, 1);
//    }
//    dt.stopDrive();
//
//    sleep(300);
//    turnTo(180, 1300);
//
//    timer.reset();
//    while(timer.milliseconds() <= 640){
//      dt.driveFieldCentric(0, 180, -0.1, 1);
//    }
//    dt.stopDrive();
//
//    turnTo(180, 200);
//    grabber.claw.setPosition(0.5);
//    sleep(200);
//
//    timer.reset();
//    while(timer.milliseconds() <= 680){
//      dt.driveFieldCentric(0, 180, 0.1, 1);
//    }
//    dt.stopDrive();
//
//
//    sleep(500);
//    grabber.raiseStack2();
//
//
//    turnTo(90, 2500);
//    sleep(200);
//
//    timer.reset();
//    while(timer.milliseconds() <= 780){
//      dt.driveFieldCentric(-0.3, 90, -0.08, 1);
//    }
//    dt.stopDrive();
//
//
//    grabber.grabCone();
//    sleep(300);
//
//    v4bRight.setPosition(0.3);
//    v4bLeft.setPosition(0.7);
//    sleep(300);
//
//    timer.reset();
//    while(timer.milliseconds()<=1130){
//      dt.driveFieldCentric(0.5, 90, 0, 1);
//    }
//    dt.stopDrive();
//
//    sleep(500);
//    turnTo(180, 2000);
//    sleep(300);
//
//    grabber.raiseToPosition(grabber.HIGH, 1);
//    v4bRight.setPosition(0.85);
//    v4bLeft.setPosition(0.15);
//    sleep(1000);
//
//    grabber.releaseCone();
//    sleep(500);
//
//    grabber.raiseToPosition(grabber.LOW, 1);
//    v4bRight.setPosition(0.3);
//    v4bLeft.setPosition(0.7);
//    sleep(500);
//
//
//
//    grabber.grabCone();


    /*timer.reset();
    while(timer.milliseconds() <= 750){
      dt.driveFieldCentric(0.3, 90, 0.08, 1);
    }
    dt.stopDrive();

    sleep(300);
    turnTo(180, 2500);

    timer.reset();
    while(timer.milliseconds() <= 685){
      dt.driveFieldCentric(0, 180, -0.1, 1);
    }
    dt.stopDrive();
    sleep(300);
    turnTo(180, 300);
    grabber.claw.setPosition(0.5);
    sleep(350);

    timer.reset();
    while(timer.milliseconds() <= 680){
      dt.driveFieldCentric(0, 180, 0.1, 1);
    }
    dt.stopDrive();*/


//    if(location == 1){
//      timer.reset();
//      while(timer.milliseconds() <= 1100){
//        dt.driveFieldCentric(-0.6, 180, 0, 1);
//      }
//      dt.stopDrive();
//    }else if(location ==2){
//      timer.reset();
//      while(timer.milliseconds() <= 570){
//        dt.driveFieldCentric(-0.4, 180, -0.15, 1);
//      }
//    }else{
//      timer.reset();
//      while(timer.milliseconds() <= 570){
//        dt.driveFieldCentric(0.4, 180, -0.15, 1);
//      }
//    }
    /*timer.reset();
    while(timer.milliseconds() <= 1300){


      dt.driveFieldCentric(0.3, 90, 0, 1);
      /*if(timer.milliseconds()<200){
        grabber.raiseMiddle();
      }
      if(timer.milliseconds()>200) {
        grabber.raiseMiddle();
        dt.driveFieldCentric(0.4, 90, 0, 1, true);
      }*/
    //}
    //dt.stopDrive();

    //sleep(200);
    //turnTo(34, 2000);
    //sleep(200);



//
//
//    timer.reset();
//    while(timer.milliseconds() < 1000){
//      dt.resetCache();
//      grabber.raiseMiddle();
//      grabber.extendV4b();
//    }
//
//    sleep(500);
//
//    grabber.releaseCone();
//    sleep(250);
//    timer.reset();
//    grabber.grabCone();



//    sleep(2000);
//
//    timer.reset();
//    while(timer.milliseconds() < 500){
//      dt.resetCache();
//      grabber.restArm();
//      grabber.retractV4b();
//    }

//    timer.reset();
//    while(timer.milliseconds() < 500) {
//      dt.driveFieldCentric(-0.07, 90, 0.1, 1);
//    }
//    dt.stopDrive();
//    if(location==1){
//      timer.reset();
//      while (timer.milliseconds() <= 3000) {
//        dt.driveFieldCentric(-0.2, 0, 0, 1);
//      }
//    }else if(location==3){
//      timer.reset();
//      while (timer.milliseconds() <= 2500) {
//        dt.driveFieldCentric(0.15, 0, 0, 1);
//      }
//    }
//    dt.stopDrive();
//    sleep(200);
//    turnTo(0,5000);

//
//
//    /*sleep(500);
//    grabber.raiseStack();
//    grabber.releaseCone();
//
//
//    timer.reset();
//    while(timer.milliseconds() < 1280) {
//      dt.driveFieldCentric(-0.4, 90, 0, 1);
//    }
//    dt.stopDrive();
//    sleep(200);
//    grabber.grabCone();
//    sleep(800);
//    grabber.stackUp();
//    sleep(400);
//
//    timer.reset();
//    while(timer.milliseconds() <= 900){
//
//
//      dt.driveFieldCentric(0.5, 90, 0, 1);
//      /*if(timer.milliseconds()<200){
//        grabber.raiseMiddle();
//      }
//      if(timer.milliseconds()>200) {
//        grabber.raiseMiddle();
//        dt.driveFieldCentric(0.4, 90, 0, 1, true);
//      }*/
//    }
//    dt.stopDrive();
//
//    sleep(200);
//    turnTo(30, 2000);
//    sleep(200);
//
//
//
//
//
//    timer.reset();
//    while(timer.milliseconds() < 1000){
//      dt.resetCache();
//      grabber.raiseMiddle();
//      grabber.extendV4b();
//    }
//
//    sleep(500);
//
//    grabber.releaseCone();
//    sleep(250);
//    timer.reset();
//    grabber.grabCone();
//
//
//
//    sleep(500);
//
//    timer.reset();
//    while(timer.milliseconds() < 1000){
//      dt.resetCache();
//      grabber.restArm();
//      grabber.retractV4b();
//    }
//
//
//
//
//    // park
//    timer.reset();
//
//
////    if (location == 1) {
////      while (timer.milliseconds() <= 3000) {
////        dt.driveFieldCentric(-0.2, 0, 0, 1);
////      }
////    } else if (location == 2) {
////      while (timer.milliseconds() <= 1000) {
////        dt.driveFieldCentric(-0.2, 0, 0, 1);
////      }
////    } else if(location == 3){
////      while (timer.milliseconds() <= 1000) {
////        dt.driveFieldCentric(0.2, 0, 0, 1);
////      }
////    }
//    //dt.stopDrive();
//
  //}

