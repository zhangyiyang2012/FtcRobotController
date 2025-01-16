package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmSystem {
    private DcMotorEx aArm;
    private DcMotorEx bArm;
    private final double distance = 4;
    private int aTarget;
    private int bTarget;
    private final double maxRPM = 312;
    private final double ticksPerRevolution = 537.7;
    private final double gearing = 19.2;

    public ArmSystem(HardwareMap hardwareMap){
        aArm = hardwareMap.get(DcMotorEx.class,"elbowa");
        bArm = hardwareMap.get(DcMotorEx.class,"elbowb");
        aTarget = 0;
        bTarget = 0;
        initMotors();
    }

    private void initMotors() {
        DcMotorEx[] motors = {aArm, bArm};
        for (DcMotorEx motor : motors) {
            motor.getMotorType().setMaxRPM(maxRPM);
            motor.getMotorType().setGearing(gearing);
            motor.getMotorType().setTicksPerRev(ticksPerRevolution);
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
    }

    public void aup(){
        if (aTarget < 1200){
            aTarget += distance;
        }
    }

    public void adown(){
        if (aTarget >= 4){
            aTarget -= distance;
        }
    }
    public void bup(){
        if (bTarget < 1200){
            bTarget += distance;
        }
    }

    public void bdown(){
        if (bTarget >= 4){
            bTarget -= distance;
        }
    }



    public void update(){
        aArm.setTargetPosition(aTarget);
        bArm.setTargetPosition(bTarget);
        aArm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        bArm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        aArm.setPower(0.8);
        bArm.setPower(0.8);
    }
    public int getaTarget(){
        return aTarget;
    }

    public int getaTicks()
    {
        return aArm.getCurrentPosition();
    }
}
