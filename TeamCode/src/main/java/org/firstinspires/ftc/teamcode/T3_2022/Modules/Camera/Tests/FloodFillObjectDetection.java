package org.firstinspires.ftc.teamcode.T3_2022.Modules.Camera.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.ArrayList;
import java.util.Collections;
import org.firstinspires.ftc.teamcode.T3_2022.Modules.Camera.Camera;
import org.firstinspires.ftc.teamcode.T3_2022.Modules.Camera.Pipelines.Helpers.VisionObject;

@Disabled
@TeleOp(name = "Ecov_yeet", group = "robot")
public class FloodFillObjectDetection extends LinearOpMode {
  @Override
  public void runOpMode() throws InterruptedException {
    Camera camera = new Camera(hardwareMap);
    camera.switchToFFPipleine();
    telemetry.addLine("Status: Initialized");
    telemetry.update();
    waitForStart();

    while (opModeIsActive()) {
      ArrayList<VisionObject> objects = camera.getObjects();
      try {
        if (objects.size() > 0) {
          Collections.sort(objects);
          VisionObject o = objects.get(objects.size() - 1);
          if (o != null) {
            telemetry.addLine("Pixel coords (x,y) " + o.centerX + " " + o.centerY);
            telemetry.addLine("Inch coords (x,y) " + o.inchX + " " + o.inchY);
          }
        }
      } catch (Exception e) {
        telemetry.addLine("No Object Found");
      }
      telemetry.update();
    }
  }
}
