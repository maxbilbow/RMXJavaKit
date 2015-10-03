package click.rmx.persistence.controller;

import click.rmx.persistence.model.Goal;
import click.rmx.persistence.model.GoalReport;
import click.rmx.persistence.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Controller
@SessionAttributes("goal")
public class GoalController {
	
	@Autowired
	private GoalService goalService;

	@RequestMapping(value = "addGoal", method = RequestMethod.GET)
	public String addGoal(Model model, HttpSession session) {
		//Goal goal = new Goal();
		Goal goal = (Goal) session.getAttribute("goal");

		if (goal == null) {
			goal = new Goal();
			goal.setMinutes(10);
		}

		model.addAttribute("goal", goal);
		
		return "addGoal";
	}

    @PreAuthorize("hasRole('ROLE_ADMIN') and hasPermission(#goal, 'createGoal')")
	@RequestMapping(value = "addGoal", method = RequestMethod.POST)
	public String updateGoal(@Valid @ModelAttribute("goal") Goal goal,
							 BindingResult result) {
		
		System.out.println("result has errors: " + result.hasErrors());
		
		System.out.println("Goal set: " + goal.getMinutes());
		
		if(result.hasErrors()) {
			return "addGoal";
		} else {
			goalService.save(goal);
		}
		
		return "redirect:index.jsp";
	}
	
	@RequestMapping(value = "getGoals", method = RequestMethod.GET)
	public String getGoals(Model model) {
		Collection<Goal> goals = goalService.findAllEntities();
		model.addAttribute("goals", goals);
		return "getGoals";
	}
	
	@RequestMapping(value = "getGoalReports", method = RequestMethod.GET)
	public String getGoalReports(Model model) {
		List<GoalReport> goalReports = goalService.findAllGoalReports();
		model.addAttribute("goalReports", goalReports);
		return "getGoalReports";
	}


	@RequestMapping(value = "/GoalReports", method = RequestMethod.GET)
	public @ResponseBody List<GoalReport> GoalReports() {
		return goalService.findAllGoalReports();
	}

}
