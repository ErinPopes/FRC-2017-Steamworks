package steps;

import org.usfirst.frc.team1091.robot.GearGate;

public class OpenGate implements Step {

	private GearGate gearGate;
	
	public OpenGate(GearGate gearGate) {
		this.gearGate = gearGate;
	}
	
	@Override
	public boolean execute() {
		this.gearGate.openDoor();
		return this.gearGate.isDoorOpen();
	}
}