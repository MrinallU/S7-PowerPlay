package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Autonomous;


import org.firstinspires.ftc.teamcode.T1_2022.Base;

public class RedPrimary extends Base {
  @Override
  public void runOpMode() throws InterruptedException {
    initHardware(0, this);
    sleep(500);
    telemetry.addData("Status", "Initialized");
    telemetry.update();

    waitForStart();
    matchTime.reset();
    dt.resetCache();

    dt.driveFieldCentric(0.3, 0, 0);
    sleep(1000);
    dt.stopDrive();

    // grabber.raiseToPosition();
  }
}
