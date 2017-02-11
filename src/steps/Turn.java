package steps;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;

public class Turn implements Step {

	private RobotDrive robotDrive;
	private Encoder lEncoder;
	private Encoder rEncoder;
	private float turnInDegrees;

	boolean hasExec = false;

	public Turn(RobotDrive robotDrive, Encoder lEncoder, Encoder rEncoder, float turnInDegrees) {
		this.robotDrive = robotDrive;
		this.lEncoder = lEncoder;
		this.rEncoder = rEncoder;
		this.turnInDegrees = turnInDegrees;
	}

	@Override
	public boolean execute() {

		return true;
		// TODO: actually turn instead of being just a poser
		
//		if (!this.hasExec) {
//			lEncoder.reset();
//			rEncoder.reset();
//			hasExec = true;
//		} else {
//			if (lEncoder.get() < this.distance * 12 * ticksPerInch) {
//				robotDrive.arcadeDrive(0, 0, true);
//			} else {
//				robotDrive.arcadeDrive(0, 0, true);
//				return true;
//			}
//		}
//		return false;
	}

}
