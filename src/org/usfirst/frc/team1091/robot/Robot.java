package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import static org.usfirst.frc.team1091.robot.StartingPosition.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

public class Robot extends IterativeRobot {

	private RobotDrive myRobot;
	private Joystick xbox; 
	final double deadZone = 0.02;
	private CameraServer camera;
	DriverStation.Alliance color;
	DigitalInput openLimitSwitch; // Limit Switch is pushed in when door is
									// open. False when door is open.
	DigitalInput closedLimitSwitch; // Limit Switch is pushed in when door is
									// closed. False when door is closed.
	DigitalInput lifterSwitch;
	Relay lifterSpike;
	Spark door;
	Spark climber;
	Encoder encoder;
	
	private Encoder lEncod, rEncod; // 20 per rotation on the encoder, 360 per rotation on the wheel
	

	SendableChooser<StartingPosition> chooser;
	StartingPosition autoSelected;
	
	float visionCenter;
	final double ticksPerInch = 360.0 / (4.0 * Math.PI);

	/*************************
	 * ROBOT code that is called once for all modes
	 */
	
	@Override
	public void robotInit() {	
		color = DriverStation.getInstance().getAlliance();
		System.out.print(color.name());

		myRobot = new RobotDrive(0, 1, 2, 3);
		myRobot.setExpiration(0.1);
		xbox = new Joystick(0);

		openLimitSwitch = new DigitalInput(0);
		closedLimitSwitch = new DigitalInput(1);
		door = new Spark(4);
		lifterSwitch = new DigitalInput(2);
		lifterSpike = new Relay(0);

		lEncod = new Encoder(3, 4, true);
		rEncod = new Encoder(5, 6);

		climber = new Spark(5);
		
//		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
//		camera.setResolution(640, 480);
		// wheels 4 inches

		chooser = new SendableChooser<>();
		chooser.addDefault(CENTER.name(), CENTER);
		chooser.addObject(LEFT.name(), LEFT);
		chooser.addObject(RIGHT.name(), RIGHT);
		SmartDashboard.putData("Auto choices", chooser);

		System.out.println("We are actually running");
		visionCenter = 0;
		Runnable visionUpdater = () -> {
			while (true) {
				try {
					URL visionURL = new URL("http://172.21.6.159:5805/");

					BufferedReader in = new BufferedReader(new InputStreamReader(visionURL.openStream()));

					String inputLine = in.readLine();
					visionCenter = Float.parseFloat(inputLine);
					in.close();
					Thread.sleep(100);
				} catch (ConnectException e) {
//					System.out.println("No connection");
				} catch (Exception e) {
//					e.printStackTrace();
				}
			}
		};
		//new Thread(visionUpdater).start();
	}
	
	@Override
	public void robotPeriodic(){
		
	}
	
	@Override
	public void disabledPeriodic(){
		
	}
	
	
/****************
*	Autonomous
*****************/
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		lEncod.reset();
		rEncod.reset();
    }
	
	// MAIN AUTONOMOUS METHOD
	@Override
	public void autonomousPeriodic() {
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

	}

	private void autonomousCenter() {

		myRobot.setSafetyEnabled(false);
		// Tell what you do in autonomous middle here
		//Drive forward until certain distance from center and then put the gear on and back up. 
		if ( lEncod.get() < 5 * 12 * ticksPerInch) {
//			System.out.println("lencode: " + lEncod.get());
			
			myRobot.arcadeDrive(1, 0, true);
		}
		else {
			myRobot.arcadeDrive(0, 0, true);
		}
	}

	private void autonomousRight() {

	}

	/******************
	 * TELEOP
	 *****************/

	@Override
	public void teleopInit() {
		
	}
	
	@Override
	public void teleopPeriodic() {
		xboxDrive(); // For xbox controls
		gearDoor();
		lifter();
	}
	
	@Override
	public void disabledInit(){}
	
	// Lifter code
	private void lifter() {
		boolean liftStartButton = xbox.getRawButton(4);
		boolean liftDownButton = xbox.getRawButton(3);

		if (liftStartButton) {
			climber.set(-.9);
		} else if (liftDownButton) {
			climber.set(.3);
		} else {
			climber.set(0);
		}

	}

	// Button controls
	private void gearDoor() {
		boolean doorOpenButton = xbox.getRawButton(6);
		boolean doorCloseButton = xbox.getRawButton(5);

		if (doorOpenButton && openLimitSwitch.get() == false){ //&& closedLimitSwitch.get() == true) {
			door.set(.4);
		} else if (doorCloseButton && closedLimitSwitch.get() == false){ //&& openLimitSwitch.get() == true) {
			door.set(-.4);
		} else {
			door.set(0);
		}
	}

	// XBOX DRIVING CONTROLS
	private void xboxDrive() {
		double yAxis = xbox.getRawAxis(1) * .8;
		double xAxis = xbox.getRawAxis(0) * -.8;
		if (!(Math.abs(yAxis) < deadZone) || !(Math.abs(xAxis) < deadZone))
			myRobot.arcadeDrive(yAxis, xAxis, true);
	}
	
}
