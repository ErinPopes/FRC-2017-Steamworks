package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

public class Robot extends SampleRobot {

	private CameraServer server;

	// ReEnable soon
	// SerialPort.Port port = new Port(3);
	// SerialPort sonic = new SerialPort(19200, port);
	// SerialPort serialPort;

	private RobotDrive myRobot;
	private final Joystick xbox; // xbox controller
	final double deadZone = 0.02;
	final DriverStation.Alliance color;
	
	public Robot() {

		color = DriverStation.getInstance().getAlliance();
		System.out.print(color.name());
		myRobot = new RobotDrive(0, 1, 2, 3);
		myRobot.setExpiration(0.1);
		xbox = new Joystick(0);
	}

	// MAIN AUTONOMOUS METHOD

	public void autonomous() {
		System.out.println("Auto F.A.P for success (For arreal persoison)");
	}

	long lLastEncoderVal = 0;
	long rLastEncoderVal = 0;
	long lastTime = System.currentTimeMillis();

	// UPDATE CONTROLS AND SENSORS
	private void refresh() throws InterruptedException {
		long currentTime = System.currentTimeMillis();
		xboxDrive(); // For xbox controls
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
