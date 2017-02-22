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
	
	boolean hasSeen = false;
	
	@Override
	public boolean execute() {
		float turnpower = -visionScale * this.imageInfo.getCenter();
		if (turnpower > 0.6)
			turnpower = 0.6f;
		if (turnpower < -0.6)
			turnpower = -0.6f;
		if(this.imageInfo.getDistance() > 100 && hasSeen)
		{
			robotDrive.arcadeDrive(0,0);
			return true;
		}
		if (this.imageInfo.getDistance() > 30) {
			robotDrive.arcadeDrive(-.7, turnpower);
			return false;
		} else if (this.imageInfo.getDistance() > 20) {
			hasSeen=true;
			robotDrive.arcadeDrive(-.65, turnpower);
			return false;
		} else if (this.imageInfo.getDistance() > 5) {
			hasSeen=true;
			robotDrive.arcadeDrive(-.6, turnpower);
			return false;
		} else {
			robotDrive.arcadeDrive(0, 0);
			return true;
		}
	}

}
