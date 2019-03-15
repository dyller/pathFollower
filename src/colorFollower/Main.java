package colorFollower;

import java.awt.Color;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.ColorAdapter;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

public class Main {
	static final int centimeter= 10; 
	static EV3 brick = (EV3) BrickFinder.getDefault();
	//static Wheel leftWheel = WheeledChassis.modelWheel(Motor.B, 56).offset(-53.2);
	//static Wheel rigthWheel = WheeledChassis.modelWheel(Motor.C, 56).offset(53.2);
	static int colorId;
	static int speed = 100;
	static int acceleration = 100;
	static boolean done  = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Button.ESCAPE.addKeyListener(new lejos.hardware.KeyListener() {

			@Override
			public void keyPressed(Key k) {
				// TODO Auto-generated method stub
				done = true;
				
			}

			@Override
			public void keyReleased(Key k) {
				// TODO Auto-generated method stub
				
			}
		});
		
		try(
		EV3ColorSensor lightSensor = new EV3ColorSensor(brick.getPort("S2"));
		EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(brick.getPort(("C")));
		EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(brick.getPort(("B")));
		)
		{
			ColorAdapter colorAdapter = new ColorAdapter(lightSensor);
			
			
			rightMotor.setAcceleration(acceleration);
			leftMotor.setAcceleration(acceleration);
			
		/*Chassis chassis = new WheeledChassis(new Wheel[] { leftWheel, rigthWheel }, WheeledChassis.TYPE_DIFFERENTIAL);
		MovePilot pilot = new MovePilot(chassis);
		pilot.setLinearAcceleration(acceleration);
		pilot.setLinearSpeed(speed);*/
		while(!done) {
			rightMotor.setSpeed(speed);
			leftMotor.setSpeed(speed);
			Delay.msDelay(200);
			leftMotor.forward();
			rightMotor.forward();
			
			colorId = colorAdapter.getColorID();
		while(colorId != lejos.robotics.Color.BLACK &&!done)
		{
			rightMotor.setSpeed(speed);
			leftMotor.setSpeed(speed);
			colorId = colorAdapter.getColorID();
			LCD.drawString("BLack: "+lejos.robotics.Color.BLACK, 0, 0);
			LCD.drawString("Color: "+colorId,0,1);
			leftMotor.setSpeed(speed/2);
			
		}
		while(colorId == lejos.robotics.Color.BLACK && !done)
		{
			rightMotor.setSpeed(speed);
			leftMotor.setSpeed(speed);
			colorId = colorAdapter.getColorID();
			LCD.drawString("Not Black", 0, 0);
			LCD.drawString("Color: "+colorId,0,1);
			rightMotor.setSpeed((int)(speed/1.5));
			
	}
		
		}
		}

	}
}
