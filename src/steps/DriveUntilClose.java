package steps;

import org.usfirst.frc.team1091.robot.ImageInfo;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveUntilClose extends Drive {

	private ImageInfo imageInfo;
	final static float visionScale = 5f;

	public DriveUntilClose(RobotDrive robotDrive, Encoder lEncoder, Encoder rEncoder, ImageInfo imageInfo) {
		super(robotDrive, lEncoder, rEncoder);
		this.imageInfo = imageInfo;
	}

	@Override
	public boolean execute() {
		float turnpower = -visionScale * this.imageInfo.getCenter();
		if (turnpower > 0.6)
			turnpower = 0.6f;
		if (turnpower < -0.6)
			turnpower = -0.6f;

		if (this.imageInfo.getDistance() > 15) {
			robotDrive.arcadeDrive(-.7, turnpower);
			return false;
		} else if (this.imageInfo.getDistance() > 10) {
			robotDrive.arcadeDrive(-.5, turnpower);
			return false;
		} else if (this.imageInfo.getDistance() > 5) {
			robotDrive.arcadeDrive(-.2, turnpower);
			return false;
		} else {
			return true;
		}
	}

}
