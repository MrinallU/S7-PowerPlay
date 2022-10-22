package org.firstinspires.ftc.teamcode.Template.Modules.Camera.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Template.Modules.Camera.Camera;

@Disabled
@Autonomous(name = "Temp_RGB_ColorThresh", group = "Tests")
public class RGB_ColorThresh extends LinearOpMode {

  @Override
  public void runOpMode() throws InterruptedException {
    Camera camera = new Camera(hardwareMap);
    sleep(1000);
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();

    String color = camera.getSignalColor();

    while (opModeIsActive()) {
      // Display Values
      telemetry.addData("Signal Color:", color);
      telemetry.update();
    }
  }
}
