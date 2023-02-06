package org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera.Camera;

@Autonomous(name = "RGB_ColorThresh", group = "Tests")
public class RGB_ColorThresh extends LinearOpMode {

  @Override
  public void runOpMode() throws InterruptedException {
    Camera camera = new Camera(hardwareMap);
    sleep(1000);
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();

    int color = camera.getSignalColor();

    while (opModeIsActive()) {
      // Display Values
      telemetry.addData("Signal Color:", color);
      telemetry.update();
    }
  }
}
