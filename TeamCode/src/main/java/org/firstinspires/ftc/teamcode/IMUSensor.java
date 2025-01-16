package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class IMUSensor {
    private IMU imu;
    private final RevHubOrientationOnRobot.LogoFacingDirection logoFacingDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
    private final RevHubOrientationOnRobot.UsbFacingDirection usbFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
    private final double alpha = 0.2;
    private double filteredYaw = 0;

    public IMUSensor(HardwareMap hardwareMap){
        imu = hardwareMap.get(IMU.class,"imu");
        initIMU();
    }

    private void initIMU(){
        RevHubOrientationOnRobot ori = new RevHubOrientationOnRobot(logoFacingDirection,usbFacingDirection);
        IMU.Parameters params = new IMU.Parameters(ori);
        imu.initialize(params);
    }

    public double getFilteredHeading(){
        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
        double newYaw = angles.getYaw(AngleUnit.RADIANS);
        filteredYaw = alpha*newYaw+(1-alpha)*filteredYaw;
        return filteredYaw;
    }

}
