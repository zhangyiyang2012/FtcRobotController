package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "testTeleOp")
public class testTeleOp extends LinearOpMode {
    DriveTrain driveTrain;
    ClawSystem clawSystem;
    ArmSystem armSystem;
    IMUSensor imuSensor;
    GamepadController gamepadController;

    @Override
    public void runOpMode(){
        driveTrain = new DriveTrain(hardwareMap);
        clawSystem  = new ClawSystem(hardwareMap);
        armSystem = new ArmSystem(hardwareMap);
        imuSensor = new IMUSensor(hardwareMap);
        gamepadController = new GamepadController(gamepad1,gamepad2,armSystem,clawSystem,driveTrain,imuSensor,telemetry);
        waitForStart();
        gamepadController.clearTimer();
        while (opModeIsActive()){
            gamepadController.update();
            telemetry.update();
        }
    }
}
