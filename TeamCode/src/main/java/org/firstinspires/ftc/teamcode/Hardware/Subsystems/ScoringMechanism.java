package org.firstinspires.ftc.teamcode.Hardware.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.hardwareMap;

import org.firstinspires.ftc.teamcode.Hardware.Sensors.Color_Sensor;

public class ScoringMechanism {
    public DcMotor spool;
    public Servo bucket;
    Color_Sensor bucketSensor;
    public ElapsedTime time = new ElapsedTime();
    public boolean armUp = false;
    public boolean readyForRumble = false;

    public ScoringMechanism() {
        spool = hardwareMap.get(DcMotor.class, "spool");
        bucket = hardwareMap.get(Servo.class, "bucket");
        bucketSensor = new Color_Sensor();
        bucketSensor.init("bucketColor");
        bucket.setPosition(0.82);
        resetMotors();

    }

    public void resetMotors() {
        spool.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        spool.setTargetPosition(0);
        spool.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        spool.setPower(0.8);


    }



    public void top() {
        readyForRumble = false;
        armUp = true;
        if(time.seconds() > 0 && time.seconds() < .1){
            bucket.setPosition(0.7);
        }
        if (time.seconds() > .1 && time.seconds() < .7) {
            spool.setTargetPosition(-1800);
            spool.setPower(((spool.getTargetPosition() - spool.getCurrentPosition()) / (double) spool.getTargetPosition())+ 0.5);        }
    }


    public void middle() {
        readyForRumble = false;
        armUp = true;
        if(time.seconds() > 0 && time.seconds() < .1){
            bucket.setPosition(0.7);
        }
        if (time.seconds() > .1 && time.seconds() < .7) {
            spool.setTargetPosition(-1000);
            spool.setPower(((spool.getTargetPosition() - spool.getCurrentPosition()) / (double) spool.getTargetPosition())+ 0.5);        }
    }

    public void bottom() {
        readyForRumble = false;
        armUp = true;
        if(time.seconds() > 0 && time.seconds() < .1){
            bucket.setPosition(0.7);
        }
        if (time.seconds() > .1 && time.seconds() < .7) {
            spool.setTargetPosition(-500);
            spool.setPower(((spool.getTargetPosition() - spool.getCurrentPosition()) / (double) spool.getTargetPosition())+ 0.5);        }
    }

    public void deposit() {
        readyForRumble = false;
        armUp = true;
        if (time.seconds() > 0 && time.seconds() < 0.5) {
            bucket.setPosition(0.1);
        }
        if(time.seconds() > 1 && time.seconds() < 1.5){
            bucket.setPosition(0.7);
        }
        if(time.seconds() > 1.5 && time.seconds() < 1.7){
            spool.setTargetPosition(-300);
        }
        if(time.seconds() > 1.7 && time.seconds() < 1.8){
            bucket.setPosition(0.7);
            armUp = false;
        }

    }


    public void driving(){
        readyForRumble = false;
        if(time.seconds() > 0 && time.seconds() < .1){
            bucket.setPosition(0.7);
        }
        if(time.seconds() > 0.2 && time.seconds() < .5){
            spool.setTargetPosition(-300);
        }
    }


    public void intake(){
        if (time.seconds() >  0 && time.seconds() < 0.2) {
            spool.setTargetPosition(0);
        }
        if (time.seconds() > 0.2 && time.seconds() < 0.3) {
            bucket.setPosition(0.82);
            armUp = false;
            readyForRumble = true;
        }
    }

    public void autoTop() {
        time.reset();
        bucket.setPosition(0.7);
        wait(0.4);
        spool.setTargetPosition(-1800);
        spool.setPower(((spool.getTargetPosition() - spool.getCurrentPosition()) / (double) spool.getTargetPosition())+ 0.5);
    }


    public void autoMiddle() {
        time.reset();
        bucket.setPosition(0.7);
        wait(0.4);
        spool.setTargetPosition(-1000);
        spool.setPower(((spool.getTargetPosition() - spool.getCurrentPosition()) / (double) spool.getTargetPosition())+ 0.5);
    }
    public void autoBottom() {
        time.reset();
        bucket.setPosition(0.7);
        wait(0.4);
        spool.setTargetPosition(-500);
        spool.setPower(((spool.getTargetPosition() - spool.getCurrentPosition()) / (double) spool.getTargetPosition())+ 0.5);
    }

    public void autoDeposit() {
        bucket.setPosition(0.1);
        wait(1.0);
        bucket.setPosition(0.7);
        wait(0.2);
        spool.setTargetPosition(0);
        wait(0.3);
        bucket.setPosition(0.82);
    }



    public void wait(double timeout){
        time.reset();
        while(time.seconds()<timeout){

        }
    }

    public void updateBucketSensor(){
        bucketSensor.updateRed();
        bucketSensor.updateGreen();
    }

    public boolean isLoaded(){
        if(bucketSensor.getRedCacheValue() > 120 && bucketSensor.getGreenCacheValue() > 200){
            return(true);
        }else{
            return(false);
        }
    }

}
