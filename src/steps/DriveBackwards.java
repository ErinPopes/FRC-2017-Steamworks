package steps;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveBackwards extends Drive {
	private float distance;
	boolean hasExec = false;

	public DriveBackwards(RobotDrive robotDrive, Encoder lEncoder, Encoder rEncoder, float distance) {
		super(robotDrive, lEncoder, rEncoder);
		this.distance = distance; // in inches
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		if (!this.hasExec) {
			lEncoder.reset();
			rEncoder.reset();
			hasExec = true;
			robotDrive.setSafetyEnabled(false);
		} else {
			double tickDistance = this.distance * ticksPerInch;
			if (Math.abs(lEncoder.get()) < Math.abs(tickDistance)) {
				robotDrive.arcadeDrive(.7, 0, true);
			} else {
				robotDrive.arcadeDrive(0, 0, true);
				return true;
			}
		}
		return false;
	}
}
