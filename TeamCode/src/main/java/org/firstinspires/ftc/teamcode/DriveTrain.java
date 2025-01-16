package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveTrain {
    private DcMotorEx lu;
    private DcMotorEx ru;
    private DcMotorEx ld;
    private DcMotorEx rd;
    private double maxTicksPerSecond;
    private final double maxRPM = 312;
    private final double ticksPerRevolution = 537.7;
    private final double gearing = 19.2;
    private final double ticksLimiter = 0.6;

    public DriveTrain(HardwareMap hardwareMap){
        lu =hardwareMap.get(DcMotorEx.class,"lu");
        ru =hardwareMap.get(DcMotorEx.class,"ru");
        ld=hardwareMap.get(DcMotorEx.class,"ld");
        rd=hardwareMap.get(DcMotorEx.class,"rd");
        initMotors();
    }

    private void initMotors(){
        DcMotorEx[] motors = {lu, ru,ld,rd};
        for (DcMotorEx motor:motors){
            motor.getMotorType().setMaxRPM(maxRPM);
            motor.getMotorType().setGearing(gearing);
            motor.getMotorType().setTicksPerRev(ticksPerRevolution);
            motor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        }
        lu.setDirection(DcMotorEx.Direction.REVERSE);
        ld.setDirection(DcMotorEx.Direction.REVERSE);
        maxTicksPerSecond = ld.getMotorType().getAchieveableMaxTicksPerSecond();
    }

    public void setMotorPowers(double luPower,double ruPower,double ldPower,double rdPower){
        lu.setVelocity(luPower*maxTicksPerSecond*ticksLimiter);
        ru.setVelocity(ruPower*maxTicksPerSecond*ticksLimiter);
        ld.setVelocity(ldPower*maxTicksPerSecond*ticksLimiter);
        rd.setVelocity(rdPower*maxTicksPerSecond*ticksLimiter);
    }

    public void stop(){
        setMotorPowers(0,0,0,0);
    }
}
