package click.rmx.persistence.service;

import click.rmx.debug.RMXException;
import click.rmx.persistence.model.Goal;
import click.rmx.persistence.model.GoalReport;
import click.rmx.persistence.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("goalService")
public class GoalServiceImpl extends AbstractEntityService<Goal> implements GoalService {

	
	@Autowired
	private GoalRepository goalRepository;
	
	@Override
	protected JpaRepository<Goal, Long> repository() {
		return goalRepository;
	}

	@Override
	@Transactional
	public List<GoalReport> findAllGoalReports() {
		try { 
			return goalRepository.findGoalReports();

		} catch (Exception e) {
			this.addError(RMXException.unexpected(e));
		}
		return null;
	}
	


}
