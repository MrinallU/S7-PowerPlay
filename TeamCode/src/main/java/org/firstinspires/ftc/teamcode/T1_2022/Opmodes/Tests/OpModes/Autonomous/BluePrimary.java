package org.firstinspires.ftc.teamcode.T1_2022.Opmodes.Tests.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.T1_2022.Base;

@Autonomous(name = "Blue_Primary", group = "OdomBot")

public class BluePrimary extends Base {
    @Override
    public void runOpMode() throws InterruptedException {
        ElapsedTime timer = new ElapsedTime();
        initHardware(0, this);
        grabber.grabCone();
        sleep(500);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        matchTime.reset();
        dt.resetCache();

        int location = 2;
        //location = getLocation();

        timer.reset();
        while (timer.milliseconds() <= 1290) {
            dt.driveFieldCentric(0, 0, 0.3, 1);
        }
        dt.stopDrive();
        sleep(300);
        turnTo(0,2000);
        sleep(300);

        timer.reset();
        while (timer.milliseconds() <= 1100) {
            dt.driveFieldCentric(-0.2, 0, 0, 1);
        }
        dt.stopDrive();
//        sleep(900);
        // Raise slide and drop
        grabber.raiseMiddle();
        sleep(2000);

        timer.reset();
        while (timer.milliseconds() <= 850) {
            dt.driveFieldCentric(0, 0, 0.1, 1);
        }
        dt.stopDrive();

        grabber.releaseCone();
        sleep(500);
        grabber.grabCone();

        timer.reset();
        while (timer.milliseconds() <= 1000) {
            dt.driveFieldCentric(0, 0, -0.1, 1);
        }
        dt.stopDrive();

        grabber.restArm();
        sleep(1000);

        // park
        timer.reset();
        if(location==3){
            while (timer.milliseconds() <= 3000) {
                dt.driveFieldCentric(0.2, 0, 0, 1);
            }
        }else if(location==2){
            while (timer.milliseconds() <= 1000) {
                dt.driveFieldCentric(0.2, 0, 0, 1);
            }
        }else{
            while (timer.milliseconds() <= 1000) {
                dt.driveFieldCentric(-0.2, 0, 0, 1);
            }
        }
        dt.stopDrive();

    }
}

