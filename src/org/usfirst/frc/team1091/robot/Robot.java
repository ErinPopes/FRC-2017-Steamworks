package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Timer;

import static org.usfirst.frc.team1091.robot.StartingPosition.*;

public class Robot extends SampleRobot {

	private RobotDrive myRobot;
	private Joystick xbox; // xbox controller
	final double deadZone = 0.02;
	private CameraServer camera;
	DriverStation.Alliance color;
	DigitalInput bottomLimitSwitch;
	DigitalInput topLimitSwitch;
	DigitalInput lifterSwitch;
	Relay lifterSpike;
	Victor door;
	Spark climer;
	private Encoder lEncod, rEncod; // 20 per rotation

	SendableChooser<StartingPosition> chooser = new SendableChooser<>();
	StartingPosition autoSelected;

	@Override
	public void robotInit() {

		color = DriverStation.getInstance().getAlliance();
		System.out.print(color.name());
		myRobot = new RobotDrive(0, 1, 2, 3);
		myRobot.setExpiration(0.1);
		xbox = new Joystick(0);
		bottomLimitSwitch = new DigitalInput(0);
		topLimitSwitch = new DigitalInput(1);
		door = new Victor(4);
		lifterSwitch = new DigitalInput(2);
		lifterSpike = new Relay(0);
		lEncod = new Encoder(3, 4, true);
		rEncod = new Encoder(5, 6);
		climer = new Spark(5);
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);

		chooser.addDefault(CENTER.name(), CENTER);
		chooser.addObject(LEFT.name(), LEFT);
		chooser.addObject(RIGHT.name(), RIGHT);
		SmartDashboard.putData("Auto choices", chooser);

	}

	// MAIN AUTONOMOUS METHOD

	public void autonomous() {

		autoSelected = chooser.getSelected();

		switch (autoSelected) {

		case LEFT:
			autonomousLeft();
			break;

		case RIGHT:
			autonomousRight();
			break;

		case CENTER:
			autonomousCenter();
			break;

		}

	}

	private void autonomousLeft() {

		// Tell what you do in autonomous left here
		System.out.println("Do Left Function");

	}

	private void autonomousCenter() {

		// Tell what you do in autonomous middle here
		System.out.println("Do Middle Function");

	}

	private void autonomousRight() {

		// Tell what you do in autonomous right here
		System.out.println("Do Right Function");

	}

	// public class Robot extends IterativeRobot {
	// final String defaultAuto = "Default";
	// final String customAuto = "My Auto";
	// final String defaultAuto = "Default";

	// String autoSelected;

	long lLastEncoderVal = 0;
	long rLastEncoderVal = 0;
	long lastTime = System.currentTimeMillis();

	// UPDATE CONTROLS AND SENSORS
	private void refresh() throws InterruptedException {
		lLastEncoderVal = lEncod.get();
		rLastEncoderVal = rEncod.get();
		xboxDrive(); // For xbox controls
		gearDoor();
		lifter();
	}

	// MAIN WHILE LOOP
	public void operatorControl() {//
		myRobot.setSafetyEnabled(true);
		while (isOperatorControl() && isEnabled()) {
			try {
				refresh();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} // Update controls and sensors
			Timer.delay(0.001); // wait for a motor update time

		}
	}

	// Lifter code
	private void lifter() {
		boolean liftStartButton = xbox.getRawButton(4);
		boolean liftDownButton = xbox.getRawButton(3);
		// switch to use pwm instead of the spike, because the spike is a lying
		// piece of sh!t who made your wife cheat on you

		if (lifterSwitch.get() == false && liftStartButton) {
			climer.set(-.9);
		} else if (liftDownButton){
			climer.set(.3);
		}
		else{
			climer.set(0);
		}

	}

	// Button controls
	private void gearDoor() {
		boolean doorOpenButton = xbox.getRawButton(6);
		boolean doorCloseButton = xbox.getRawButton(5);

		if (doorOpenButton && topLimitSwitch.get()) {
			door.set(0.5);
		} else if (doorCloseButton && bottomLimitSwitch.get()) {
			door.set(-0.5);
		} else {
			door.set(0);
		}

		// if doorButton = true &&

	}

	// XBOX DRIVING CONTROLS
	private void xboxDrive() {
		double yAxis = xbox.getRawAxis(1) * -.8;
		double xAxis = xbox.getRawAxis(0) * -.8;
		if (!(Math.abs(yAxis) < deadZone) || !(Math.abs(xAxis) < deadZone))
			myRobot.arcadeDrive(yAxis, xAxis, true);
	}

	@Override
	public void disabled() {
		System.out.println("time to just wait.. and wait");
	}
}
