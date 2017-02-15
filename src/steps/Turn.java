package steps;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class Turn implements Step {

	private RobotDrive robotDrive;
	private Encoder lEncoder;
	private Encoder rEncoder;
	private double turnInInches;
	final double ticksPerInch = 360.0 / (4.0 * Math.PI);

	boolean hasExec = false;

	public Turn(RobotDrive robotDrive, Encoder lEncoder, Encoder rEncoder, double turnInInches) {
		this.robotDrive = robotDrive;
		this.lEncoder = lEncoder;
		this.rEncoder = rEncoder;
		this.turnInInches = turnInInches;
	}

	@Override
	public boolean execute() {

		if (!this.hasExec) {
			lEncoder.reset();
			rEncoder.reset();
			hasExec = true;
		} else {
			if (this.turnInInches < 0) {
				if (Math.abs(rEncoder.get()) < Math.abs(this.turnInInches * ticksPerInch)) { //RIGHT
					robotDrive.arcadeDrive(0, -0.5, true);
				} else {
					robotDrive.arcadeDrive(0, 0, true);
					return true;
				}
			} else {
				if (Math.abs(lEncoder.get()) < Math.abs(this.turnInInches * ticksPerInch)) { //LEFT
					robotDrive.arcadeDrive(0, 0.65, true);
				} else {
					robotDrive.arcadeDrive(0, 0, true);
					return true;
				}
			}
		}
		return false;
	}

}