package org.firstinspires.ftc.teamcode.Template.Modules.Camera.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Template.Modules.Camera.Camera;
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

@TeleOp(name = "ApilTagTest", group = "robot")
public class AprilTagDetectionExample extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Camera camera = new Camera(hardwareMap);
    camera.switchToAprilTagDetection();
    telemetry.addLine("Status: Initialized");
    telemetry.update();
    waitForStart();

    while (opModeIsActive()) {
      ArrayList<AprilTagDetection> currentDetections = camera.getLatestDetections();
      if (currentDetections.size() != 0) {
        boolean tagFound = false;

        for (AprilTagDetection tag : currentDetections) {
          telemetry.addLine(String.valueOf(tag.id));
        }
        telemetry.update();
      }
    }
  }
}
