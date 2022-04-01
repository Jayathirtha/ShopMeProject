package com.shopme.admin.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserServices userService;

	@GetMapping("/Users")
	public String listAllUsers(Model model) {

		List<User> usersList = userService.listAllUsers();
		model.addAttribute("usersList", usersList);
		return "users";
	}

	@GetMapping("/Users/new")
	public String addNewUser(Model model) {
		User user = new User();
		user.setEnabled(true);
		List<Role> roles = userService.listAllRoles();
		model.addAttribute("user", user);
		model.addAttribute("pageTitle", "Create new User");
		model.addAttribute("roles", roles);
		return "user_form";
	}

	@PostMapping("/Users/save")
	public String saveNewUser(User user, RedirectAttributes redirectAttirbute) {
		
	
		
		userService.saveUser(user);
		redirectAttirbute.addFlashAttribute("message", "the User has been added sucessfully. ");

		return "redirect:/Users";
	}

	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {
		try {
			User user = userService.get(id);
			List<Role> roles = userService.listAllRoles();
			model.addAttribute("user", user); // matches with th:object="${user} in users_form
			model.addAttribute("pageTitle", "Edit user ID: " + id);
			model.addAttribute("roles", roles);
			return "user_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", "Cannot find the user with user id " + id);
			return "redirect:/Users";
		}

	}

}
