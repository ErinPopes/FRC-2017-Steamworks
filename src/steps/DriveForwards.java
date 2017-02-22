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
		this.distance = distance; // in inches
		execute();
	}

	private double maximal = 0.9;
	private double minimal = 0.6;

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		if (!this.hasExec) {
			lEncoder.reset();
			rEncoder.reset();
			hasExec = true;
			robotDrive.setSafetyEnabled(false);
		} else {
			double targetDistance = this.distance * ticksPerInch;
			double currentDistance = Math.abs(lEncoder.get());
			if (currentDistance < targetDistance) {

				double rampUp = 12 * ticksPerInch;
				double rampDown = 24 * ticksPerInch;
				if (currentDistance > targetDistance - rampDown) {

					double d = (targetDistance - currentDistance) / rampDown;

					robotDrive.arcadeDrive(-lerp(minimal, maximal, d), 0.25, true);

				} else if (currentDistance < rampUp) {

					double d = currentDistance / rampUp;
					robotDrive.arcadeDrive(-lerp(minimal, maximal,d), 0.25, true);

				} else {
					robotDrive.arcadeDrive(-maximal, 0.25, true);
				}

			} else {
				robotDrive.arcadeDrive(0, 0, true);
				return true;
			}
		}
		return false;
	}

	double lerp(double start, double end, double t) {
		return (1 - t) * start + t * end;
	}
}
