package org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera;

import com.qualcomm.robotcore.hardware.HardwareMap;
import java.util.ArrayList;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera.Pipelines.Helpers.VisionObject;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera.Pipelines.april_tag_detection_pipeline;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera.Pipelines.auto_floodfill_detection;
import org.firstinspires.ftc.teamcode.T2_2022.Modules.Camera.Pipelines.rectangle_thresholder_pipeline;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class Camera {
  private OpenCvWebcam webcam;
  public HardwareMap hardwareMap;
  private rectangle_thresholder_pipeline p1;
  private auto_floodfill_detection p2;
  double fx = 578.272;
  double fy = 578.272;
  double cx = 402.145;
  double cy = 221.506;
  private april_tag_detection_pipeline p3;

  public Camera(HardwareMap hw) {

    p1 = new rectangle_thresholder_pipeline();
    this.hardwareMap = hw;
    int cameraMonitorViewId =
        hardwareMap
            .appContext
            .getResources()
            .getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

    webcam =
        OpenCvCameraFactory.getInstance()
            .createWebcam(hardwareMap.get(WebcamName.class, "camera"), cameraMonitorViewId);
    webcam.setPipeline(p1);
    webcam.setMillisecondsPermissionTimeout(2500);
    webcam.openCameraDeviceAsync(
        new OpenCvCamera.AsyncCameraOpenListener() {
          @Override
          public void onOpened() {
            webcam.startStreaming(800, 448, OpenCvCameraRotation.SIDEWAYS_LEFT);
          }

          @Override
          public void onError(int errorCode) {}
        });

    p2 = new auto_floodfill_detection(true, webcam);
    p3 = new april_tag_detection_pipeline(0.166, fx, fy, cx, cy);
  }

  public int getSignalColor() {
    return p1.getOut();
  }

  public void switchToFFPipleine() {
    webcam.setPipeline(p2);
  }

  public void switchToAprilTagDetection() {
    webcam.setPipeline(p3);
  }

  public ArrayList<VisionObject> getObjects() {
    return p2.objs;
  }

  public ArrayList<AprilTagDetection> getLatestDetections() {
    return p3.getLatestDetections();
  }

  public int getDetection() {
    if (p3.getLatestDetections().size() > 0) {
      return p3.getLatestDetections().get(0).id;
    }
    return 5;
  }

  public void stop() {
    webcam.stopStreaming();
  }
}
