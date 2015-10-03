package click.rmx.persistence.repository;

import click.rmx.persistence.model.Goal;
import click.rmx.persistence.model.GoalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("goalRepository")
public interface GoalRepository  extends JpaRepository<Goal, Long> {

	@Query("Select new " + GoalReport.CLASS
					+ "(g.minutes, e.minutes, e.activity) "
					+ "from Goal g, Exercise e where g.id = e.goal.id"
	)
	 List<GoalReport> findGoalReports();



}
