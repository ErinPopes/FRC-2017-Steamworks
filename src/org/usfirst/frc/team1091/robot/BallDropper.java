package org.usfirst.frc.team1091.robot;

import edu.wpi.first.wpilibj.Spark;

public class BallDropper {
	private Spark spark;
	
	public BallDropper(){
		this.spark = new Spark(6);
	}
	
	public void drop(){
		this.spark.set(.5);
	}
	
	public void stop() {
		this.spark.set(0);
	}
}
