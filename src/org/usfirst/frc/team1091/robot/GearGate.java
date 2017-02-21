package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearGate {

	// Limit Switch is pushed in when door is open. False when door is open.
	private DigitalInput openLimitSwitch; 
	// Limit Switch is pushed in when door is closed. False when door is closed.
	private DigitalInput closedLimitSwitch; 
	private Spark door;

	public GearGate() {
		this.openLimitSwitch = new DigitalInput(1);
		this.closedLimitSwitch = new DigitalInput(0);
		this.door = new Spark(4);
	}

	public void openDoor() {
		if (this.openLimitSwitch.get()) {
			this.door.set(0);
		} else {
			this.door.set(-0.9);
		}
	}

	public void closeDoor() {
		if (this.closedLimitSwitch.get()) {
			this.door.set(0);
		} else {
			this.door.set(.9);
		}
	}

	public void stopDoor() {
		this.door.set(0);
	}

	public boolean isDoorOpen() {
		return this.openLimitSwitch.get();
	}

	public boolean isDoorClosed() {
		return this.closedLimitSwitch.get();
	}
}
