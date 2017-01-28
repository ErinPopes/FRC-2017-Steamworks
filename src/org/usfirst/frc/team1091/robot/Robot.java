package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Timer;

import static org.usfirst.frc.team1091.robot.StartingPosition.*;

public class Robot extends SampleRobot {

	private RobotDrive myRobot;
	private Joystick xbox; // xbox controller
	final double deadZone = 0.02;
	DriverStation.Alliance color;
	DigitalInput bottomLimitSwitch;
	DigitalInput topLimitSwitch;
	DigitalInput lifterSwitch;
	Relay lifterSpike;
	Victor door;

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

		chooser.addDefault(CENTER.name(), CENTER);
		chooser.addObject(LEFT.name(), LEFT);
		chooser.addObject(RIGHT.name(), RIGHT);
		SmartDashboard.putData("Auto choices", chooser);

	}

	// MAIN AUTONOMOUS METHOD

	public void autonomous() {
		System.out.println("Auto F.A.P for success (For arreal persoison)");

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
		xboxDrive(); // For xbox controls
		gearDoor();
		lifter();
		// xboxAutoShoot(angle, RPM);

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

		if (lifterSwitch.get() == false && liftStartButton) {
			lifterSpike.setDirection(Direction.kReverse);
			lifterSpike.set(Value.kOn);
		} else {
			lifterSpike.set(Value.kOff);
		}

	}

	// Button controls
	private void gearDoor() {
		boolean doorOpenButton = xbox.getRawButton(6);
		boolean doorCloseButton = xbox.getRawButton(5);

		if (doorOpenButton && topLimitSwitch.get()) {
			while (topLimitSwitch.get()) { // run door motor forwards to open
											// door
				door.set(0.5);
			}
			door.set(0);
		}

		if (doorCloseButton && bottomLimitSwitch.get()) {
			while (bottomLimitSwitch.get()) { // run door motor backwards to
												// close door
				door.set(-0.5);
			}
			door.set(0);
		}

		// if doorButton = true &&

	}

	// XBOX DRIVING CONTROLS
	private void xboxDrive() {
		double yAxis = xbox.getRawAxis(1) * .60;
		double xAxis = xbox.getRawAxis(0) * -.60;
		if (!(Math.abs(yAxis) < deadZone) || !(Math.abs(xAxis) < deadZone))
			myRobot.arcadeDrive(yAxis, xAxis, true);
	}

	@Override
	public void disabled() {
		System.out.println("time to just wait.. and wait");
	}
}
