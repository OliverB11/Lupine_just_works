package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Hardware.Controls.Controller;
import org.firstinspires.ftc.teamcode.Hardware.Robot;
import org.firstinspires.ftc.teamcode.Utilities.MathUtils;
import org.firstinspires.ftc.teamcode.StaticStuff.Side;

import static org.firstinspires.ftc.teamcode.Utilities.OpModeUtils.setOpMode;


@TeleOp(name="Base TeleOp", group="Iterative Opmode")
public class BaseTeleOp extends OpMode {

    // Declare Things
    private ElapsedTime runtime = new ElapsedTime();
    public static Robot robot;
    double power;
    Controller controller;
    Controller controller2;
    double setPoint = 360;
    boolean wasTurning;



//Code to run when the driver hits INIT
    @Override
    public void init() {
        setOpMode(this);


        //Set power and initialize things
        power = 0.6;
        robot = new Robot();
        controller = new Controller(gamepad1);
        controller2 = new Controller(gamepad2);

        if (!Side.blue && !Side.red) {
            Side.blue = true;
            Side.red = false;
        }
    }



//Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() {


    }


//Code to run ONCE when the driver hits PLAY

    @Override
    public void start() {
        runtime.reset();

    }


//Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
    @Override
    public void loop() {

//In-Loop variable declarations
        double rotation;


// Updates (Don't forget these)
        controller.controllerUpdate();
        controller2.controllerUpdate();
        robot.chassis.gyro.update();


// PID

        if (!(controller.rightStick().x == 0)) {
            rotation = controller.rightStick().x;
            wasTurning = true;
        } else if (wasTurning && Math.abs(robot.chassis.gyro.rateOfChange()) > 0) {
            rotation = controller.rightStick().x;
        } else {
            if (wasTurning) {
                setPoint = robot.chassis.gyro.angle();
                wasTurning = false;
            }
            rotation = robot.chassis.pid.update(robot.chassis.gyro.angle() - setPoint);
        }

// Speed Control
        if (controller.RB.press()) {
            power = 0.2;
        }else if(controller.LB.press()){
            power = 0.5;
        } else {
            power = 0.8;
        }


//Gyro Reset ability
        if (controller.share.tap()) {
            setPoint = 0;
            robot.chassis.gyro.reset();
        }

// Switch Sides
        if(controller2.share.tap()) {
            if (Side.blue) {
                Side.blue = false;
                Side.red = true;
            } else if (Side.red) {
                Side.blue = true;
                Side.red = false;
            }
        }


// Stuff

        //Controller1 Stuff

        //Controller 2 Stuff




        //Automatic Turning
        if (controller.left.press()) {
            setPoint = MathUtils.closestAngle(90, robot.chassis.gyro.angle());
        }
        if (controller.up.press()) {
            setPoint = MathUtils.closestAngle(0, robot.chassis.gyro.angle());
        }
        if (controller.down.press()) {
            setPoint = MathUtils.closestAngle(180, robot.chassis.gyro.angle());
        }
        if (controller.right.press()) {
            setPoint = MathUtils.closestAngle(270, robot.chassis.gyro.angle());
        }





        //Movement control
        double drive = -MathUtils.shift(controller.leftStick(), robot.chassis.gyro.angle()).y;
        double strafe = MathUtils.shift(controller.leftStick(), robot.chassis.gyro.angle()).x;
        double turn = -rotation;

        robot.chassis.setDrivePower(power, strafe, turn, drive);


        //Telemetry


    }

//Code to run once when you hit stop (Normally theres nothing here)
    @Override
    public void stop(){

    }
}