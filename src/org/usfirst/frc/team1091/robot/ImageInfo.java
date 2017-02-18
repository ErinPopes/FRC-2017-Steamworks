package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ImageInfo {
	private float center = 0;
	private float distance = 0;

	public void update(String input) {
		if (input != null && !input.isEmpty()) {
			String[] split = input.split(",");
			if (split.length == 2) {
				center = Float.parseFloat(split[0]);
				distance = Float.parseFloat(split[1]);

				SmartDashboard.putNumber("Distance", this.distance);
				SmartDashboard.putString("input string", input);
			}
		}
	}

	public float getCenter() {
		return this.center;
	}

	public float getDistance() {
		return this.distance;
	}
}
