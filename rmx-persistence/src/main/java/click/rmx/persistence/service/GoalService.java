package click.rmx.persistence.service;


import click.rmx.persistence.model.Goal;
import click.rmx.persistence.model.GoalReport;

import java.util.List;

public interface GoalService extends EntityService<Goal> {

	List<GoalReport> findAllGoalReports();
	
}
