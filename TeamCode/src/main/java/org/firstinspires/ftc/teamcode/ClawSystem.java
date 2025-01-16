package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawSystem {
    private CRServo up;
    private CRServo down;
    private double speed;
    public ClawSystem(HardwareMap hardwareMap){
        up = hardwareMap.crservo.get("up");
        down = hardwareMap.crservo.get("down");
        speed = 1;
        initServos();
    }

    private void initServos(){
        stop();
    }

    public void stop(){
        up.setPower(0);
        down.setPower(0);
    }

    public void in(){
        up.setPower(speed);
        down.setPower(-speed);
    }

    public void out(){
        up.setPower(-speed);
        down.setPower(speed);

    }

    public double getSpeed(){
        return speed;
    }
}
