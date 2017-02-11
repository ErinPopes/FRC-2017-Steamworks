package steps;

public class StepExecutor {
	private Step[] steps;
	private int currentStep = 0;
	
	public StepExecutor(Step[] steps) {
		this.steps = steps;
		
	}
	public void execute() {
		if (currentStep >= this.steps.length)
			return;
		Step step = this.steps[currentStep];
		if (step.execute())
		{
			currentStep++;
		}
	}
}
