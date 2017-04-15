package steps;

import org.usfirst.frc.team1091.robot.GearGate;
import org.usfirst.frc.team1091.robot.ImageInfo;

public class OpenGate implements Step {

	private GearGate gearGate;
	private ImageInfo imageInfo;
	public OpenGate(GearGate gearGate, ImageInfo imageInfo) {
		
		this.gearGate = gearGate;
		this.imageInfo = imageInfo;
	}
	
	@Override
	public boolean execute() {
		if (!imageInfo.getHasSeen()){
			return true;
		}
		this.gearGate.openDoor();
		return this.gearGate.isDoorOpen();
	}
}