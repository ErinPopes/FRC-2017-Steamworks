package steps;

import org.usfirst.frc.team1091.robot.GearGate;

public class CloseGate implements Step {
	private GearGate gearGate;

	public CloseGate(GearGate gearGate) {
		this.gearGate = gearGate;
	}

	@Override
	public boolean execute() {
		this.gearGate.closeDoor();
		return this.gearGate.isDoorClosed();
	}
}
