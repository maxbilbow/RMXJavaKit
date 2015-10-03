package click.rmx.persistence.controller;

import click.rmx.persistence.model.Bomb;
import click.rmx.persistence.model.Bombs;
import click.rmx.persistence.service.BombService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes({
		"bomb", "bombs","fjwa.bombs"
})
public class BoomController {

	@Autowired
	private BombService bombService;

	private List<Bomb> bombs;

	@RequestMapping(value = "/boom") //The page name (.whatever - i.e. html)
	public String sayBoom(Model model) {
		updateModel(model, bombService.synchronize());
		return "boom"; //The jsp name?
	}

	private void updateModel(Model model, List<Bomb> bombs) {
		this.bombs = bombs;
		if (bombs == null || bombs.isEmpty())
			model.addAttribute("boom", "No bombs... safe!");
		else {
			model.addAttribute("boom", "Time is ticking...");
		}
		model.addAttribute("bombs", bombs);
		model.addAttribute("fjwa.bombs", (List<Bomb>) bombs);
		model.addAttribute("errors", bombService.getErrors());
	}
	@RequestMapping(value = "addBomb", method = RequestMethod.GET)
	public @ResponseBody List<Bomb> addBomb(Model model) {//, BindingResult result){
		Bomb bomb = Bombs.newBomb();
		bombService.save(bomb);
		return  this.bombs = bombService.synchronize();//"redirect:boom.html";
	}



	@RequestMapping(value = "removeAll", method = RequestMethod.GET)
	public @ResponseBody List<Bomb> removeAll(Model model){
		updateModel(model, bombService.removeAll());
		return this.bombs;
	}





	/**
	 * @warning Should be called in client-side js
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "updateBombs", method = RequestMethod.GET) //bombs?
	public @ResponseBody List<Bomb> updateBombs(Model model) {
//		updateModel(model, bombService.synchronize());
		return bombs;
	}

	@RequestMapping(value = "defuse", method = RequestMethod.GET) //bombs?
	public @ResponseBody List<Bomb> defuse(Model model) {
		updateModel(model, bombService.defuse());
		return this.bombs;
	}


}
