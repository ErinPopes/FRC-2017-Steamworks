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
			float lMotorPower = -.6f;
			float rMotorPower = -.6f;
			int encoderDifference = rEncoder.get() - lEncoder.get();

			System.out.println("Left Encoder:");
			System.out.println(lEncoder.get());
			System.out.println("Right Encoder:");
			System.out.println(rEncoder.get());

			// if (Math.abs(lEncoder.get()) < Math.abs(tickDistance)) {
			// lMotorPower = -.6f;
			// }
			//
			// if (Math.abs(rEncoder.get()) < Math.abs(tickDistance)) {
			// rMotorPower = -.6f;
			// }
			
			System.out.println(encoderDifference);
			if (encoderDifference > 1) { // IF RIGHT > LEFT
				rMotorPower = lMotorPower + (Math.abs(encoderDifference) / 200);
				lMotorPower = 0;
				System.out.println("Drifting");
			} else if (encoderDifference < -1) { //IF LEFT > RIGHT
				lMotorPower = rMotorPower + (Math.abs(encoderDifference) / 200);
				rMotorPower = 0;
				System.out.println("Drifting the other way");
			}

			else {
				System.out.println("Not drifting");
			}
			
			if (Math.abs(lEncoder.get()) > Math.abs(tickDistance) || Math.abs(rEncoder.get()) > Math.abs(tickDistance)) {
				robotDrive.tankDrive(0, 0);
				return true;
			}
			System.out.println("tickDistance:");
			System.out.println(tickDistance);
			
			
			robotDrive.tankDrive(lMotorPower, rMotorPower);

//			if (lMotorPower == 0 && rMotorPower == 0) {
//				return true;
//			}
		}
		return false;
	}
}
