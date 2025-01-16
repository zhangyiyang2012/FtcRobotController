package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "testTeleOp")
public class testTeleOp extends LinearOpMode {
    DriveTrain driveTrain;
    ClawSystem clawSystem;
    ArmSystem armSystem;
    ElapsedTime timer = new ElapsedTime();
    double prevUpdate;

    @Override
    public void runOpMode(){
        driveTrain = new DriveTrain(hardwareMap);
        clawSystem  = new ClawSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap);
        waitForStart();
        timer.reset();
        prevUpdate = 0;
        while (opModeIsActive()){
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double r = gamepad1.right_stick_x;

            double luPower = y + x + r;
            double ruPower = y - x - r;
            double ldPower = y - x + r;
            double rdPower = y + x - r;

            double maxPower = Math.max(Math.abs(luPower), Math.abs(ruPower));
            maxPower = Math.max(maxPower, Math.abs(ldPower));
            maxPower = Math.max(maxPower, Math.abs(rdPower));
            if (maxPower > 1.0)
            {
                luPower /= maxPower;
                ruPower /= maxPower;
                ldPower /= maxPower;
                rdPower /= maxPower;
            }

            driveTrain.setMotorPowers(luPower,ruPower,ldPower,rdPower);

            if (gamepad1.right_trigger>0.2){
                clawSystem.in();
            }
            if (gamepad1.left_trigger>0.2){
                clawSystem.out();
            }
            if (gamepad1.right_bumper || gamepad1.left_bumper){
                clawSystem.stop();
            }

            if (gamepad1.dpad_up){
                if (timer.milliseconds()-prevUpdate >= 10){
                    armSystem.aup();
                    prevUpdate = timer.milliseconds();
                }
            }
            if (gamepad1.y){
                if (timer.milliseconds()-prevUpdate >= 10){
                    armSystem.adown();
                    prevUpdate = timer.milliseconds();
                }
            }
            if (gamepad1.dpad_left){
                if (timer.milliseconds()-prevUpdate >= 10){
                    armSystem.bdown();
                    prevUpdate = timer.milliseconds();
                }
            }
            if (gamepad1.dpad_right){
                if (timer.milliseconds()-prevUpdate >= 10){
                    armSystem.bup();
                    prevUpdate = timer.milliseconds();
                }
            }
            armSystem.update();

            telemetry.addData("atarget", armSystem.getaTarget());
            telemetry.addData("aTicks", armSystem.getaTicks());
            telemetry.update();
        }
    }
}
