package steps;

import org.usfirst.frc.team1091.robot.GearGate;

public class CloseGate implements Step {
	private GearGate gearGate;

	public CloseGate(GearGate gearGate) {
		this.gearGate = gearGate;
	}

	@Override
	public boolean execute() {
		if (!this.gearGate.isDoorOpen()) {
			return true;
		} else {
			this.gearGate.closeDoor();
			return false;
		}
	}
}
