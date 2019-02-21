package com.uniovi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.uniovi.entities.Mark;
import com.uniovi.entities.User;
import com.uniovi.services.MarksService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.AddMarkFormValidator;

@Controller
public class MarksControllers {

	@Autowired // Inyectar el servicio
	private MarksService marksService;

	@Autowired
	private UsersService usersService;
	
	@Autowired
	private AddMarkFormValidator addMarkFormValidator;

	@RequestMapping("/mark/list")
	public String getList(Model model) {
		model.addAttribute("markList", marksService.getMarks());
		return "mark/list";
	}

	@RequestMapping(value = "/mark/add")
	public String getMark(Model model) {
		model.addAttribute("mark", new Mark());
		model.addAttribute("usersList", usersService.getUsers());
		return "mark/add";
	}

	@RequestMapping(value = "/mark/add", method = RequestMethod.POST)
	public String setMark(Model model, @Validated Mark mark, BindingResult result) {
		addMarkFormValidator.validate(mark, result);
		model.addAttribute("usersList", usersService.getUsers());
		if (result.hasErrors()) {
			return "mark/add";
		}
		marksService.addMark(mark);	
		return "redirect:/mark/list";
	}

	@RequestMapping("/mark/details/{id}")
	public String getDetail(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		return "mark/details";
	}

	@RequestMapping("/mark/delete/{id}")
	public String deleteMark(@PathVariable Long id) {
		marksService.deleteMark(id);
		return "redirect:/mark/list";
	}

	@RequestMapping(value = "/mark/edit/{id}")
	public String getEdit(Model model, @PathVariable Long id) {
		model.addAttribute("mark", marksService.getMark(id));
		model.addAttribute("usersList", usersService.getUsers());
		return "mark/edit";
	}

	@RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
	public String setEdit(Model model,@Validated Mark mark, BindingResult result) {
		addMarkFormValidator.validate(mark, result);
		User user = mark.getUser();
		model.addAttribute("usersList", usersService.getUsers());
		if (result.hasErrors()) {
			return "mark/edit/{id}";
		}
		mark.setUser(user);
		marksService.addMark(mark);			
		return "redirect:/mark/list";
	}

	@RequestMapping("/mark/list/update")
	public String updateList(Model model) {
		model.addAttribute("markList", marksService.getMarks());
		return "mark/list :: tableMarks";
	}

}
