package click.rmx.persistence.model;


import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries( {
	@NamedQuery(
			name=Exercise.FIND_ALL_EXERCISES,
			query="Select e from Exercise e"
			)
})
public class Exercise implements IEntity{
	public static final String 
	FIND_ALL_EXERCISES = "findAllExercises",
	CLASS = "click.rmx.persistence.model.Exercise";
	
	@Id
	@GeneratedValue
	private Long id;

	@Range(min = 1, max = 120)
	@Column(name="MINUTES")
	private int minutes;

	@NotNull
	private String activity;

	@ManyToOne
	private Goal goal;
	
	public String getActivity() {
		return activity;
	}
	
	/**
	 * @return the goal
	 */
	public Goal getGoal() {
		return goal;
	}

	public Long getId() {
		return id;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	/**
	 * @param goal the goal to set
	 */
	public void setGoal(Goal goal) {
		this.goal = goal;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
}
