package com.example.demo.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	@Transactional
	public void create(UserDTO userDTO) throws Exception {
		
//		User user = new User();
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setAge(userDTO.getAge());
//		user.setUsername(userDTO.getUsername());
//		user.setPassword(userDTO.getPassword());
//		user.setAvatarURL(userDTO.getAvatarURL());
		userDTO.setBirthday(convertFormatDate(userDTO.getBirthday()));
		
		User user = new ModelMapper().map(userDTO, User.class);
		userRepo.save(user);
	}
	
	@Transactional
	public void delete(int id) {
		userRepo.deleteById(id);
	}
	
//	@Transactional
//	public void update(User user) {
//		userRepo.save(user);
//	}
	
	@Transactional
	public void update(UserDTO userDTO) throws Exception {
		User currentUser = userRepo.findById(userDTO.getId()).orElse(null);
		
		if (currentUser != null) {
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//			userDTO.setBirthday(formatter.parse(userDTO.getBirthday()));
			userDTO.setBirthday(convertFormatDate(userDTO.getBirthday()));
			User user = new ModelMapper().map(userDTO, User.class); 
			currentUser = user;
			
			userRepo.save(currentUser);
		}
	}
	
	public Date convertFormatDate (Date date) throws Exception {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String  dateString = dateFormat.format(date);
		
		
		Date newFormatDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		
		return newFormatDate;
	}
	
	public List<UserDTO> getAll() {
		
		List<User> userList = userRepo.findAll();
		
//		List<UserDTO> userDTOs = new ArrayList<>();
//		for(User user : userList) {
//			userDTOs.add(convert(user));
//		}
//		
//		return userDTOs;
		
		
		return userList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}
	
	public UserDTO getById(int id) {
		User user =  userRepo.findById(id).orElse(null);
		
		if (user != null) {

			return convert(user);

		}
		return null;
	}
	
	
	private UserDTO convert(User user) {
//		UserDTO userDTO = new UserDTO();
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setAge(user.getAge());
//		userDTO.setUsername(user.getUsername());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAvatarURL(user.getAvatarURL());
		
		UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);
		return userDTO;
	}
	
	public PageDTO<List<UserDTO>> searchName(SearchDTO searchDTO) {
		Sort sortBy = Sort.by("name").ascending().and(Sort.by("age").descending());
		
		if (org.springframework.util.StringUtils.hasText(searchDTO.getSortedField())) {
			sortBy = Sort.by(searchDTO.getSortedField()).ascending();
		}
		
		if (searchDTO.getCurrentPage() == null) {
			searchDTO.setCurrentPage(0);
		}
		
		if (searchDTO.getSize() == null) {
			searchDTO.setSize(2);
		}
		
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPage(), searchDTO.getSize(), sortBy);
		Page<User> page = userRepo.searchByName("%" + searchDTO.getKeyword() + "%", pageRequest);
		
		PageDTO<List<UserDTO>> pageDTO = new PageDTO<>();
		pageDTO.setTotalPages(page.getTotalPages());
		pageDTO.setTotalElements(page.getTotalElements());
		
		List<UserDTO> userDTOs = page.get().map(u -> convert(u)).collect(Collectors.toList());
		
		pageDTO.setData(userDTOs);
		
		return pageDTO;
	}
}
