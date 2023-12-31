package com.example.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TicketDTO {

	
	private Integer ticketId;
	
	private String clientName;
	private String clientPhone;
	
	private String content;
	private boolean status;
	
	@DateTimeFormat(pattern = "mm/dd/yyyy")
	private Date processDate;
	
	@DateTimeFormat(pattern = "mm/dd/yyyy")
	private Date createdAt;

	private DepartmentDTO department;


	
	
	public Integer getTicketId() {
		return ticketId;
	}


	public void setTicketId(Integer ticketId) {
		this.ticketId = ticketId;
	}


	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getClientPhone() {
		return clientPhone;
	}


	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public Date getProcessDate() {
		return processDate;
	}


	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}


	public DepartmentDTO getDepartment() {
		return department;
	}


	public void setDepartment(DepartmentDTO department) {
		this.department = department;
	}

	

	
}
