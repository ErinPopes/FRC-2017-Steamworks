package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GearGate {

	private DigitalInput openLimitSwitch; // Limit Switch is pushed in when door is
	// open. False when door is open.
	private DigitalInput closedLimitSwitch; // Limit Switch is pushed in when door is
		// closed. False when door is closed.
	private Spark door;
	
	private boolean doorOpen;

	public GearGate() {
		this.openLimitSwitch = new DigitalInput(0);
		this.closedLimitSwitch = new DigitalInput(1);
		this.door = new Spark(4);
	}
	
	public boolean closedSwitch(){
		return closedLimitSwitch.get();
	}
	
	public boolean openSwitch(){
		return openLimitSwitch.get();
	}
	
	public void openDoor() {
		if (this.openLimitSwitch.get()) {
			this.doorOpen = true;
			SmartDashboard.putBoolean("Door Closed", doorOpen);
		}
		else {
			this.door.set(0.9);
		}
	}
	
	public void closeDoor() {
		if (this.closedLimitSwitch.get()) {
			this.doorOpen = false;
			SmartDashboard.putBoolean("Door Closed", doorOpen);
		}
		else {
			this.door.set(-.9);
		}
	}
	
	public void stopDoor() {
		this.door.set(0);
	}
	
	public boolean isDoorOpen() {
		return this.doorOpen;
	}

	public boolean isDoorClosed() {
		// TODO Auto-generated method stub
		return false;
	}
}
