package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import steps.DriveForwards;
import steps.Step;
import steps.StepExecutor;
import steps.Turn;
import steps.TurnToVisionCenter;
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
	StepExecutor stepExecutor;

	private Encoder lEncod, rEncod; // 20 per rotation on the encoder, 360 per
									// rotation on the wheel

	SendableChooser<StartingPosition> chooser;
	StartingPosition autoSelected;

	public float visionCenter;
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

		lEncod = new Encoder(6, 7, true);
		rEncod = new Encoder(4, 5);

		climber = new Spark(5);

		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		camera.setBrightness(255);
		camera.setExposureManual(50);
		camera.setWhiteBalanceManual(50);
		camera.enumerateProperties();
		// wheels 4 inches

		chooser = new SendableChooser<>();
		chooser.addDefault(CENTER.name(), CENTER);
		for (StartingPosition p : StartingPosition.values()) {
			chooser.addObject(p.name(), p);
		}

		// chooser.addDefault(CENTER.name(), CENTER);
		// chooser.addObject(LEFT.name(), LEFT);
		// chooser.addObject(RIGHT.name(), RIGHT);
		SmartDashboard.putData("Auto choices", chooser);

		System.out.println("We are actually running");
		visionCenter = 0;
		Runnable visionUpdater = () -> {
			while (true) {
				try {
					URL visionURL = new URL("http://10.10.91.34:5805/");

					BufferedReader in = new BufferedReader(new InputStreamReader(visionURL.openStream()));

					String inputLine = in.readLine();
					visionCenter = Float.parseFloat(inputLine);
					in.close();
					Thread.sleep(100);
				} catch (ConnectException e) {
					// System.out.println("No connection");
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		};
		new Thread(visionUpdater).start();
	}

	@Override
	public void robotPeriodic() {

	}

	@Override
	public void disabledPeriodic() {

	}

	/****************
	 * Autonomous
	 *****************/
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		Step[] steps = null;

		switch (autoSelected) {
		case AUTOCENTER:
			steps = new Step[] { new TurnToVisionCenter(this, myRobot) };
			break;
		case RIGHT:
			steps = new Step[] { new Turn(myRobot, lEncod, rEncod, -12), };
			break;

		case LEFT:
			steps = new Step[] { new Turn(myRobot, lEncod, rEncod, 24) };
			break;

		case CENTER: // CENTER
			steps = new Step[] { new DriveForwards(myRobot, lEncod, rEncod, 1) };
			break;

		case POS1_OR_3:
			steps = new Step[] { new DriveForwards(myRobot, lEncod, rEncod, 100) };
			break;

		case POS2:
			steps = new Step[] { new DriveForwards(myRobot, lEncod, rEncod, 100) };
		default:
			break;
		}
		this.stepExecutor = new StepExecutor(steps);

	}

	// MAIN AUTONOMOUS METHOD
	@Override
	public void autonomousPeriodic() {
		stepExecutor.execute();
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
	public void disabledInit() {
	}

	// Lifter code
	private void lifter() {
		boolean liftStartButton = xbox.getRawButton(4);
		boolean liftDownButton = xbox.getRawButton(3);

		if (liftStartButton) {
			climber.set(-.3);
		} else if (liftDownButton) {
			climber.set(.9);
		} else {
			climber.set(0);
		}

	}

	// Button controls
	private void gearDoor() {
		boolean doorOpenButton = xbox.getRawButton(6);
		boolean doorCloseButton = xbox.getRawButton(5);

		if (doorOpenButton && openLimitSwitch.get() == false) {
			door.set(.4);
		} else if (doorCloseButton && closedLimitSwitch.get() == false) {
			door.set(-.4);
		} else {
			door.set(0);
		}
	}

	float currentPower = 0;
	float maxAcc = 10;
	long lastTime = 0;

	// XBOX DRIVING CONTROLS
	private void xboxDrive() {
		long now = System.currentTimeMillis();
		if (lastTime == 0)
			lastTime = now;

		// Trevor suggested speed
		double yAxis = xbox.getRawAxis(1) * .75;
		double xAxis = xbox.getRawAxis(0) * -.6;

		float timeDiffInSeconds = (now - lastTime) / 1000f;

		lastTime = now;

		if (yAxis > currentPower) {
			currentPower = (float) Math.max(currentPower + (maxAcc * timeDiffInSeconds), yAxis);
		} else if (yAxis < currentPower) {
			currentPower = (float) Math.min(currentPower - (maxAcc * timeDiffInSeconds), yAxis);
		}

		// if ((Math.abs(yAxis) > deadZone) || (Math.abs(xAxis) > deadZone))
		myRobot.arcadeDrive(currentPower, xAxis, true);
	}

}
