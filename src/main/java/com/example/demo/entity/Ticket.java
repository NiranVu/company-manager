package com.example.demo.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Ticket {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer ticketId;
	
	private String clientName;
	private String clientPhone;
	
	private String content;
	private boolean status;
	private Date processDate;
	
	@CreatedDate
	@Column(updatable = false)
	private Date createdAt;
	
	@ManyToOne
	private Department department;

}
