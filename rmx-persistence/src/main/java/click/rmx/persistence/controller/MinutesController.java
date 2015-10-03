package click.rmx.persistence.controller;


import click.rmx.persistence.model.Activity;
import click.rmx.persistence.model.Exercise;
import click.rmx.persistence.model.Goal;
import click.rmx.persistence.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;


@Controller
@SessionAttributes({
		"goal"//, "minutes", "activity"
})
public class MinutesController {

	@Autowired
	private ExerciseService exerciseService;

	@RequestMapping(value = "/addMinutes",  method = RequestMethod.GET)
	public String getMinutes(@ModelAttribute ("exercise") Exercise exercise) {
		return "addMinutes";
	}
	
	@RequestMapping(value = "/addMinutes",  method = RequestMethod.POST)
	public String addMinutes(@Valid @ModelAttribute ("exercise") Exercise exercise,
							 BindingResult result,
							 HttpSession session) {
		System.err.println(result);
		System.out.println("exercise: " + exercise.getMinutes());
		System.out.println("exercise activity: " + exercise.getActivity());
		
		if(result.hasErrors()) {
			return "addMinutes";
		}  else {
			Goal goal = (Goal) session.getAttribute("goal");
			exercise.setGoal(goal);
			exerciseService.save(exercise);
		}
		
		return "redirect:index.jsp";
	}
	
	@RequestMapping(value = "/activities", method = RequestMethod.GET)
	public @ResponseBody List<Activity> findAllActivities() {
		return exerciseService.findAllActivities();
	}
	
}
