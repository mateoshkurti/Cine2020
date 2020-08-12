package ca.sheridancollege.shkurtim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.shkurtim.beans.Cinema;
import ca.sheridancollege.shkurtim.database.DatabaseAccess;

@Controller
public class HomeController {

	@Lazy
	@Autowired
	private DatabaseAccess da;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String password) {

		da.addUser(username, password);
		Long userId = da.findUserAccount(username).getUserId();
		da.addRole(userId, Long.valueOf(1));
		da.addRole(userId, Long.valueOf(2));
		return "redirect:/";
	}

	@GetMapping("/secureSales")
	public String secureSales(Model model, Authentication authentication) {

		String username = authentication.getName();
		Long userId = da.findUserAccount(username).getUserId();

		List<Cinema> cinemaList = da.getCinemaList();
		model.addAttribute("cinemaList", cinemaList);
		
		return "/secureSales/index";
	}

	@GetMapping("/secureAdmin")
	public String secureIndex(Model model, Authentication authentication) {

		String username = authentication.getName();
		Long userId = da.findUserAccount(username).getUserId();

		return "/secureAdmin/index";
	}

	@PostMapping("/secureAdmin/submit")
	public String submit(Model model, Authentication authentication, @RequestParam String[] features,
			@RequestParam String cinemaName, @RequestParam String cinemaStage, @RequestParam String movie,
			@RequestParam int availableSeats) {

		da.insertCinema(cinemaName, cinemaStage, availableSeats, movie);

		if (features.length > 0) {
			String a = "properlySanitized";
			String b = "markedGround";
			String c = "markedSeats";
			String d = "doorSigns";

			Boolean result = check(features, a);
			
			if (result) 
				da.properlySanitized(cinemaName, cinemaStage, availableSeats, movie, "Yes");
			else
				da.properlySanitized(cinemaName, cinemaStage, availableSeats, movie, "No");
			
			result = check(features, b);
			if (result) 
				da.markedGround(cinemaName, cinemaStage, availableSeats, movie, "Yes");
			else
				da.markedGround(cinemaName, cinemaStage, availableSeats, movie, "No");
			
			
			result = check(features, c);
			if (result) 
				da.markedSeats(cinemaName, cinemaStage, availableSeats, movie, "Yes");
			else
				da.markedSeats(cinemaName, cinemaStage, availableSeats, movie, "No");
			
			
			result = check(features, d);
			if (result) 
				da.doorSigns(cinemaName, cinemaStage, availableSeats, movie, "Yes");
			else
				da.doorSigns(cinemaName, cinemaStage, availableSeats, movie, "No");
			
		}
		return "/secureAdmin/index";
	}

	@GetMapping("/secureAdmin/cinemaSub")
	public String cinemaSub(Model model, Authentication authentication) {

		String username = authentication.getName();
		Long userId = da.findUserAccount(username).getUserId();
		model.addAttribute("cinema", new Cinema());

		return "/secureAdmin/cinemaSub";
	}

	@GetMapping("/secureAdmin/cinemaList")
	public String cinemaList(Model model, Authentication authentication) {

		String username = authentication.getName();
		Long userId = da.findUserAccount(username).getUserId();

		List<Cinema> cinemaList = da.getCinemaList();
		model.addAttribute("cinemaList", cinemaList);
		return "/secureAdmin/cinemaList";
	}
	
	@GetMapping("/secureAdmin/deleteAppointment/{cinemaID}")
	public String deleteAppointment(Model model, @PathVariable Long cinemaID) {
		da.deleteCinema(cinemaID);
		model.addAttribute("cinema", new Cinema());
		model.addAttribute("cinemaList", da.getCinemaList());
		return "/secureAdmin/cinemaList";

	}
	
	
	
	

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}

	public boolean check(String[] features, String feature) {
		
		for (String f : features) {
			if (feature.equals(f)) {
				return true;
			}
		}
		return false;
	}
}
