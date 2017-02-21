package steps;

import org.usfirst.frc.team1091.robot.ImageInfo;
import org.usfirst.frc.team1091.robot.Robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class TurnToVisionCenter implements Step {

	private ImageInfo imageInfo;
	private RobotDrive robotDrive;
	final double ticksPerInch = 360.0 / (4.0 * Math.PI);

	final static float visionScale = 5f;

	public TurnToVisionCenter(ImageInfo robot, RobotDrive robotDrive) {
		this.imageInfo = robot;
		this.robotDrive = robotDrive;
}

	@Override
	public boolean execute() {
		float turnpower = visionScale * imageInfo.getCenter();
		if (turnpower > 0.6)
			turnpower = 0.6f;
		if (turnpower < -0.6)
			turnpower = -0.6f;
		robotDrive.arcadeDrive(0, -turnpower);
		
		return Math.abs(imageInfo.getCenter()) < .1;
	}

}