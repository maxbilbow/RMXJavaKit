package click.rmx.persistence.model;


public class GoalReport {
	public static final String CLASS = "click.rmx.persistence.model.GoalReport";
	private int goalMinutes;
	
	private int exerciseMinutes;
	
	private String exerciseActivity;

	
	public GoalReport(int goalMinutes, int exerciseMinutes, String exerciseActivity) {
		this.goalMinutes = goalMinutes;
		this.exerciseMinutes = exerciseMinutes;
		this.exerciseActivity = exerciseActivity;
	}
	
	public String getExerciseActivity() {
		return exerciseActivity;
	}

	public int getExerciseMinutes() {
		return exerciseMinutes;
	}

	public int getGoalMinutes() {
		return goalMinutes;
	}

	public void setExerciseActivity(String exerciseActivity) {
		this.exerciseActivity = exerciseActivity;
	}

	public void setExerciseMinutes(int exerciseMinutes) {
		this.exerciseMinutes = exerciseMinutes;
	}

	public void setGoalMinutes(int goalMinutes) {
		this.goalMinutes = goalMinutes;
	}
}
