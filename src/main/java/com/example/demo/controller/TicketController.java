package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.SearchTicketDTO;
import com.example.demo.dto.TicketDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.TicketService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ticket")
public class TicketController {

	@Autowired
	TicketService ticketService;
	
	@Autowired
	DepartmentService departmentService;
	
	
	@GetMapping("/list-staff")
	public String listStaff(Model model) {		
		return "redirect:/ticket/search-staff";
	}
	
	@GetMapping("/search-staff")
	public String searchStaff(Model model,
							@ModelAttribute("searchTicketDTO") @Valid SearchTicketDTO searchTicketDTO,
							BindingResult bindingResult) {
		
		
		if (bindingResult.hasErrors()) {
			return "tickets-staff.html";
		}
		
		PageDTO<List<TicketDTO>> pageDTO = ticketService.search(searchTicketDTO);
		
		
		model.addAttribute("ticketList", pageDTO.getData());
		model.addAttribute("departmentList", pageDTO.getData());
		model.addAttribute("totalPage", pageDTO.getTotalPages());
		model.addAttribute("totalElements", pageDTO.getTotalElements());
		model.addAttribute("searchDTO", searchTicketDTO);
		return "tickets-staff.html";
	}
	
	
	@GetMapping("/list-user")
	public String listUser(Model model) {
		return "redirect:/ticket/search-user";
	}
	
	@GetMapping("/search-user")
	public String searchUser(Model model,
							@ModelAttribute("searchTicketDTO") @Valid SearchTicketDTO searchTicketDTO,
							BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "tickets-user.html";
		}
		
		PageDTO<List<DepartmentDTO>> pageDTOd = departmentService.search(new SearchDTO());
		PageDTO<List<TicketDTO>> pageDTO = ticketService.search(searchTicketDTO);
		
		model.addAttribute("ticketList", pageDTO.getData());
		model.addAttribute("departmentList", pageDTOd.getData());
		model.addAttribute("totalPage", pageDTO.getTotalPages());
		model.addAttribute("totalElements", pageDTO.getTotalElements());
		model.addAttribute("searchDTO", searchTicketDTO);
		return "tickets-user.html";
	}
	
	@GetMapping("/new")
	public String newTicket(Model model) {
		PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
		
		model.addAttribute("ticket", new TicketDTO());
		model.addAttribute("departmentList", pageDTO.getData());
		
		return "new-ticket.html";
	}
	
	
	@PostMapping("/new")
	public String newTicket(@ModelAttribute("ticket") @Valid TicketDTO ticketDTO,
							BindingResult bindingResult,
							Model model) throws IllegalStateException, IOException {
		
		if (bindingResult.hasErrors()) {
			
			PageDTO<List<DepartmentDTO>> pageDTO = departmentService.search(new SearchDTO());
			
			model.addAttribute("departmentList", pageDTO.getData());
			
			return "new-ticket.html";
		}
		
		ticketService.create(ticketDTO);
		
		return "redirect:/ticket/list-user";
	}
}
