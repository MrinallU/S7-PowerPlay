package org.firstinspires.ftc.teamcode.TestingRobot.Opmodes.Tests.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.TestingRobot.Base;

@TeleOp(name = "Test_TeleOp", group = "OdomBot")
public class Test_TeleOp extends Base {
    // TeleOp Variables
    @Override
    public void runOpMode() throws InterruptedException {
        initHardware(0, this);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        matchTime.reset();
        dt.updateencodo();

        while (opModeIsActive()) {
            // Updates
            dt.resetCache();
            dt.updateencodo();
            dt.updateencodo();

            currAngle = dt.getAngle();

            // Change Drive Mode
            yLP = yP;
            yP = gamepad1.y;
            if (!yLP && yP) {
                basicDrive = !basicDrive;
            }

            // Drive
            slowDrive = gamepad1.left_bumper;
            fastDrive = gamepad1.left_trigger > 0.05;
            drive = floor(gamepad1.right_stick_x);
            strafe = floor(-gamepad1.right_stick_y);
            turn = turnFloor(gamepad1.left_stick_x);
            computeDrivePowers(gamepad1);


            // Display Values
            telemetry.addData("Drive Type", driveType);
            telemetry.addData("Odometry Info", dt.getCurrentPosition());
            telemetry.addData("Enc Odo", dt.xEncPosv);
            telemetry.addLine(String.valueOf(dt.yEncPos));
            telemetry.update();
        }
    }
}
