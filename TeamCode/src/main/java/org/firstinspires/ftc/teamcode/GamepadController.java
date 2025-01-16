package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class GamepadController {
    private Gamepad gamepad1;
    private Gamepad gamepad2;
    private ArmSystem armSystem;
    private ClawSystem clawSystem;
    private DriveTrain driveTrain;
    private IMUSensor imuSensor;
    private InputFilter inputFilter;
    private boolean fieldCentric = false;
    private boolean prevY = false;
    private ElapsedTime timer;
    private double prevUpdate;
    private Telemetry telemetry;

    public GamepadController(Gamepad gamepad1, Gamepad gamepad2, ArmSystem armSystem, ClawSystem clawSystem, DriveTrain driveTrain, IMUSensor imuSensor, Telemetry telemetry){
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
        this.armSystem = armSystem;
        this.clawSystem = clawSystem;
        this.driveTrain = driveTrain;
        this.imuSensor = imuSensor;
        inputFilter = new InputFilter();
        timer = new ElapsedTime();
        this.telemetry = telemetry;
        prevUpdate = 0.0;
    }

    public void update() {
        handleToggles();
        handleDrive();
        handleArm();
        handleClaw();
    }

    private void handleToggles(){
        if (gamepad1.y && !prevY){
            fieldCentric = !fieldCentric;
        }
        prevY = gamepad1.y;
    }

    private void handleClaw(){
        if (gamepad2.right_trigger >0.2){
            clawSystem.in();
        }
        if (gamepad2.left_trigger > 0.2){
            clawSystem.out();
        }
        if (gamepad2.left_bumper || gamepad2.right_bumper){
            clawSystem.stop();
        }
    }

    private void handleArm(){
        double ly = -gamepad2.left_stick_y;
        double ry = -gamepad2.right_stick_y;
        if (timer.milliseconds()-prevUpdate >= 10) {
            if (ly > 0.5) {
                armSystem.aup();
                prevUpdate = timer.milliseconds();
            }
            if (ly < -0.5) {
                armSystem.adown();
                prevUpdate = timer.milliseconds();
            }
            if (ry > 0.5) {
                armSystem.bup();
                prevUpdate = timer.milliseconds();
            }
            if (ry < -0.5) {
                armSystem.bdown();
                prevUpdate = timer.milliseconds();
            }
        }
        armSystem.update();
    }

    private void handleDrive(){
        double x = gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double r = gamepad1.right_stick_x;
        x = inputFilter.applyDeadzone(x);
        y = inputFilter.applyDeadzone(y);
        r = inputFilter.applyDeadzone(r);
        x = inputFilter.scaleInput(x);
        y = inputFilter.scaleInput(y);
        r = inputFilter.scaleInput(r);
        /*
        double magnitude = Math.sqrt(y*y+x*x+r*r);
        telemetry.addData("raw magnitude:", magnitude);
        double directionX = 0;
        double directionY = 0;
        double directionR = 0;
        if (magnitude != 0){
            directionX = x/magnitude;
            directionR = r/magnitude;
            directionY = y/magnitude;
        }
        boolean suddenChange = Math.abs(magnitude-inputFilter.getPrevMagnitude())>0.5;
        telemetry.addData("suddentChange:",suddenChange);
        double dynamicAlpha = suddenChange ? 0.9 : (magnitude<0.3 ? 0.3:0.5);
        telemetry.addData("emaAlpha:",dynamicAlpha);
        magnitude = inputFilter.applySmoothing(magnitude,dynamicAlpha);
        telemetry.addData("smoothedMagnitude:",magnitude);
        x = magnitude*directionX;
        y = magnitude*directionY;
        r = magnitude*directionR;
        telemetry.addLine("Smoothed Inputs |")
                        .addData("x:",x)
                                .addData("y:",y)
                                        .addData("r:",r);
        inputFilter.setPrevMagnitude(magnitude)
         */
        if (fieldCentric){
            double heading = imuSensor.getFilteredHeading();
            double cosA = Math.cos(-heading);
            double sinA = Math.sin(-heading);
            double oldX = x;
            x = x*cosA-y*sinA;
            y = oldX*sinA+y*cosA;
        }
        double luPower = x+y+r;
        double ruPower = y-x-r;
        double ldPower = y-x+r;
        double rdPower = y+x-r;
        double maxOutput = Math.max(Math.abs(luPower), Math.abs(ruPower));
        maxOutput = Math.max(maxOutput, Math.abs(ldPower));
        maxOutput = Math.max(maxOutput, Math.abs(rdPower));
        if (maxOutput > 1.0)
        {
            luPower /= maxOutput;
            ruPower /= maxOutput;
            ldPower /= maxOutput;
            rdPower /= maxOutput;
        }
        driveTrain.setMotorPowers(luPower,ruPower,ldPower,rdPower);
    }

    public void clearTimer()
    {
        timer.reset();
    }
}
