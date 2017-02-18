package steps;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StepExecutor {
	private Step[] steps;
	private int currentStep = 0;
	
	public StepExecutor(Step[] steps) {
		this.steps = steps;
		
	}
	public void execute() {
		if (currentStep >= this.steps.length) {
			SmartDashboard.putString("Current Step", "DONE!");
			return;
		}
		Step step = this.steps[currentStep];
		if (step.execute())
		{
			currentStep++;
		}
		else {
			SmartDashboard.putString("Current Step", step.getClass().toString());
		}	
	}
}
