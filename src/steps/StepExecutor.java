package steps;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StepExecutor {
	private ArrayList<Step> steps;
	private int currentStep = 0;
	
	public StepExecutor(ArrayList<Step> steps) {
		this.steps = steps;
	}
	public void execute() {
		if (currentStep >= this.steps.size()) {
			SmartDashboard.putString("Current Step", "DONE!");
			return;
		}
		Step step = this.steps.get(currentStep);
		if (step.execute())
		{
			currentStep++;
		}
		else {
			SmartDashboard.putString("Current Step", step.getClass().toString());
		}	
	}
}
