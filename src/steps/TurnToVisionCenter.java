package steps;

import org.usfirst.frc.team1091.robot.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class TurnToVisionCenter implements Step {

	private Robot robot;
	private RobotDrive robotDrive;
	final double ticksPerInch = 360.0 / (4.0 * Math.PI);

	final static float visionScale = 5f;

	public TurnToVisionCenter(Robot robot, RobotDrive robotDrive) {
		this.robot = robot;
		this.robotDrive = robotDrive;
}

	@Override
	public boolean execute() {
		float turnpower = visionScale * robot.visionCenter;
		if (turnpower > 1)
			turnpower = 1;
		if (turnpower < -1)
			turnpower = -1;
		robotDrive.arcadeDrive(0, turnpower);
		return false;
	}

}