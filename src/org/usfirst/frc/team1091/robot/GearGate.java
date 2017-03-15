package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearGate {

	private DigitalInput rightOpenLimitSwitch;
	private DigitalInput rightClosedLimitSwitch;

	private DigitalInput leftClosedLimitSwitch;
	private DigitalInput leftOpenLimitSwitch;

	private Spark rightDoor;
	private Spark leftDoor;

	public GearGate() {
		rightOpenLimitSwitch = new DigitalInput(2);
		rightClosedLimitSwitch = new DigitalInput(3);

		leftOpenLimitSwitch = new DigitalInput(1);
		leftClosedLimitSwitch = new DigitalInput(0);

		rightDoor = new Spark(6);
		leftDoor = new Spark(5);
	}

	public void print(){
		SmartDashboard.putBoolean("Left Open", leftOpenLimitSwitch.get());
		SmartDashboard.putBoolean("Left Closed", leftClosedLimitSwitch.get());
		SmartDashboard.putBoolean("Right Open", rightOpenLimitSwitch.get());
		SmartDashboard.putBoolean("Right Closed", !rightClosedLimitSwitch.get());
	}

	public void openDoor() {

		if (leftOpenLimitSwitch.get()) {
			leftDoor.set(0);
		} else {
			leftDoor.set(0.3);
		}

		 if (rightOpenLimitSwitch.get()) {
			 rightDoor.set(0);
		 } else {
			 rightDoor.set(-0.3);
		 }
	}

	public void closeDoor() {
		if (leftClosedLimitSwitch.get()) {
			leftDoor.set(0);
		} else {
			leftDoor.set(-0.3);
		}

		 if (!rightClosedLimitSwitch.get()) {
			 rightDoor.set(0);
		 } else {
			 rightDoor.set(0.3);
		 }
	}

	public void stopDoor() {
		leftDoor.set(0);
		rightDoor.set(0);
	}

	public boolean isDoorOpen() {
		return rightOpenLimitSwitch.get() && leftOpenLimitSwitch.get();
	}

	public boolean isDoorClosed() {
		return rightClosedLimitSwitch.get() && !leftClosedLimitSwitch.get();
	}
}
