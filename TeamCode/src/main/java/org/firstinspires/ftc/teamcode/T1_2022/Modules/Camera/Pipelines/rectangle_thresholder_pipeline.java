package org.firstinspires.ftc.teamcode.T1_2022.Modules.Camera.Pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class rectangle_thresholder_pipeline extends OpenCvPipeline {

  private int out;
  private boolean saveImg;
  public Scalar lowerOrange = new Scalar(0, 89.3, 0);
  public Scalar upperOrange = new Scalar(28.3, 175.7, 255);

  public Scalar lowerBlue = new Scalar(110.5, 48.2, 0);
  public Scalar upperBlue = new Scalar(255, 144.5, 255);

  public Scalar lowerGreen = new Scalar(32.6, 52.4, 123.3);
  public Scalar upperGreen = new Scalar(80, 255, 255);

  private Mat hsvMat = new Mat(), binaryMat = new Mat();
  private Point topLeft1 = new Point(93, 80),
      bottomRight1 = new Point(115, 110); // Analyzed area is constant

  /*public rectangle_thresholder_pipeline(Telemetry telemetry) {
    this.telemetry = telemetry;
    saveImg = false;
  }*/

  public rectangle_thresholder_pipeline() {
    saveImg = true;
  }

  public rectangle_thresholder_pipeline(boolean s) {
    saveImg = s;
  }

  // GAME SPECIFIC CODE
  public double thresholdColor(Mat input, Scalar lower, Scalar upper) {
    Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
    Core.inRange(hsvMat, lower, upper, binaryMat);

    double w1 = 0, w2 = 0;
    // process the pixel value for each rectangle  (255 = W, 0 = B)
    for (int i = (int) topLeft1.x; i <= bottomRight1.x; i++) {
      for (int j = (int) topLeft1.y; j <= bottomRight1.y; j++) {
        if (binaryMat.get(i, j)[0] == 255) {
          w1++;
        }
      }
    }

    return w1;
  }

  @Override
  public Mat processFrame(Mat input) {
    double winR, winB, winG;
    winB = thresholdColor(input, lowerBlue, upperBlue);
    winG = thresholdColor(input, lowerGreen, upperGreen);
    winR = thresholdColor(input, lowerOrange, upperOrange);

    double best = Math.max(Math.max(winB, winG), winR);

    Imgproc.rectangle(
        input,
        new Point(topLeft1.x, topLeft1.y),
        new Point(bottomRight1.x, bottomRight1.y),
        new Scalar(255, 0, 0));

    if (best == winR) out = 3;
    else if (best == winB) out = 2;
    else out = 1;

    if (saveImg) {
      saveMatToDisk(input, "rect_manual_img");
      saveImg = false;
    }

    return input;
  }

  public int getOut() {
    return out;
  }
}
