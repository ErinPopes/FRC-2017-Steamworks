package steps;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class DriveForwards implements Step {
	private RobotDrive robotDrive;
	private Encoder lEncoder;
	private Encoder rEncoder;
	private float distance;
	boolean hasExec = false;
	final double ticksPerInch = 360.0 / (4.0 * Math.PI);

	public DriveForwards(RobotDrive robotDrive, Encoder lEncoder, Encoder rEncoder, float distance) {
		this.robotDrive = robotDrive;
		this.lEncoder = lEncoder;
		this.rEncoder = rEncoder;
		this.distance = distance; //in inches
		execute();
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
				robotDrive.arcadeDrive(-.9, 0.3, true); //Trust in the magic number
			} else {
				robotDrive.arcadeDrive(0, 0, true);
				return true;
			}
		}
		return false;
	}
}
