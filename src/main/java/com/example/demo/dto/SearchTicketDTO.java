package com.example.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import groovy.transform.EqualsAndHashCode;
import groovyjarjarantlr4.v4.parse.ANTLRParser.id_return;
import lombok.Data;

@Data
@EqualsAndHashCode(callSuper = false)
public class SearchTicketDTO extends SearchDTO{
	
	private String clientName;
	
	
	@DateTimeFormat(pattern = "mm/dd/yyyy")
	private Date start;
	
	@DateTimeFormat(pattern = "mm/dd/yyyy")
	private Date end;
	
	private boolean status;

	private Integer departmentId;
	
	
	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	
}
