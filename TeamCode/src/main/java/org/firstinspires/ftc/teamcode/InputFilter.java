package org.firstinspires.ftc.teamcode;

public class InputFilter {
    private final double deadzone = 0.02;
    private final int scaleExp = 3;
    private double prevMagnitude = 0.0;

    public double applyDeadzone(double input){
        if (Math.abs(input)>deadzone){
            return input;
        }
        return 0.0;
    }

    public double scaleInput(double input){
        return Math.pow(input,scaleExp);
    }

    public double applySmoothing(double magnitude,double dynamicAlpha){
        return magnitude*dynamicAlpha+prevMagnitude*(1-dynamicAlpha);
    }

    public double getPrevMagnitude(){
        return prevMagnitude;
    }

    public void setPrevMagnitude(double m){
        prevMagnitude = m;
    }
}
