package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchTicketDTO;
import com.example.demo.dto.TicketDTO;
import com.example.demo.entity.Ticket;
import com.example.demo.repository.TicketRepo;

public interface TicketService {

	void create(TicketDTO ticketDTO);

	void update(TicketDTO ticketDTO);

	void delete(int id);

	TicketDTO getById(int id);

	PageDTO<List<TicketDTO>> search(SearchTicketDTO searchTicketDTO);
}

@Service
class TicketServiceImpl implements TicketService {

	@Autowired
	TicketRepo ticketRepo;

	@Override
	public void create(TicketDTO ticketDTO) {
		Ticket ticket = new ModelMapper().map(ticketDTO, Ticket.class);
		ticketRepo.save(ticket);

	}

	@Override
	public void update(TicketDTO ticketDTO) {
		Ticket ticket = ticketRepo.findById(ticketDTO.getTicketId()).orElse(null);

		if (ticket != null) {
			ticket.setClientName(ticketDTO.getClientName());
			

			ticketRepo.save(ticket);
		}

	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		ticketRepo.deleteById(id);
	}

	private TicketDTO convert(Ticket ticket) {

		TicketDTO ticketDTO = new ModelMapper().map(ticket, TicketDTO.class);
		return ticketDTO;
	}

	@Override
	public TicketDTO getById(int id) {
		Ticket ticket = ticketRepo.findById(id).orElse(null);

		if (ticket != null) {

			return convert(ticket);

		}
		return null;
	}

	@Override
	public PageDTO<List<TicketDTO>> search(SearchTicketDTO searchTicketDTO) {
		Sort sortBy = Sort.by("createdAt").ascending();

		if (org.springframework.util.StringUtils.hasText(searchTicketDTO.getSortedField())) {
			sortBy = Sort.by(searchTicketDTO.getSortedField()).ascending();
		}

		if (searchTicketDTO.getCurrentPage() == null) {
			searchTicketDTO.setCurrentPage(0);
		}

		if (searchTicketDTO.getSize() == null) {
			searchTicketDTO.setSize(5);
		}

		PageRequest pageRequest = PageRequest.of(searchTicketDTO.getCurrentPage(), searchTicketDTO.getSize(), sortBy);

		Page<Ticket> page = ticketRepo.findAll(pageRequest);
		
		if (searchTicketDTO.getKeyword() != null && (searchTicketDTO.getKeyword().length() == 10 || searchTicketDTO.getKeyword().length() == 11)) { 
			  page = ticketRepo.searchByClientPhone(searchTicketDTO.getKeyword(), pageRequest); 
		}
		if (searchTicketDTO.getKeyword() != null && searchTicketDTO.getKeyword().length() <= 5) {
			page = ticketRepo.searchById(Integer.parseInt(searchTicketDTO.getKeyword()), pageRequest); 
		}

		if (searchTicketDTO.getStart() != null && searchTicketDTO.getEnd() != null) {
			page = ticketRepo.searchByDate(searchTicketDTO.getStart(), searchTicketDTO.getEnd(), pageRequest);
		}
		if (searchTicketDTO.getDepartmentId() != null) {
			page = ticketRepo.searchByDepartmentId(searchTicketDTO.getDepartmentId(), pageRequest);
		}
		// .... thêm vài if esle làm cho quen như search theo trạng thái, search mix các
		// trường hợp name có + date
		if (StringUtils.hasText(searchTicketDTO.getClientName())) {
			page = ticketRepo.searchByName(searchTicketDTO.getClientName(), pageRequest);
		}

		PageDTO<List<TicketDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());

		// List<User> users = page.getContent();
		List<TicketDTO> ticketDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());

		pageDTO.setData(ticketDTOs);

		return pageDTO;
	}

}