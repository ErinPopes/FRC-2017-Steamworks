package steps;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public abstract class Drive implements Step {
	protected RobotDrive robotDrive;
	protected Encoder lEncoder;
	protected Encoder rEncoder;
	final double ticksPerInch = 360.0 / (4.0 * Math.PI);
	
	protected Drive(RobotDrive robotDrive, Encoder lEncoder, Encoder rEncoder) {
		this.robotDrive = robotDrive;
		this.lEncoder = lEncoder;
		this.rEncoder = rEncoder;
	}
}
