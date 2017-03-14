package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearGate {

	//LeftSet
	// Limit Switch is pushed in when door is open. False when door is open.
	private DigitalInput rightOpenLimitSwitch; 
	// Limit Switch is pushed in when door is closed. False when door is closed.
	private DigitalInput rightClosedLimitSwitch; 
	
	
	private DigitalInput leftClosedLimitSwitch; 
	private DigitalInput leftOpenLimitSwitch;
	
	private Spark rightDoor;
	private Spark leftDoor;
	
	public GearGate() {
		this.rightOpenLimitSwitch = new DigitalInput(0);
		this.rightClosedLimitSwitch = new DigitalInput(1);
		
		this.leftOpenLimitSwitch = new DigitalInput(2);
		this.leftClosedLimitSwitch = new DigitalInput(3);

		
		this.rightDoor = new Spark (5);
		this.leftDoor = new Spark(4);
	}

	public void openDoor() {
		
		if (this.leftOpenLimitSwitch.get()) {
			this.leftDoor.set(0);
		} else {
			this.leftDoor.set(-0.9);
		}		
		
		if (this.rightOpenLimitSwitch.get()) {
			this.rightDoor.set(0);
		} else {
			this.rightDoor.set(-0.9);
		}
	}

	public void closeDoor() {
		if (this.leftClosedLimitSwitch.get()) {
			this.leftDoor.set(0);
		} else {
			this.leftDoor.set(0.9);
		}		
		
		if (this.rightClosedLimitSwitch.get()) {
			this.rightDoor.set(0);
		} else {
			this.rightDoor.set(0.9);
		}
	}
	

	public void stopDoor() {
		this.leftDoor.set(0);
		this.rightDoor.set(0);
	}

	public boolean isDoorOpen() {
		return this.rightOpenLimitSwitch.get();
	}

	public boolean isDoorClosed() {
		return this.rightClosedLimitSwitch.get();
	}
}
