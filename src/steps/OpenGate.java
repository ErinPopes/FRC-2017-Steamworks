package steps;

import org.usfirst.frc.team1091.robot.GearGate;

public class OpenGate implements Step {

	private GearGate gearGate;
	
	public OpenGate(GearGate gearGate) {
		this.gearGate = gearGate;
	}
	
	@Override
	public boolean execute() {
		if (this.gearGate.isDoorOpen()) {
			return true;
		}
		else {
			this.gearGate.openDoor();
			return false;
		}
	}

}
