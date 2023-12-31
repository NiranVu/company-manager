package com.example.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

//import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	DepartmentService departmentService;
	
	@GetMapping("/user/list")
	public String list(//HttpServletRequest req,
						Model model) {
		List<UserDTO> userDTOs = userService.getAll();
//		req.setAttribute("userList", users);
		model.addAttribute("userList", userDTOs);
		
		model.addAttribute("searchDTO", new SearchDTO());
		
		return "users.html";
	}
	
	@GetMapping("/user/search")
	public String search(Model model,
							@ModelAttribute("searchDTO") @Valid SearchDTO searchDTO,
							BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "users.html";
		}
		
		PageDTO<List<UserDTO>> pageUser = userService.searchName(searchDTO);
		
		
		model.addAttribute("userList", pageUser.getData());
		model.addAttribute("totalPage", pageUser.getTotalPages());
		model.addAttribute("totalElements", pageUser.getTotalElements());
		model.addAttribute("searchDTO", searchDTO);
		return "users.html";
	}
	
	@GetMapping("/user/new")
	public String newUser(Model model) {
		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
		
		model.addAttribute("user", new UserDTO());
		model.addAttribute("departmentList", pageDTO.getData());
		
		return "new-user.html";
	}
	
	@PostMapping("/user/new")
	public String newUser(@ModelAttribute("user") @Valid UserDTO userDTO,
							BindingResult bindingResult,
							Model model) throws Exception {
		
		if (bindingResult.hasErrors()) {
			
			PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
			
			model.addAttribute("departmentList", pageDTO.getData());
			
			return "new-user.html";
		}
		
		if(!userDTO.getFile().isEmpty()) {
			String filename = userDTO.getFile().getOriginalFilename();
			File saveFile = new File("D:/Image/" + filename);
			userDTO.getFile().transferTo(saveFile);
			userDTO.setAvatarURL(filename);
		}
		
		userService.create(userDTO);
		
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/delete")
	public String delete(@RequestParam ("id") int id) {
		userService.delete(id);
		return "redirect:/user/list";
	}
	
	@GetMapping("/user/download")
	public void download(@RequestParam("filename") String filename, HttpServletResponse resp) throws Exception {
		File file = new File("D:/Iamge/" + filename);
		Files.copy(file.toPath(), resp.getOutputStream());
	}
	
	
	@GetMapping("/user/edit")
	public String edit(@RequestParam ("id") int id,
							Model model) {
		UserDTO userDTO = userService.getById(id);
		
		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
		
		model.addAttribute("user", userDTO);
		model.addAttribute("departmentList", pageDTO.getData());
		return "edit-user.html";
	}
	
	@PostMapping("/user/edit")
	public String edit(@ModelAttribute("user") @Valid UserDTO userDTO, 
						BindingResult bindingResult,
						Model model) throws Exception {
		
		if (bindingResult.hasErrors()) {
			
			PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
			
			model.addAttribute("departmentList", pageDTO.getData());
			
			return "edit-user.html";
		}
		
		/*
		 * if(!userDTO.getFile().isEmpty()) { String filename =
		 * userDTO.getFile().getOriginalFilename(); File saveFile = new File("D:/Image/"
		 * + filename); userDTO.getFile().transferTo(saveFile);
		 * userDTO.setAvatarURL(filename); }
		 */
		
		
		
		userService.update(userDTO);
		
		return "redirect:/user/list";
	}
}