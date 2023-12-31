package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Ticket;

public interface TicketRepo extends JpaRepository<Ticket, Integer>{

	@Query("SELECT u FROM Ticket u WHERE u.clientName LIKE :x")
	Page<Ticket> searchByName(@Param("x") String s, Pageable pageable);
	
	@Query("SELECT u FROM Ticket u WHERE u.id LIKE :x")
	Page<Ticket> searchById(@Param("x") int s, Pageable pageable);
	
	@Query("SELECT u FROM Ticket u " +  "WHERE u.createdAt >= :start AND u.createdAt <= :end")
	Page<Ticket> searchByDate(@Param("start") Date start, @Param("end") Date end, Pageable pageable);
	
	@Query("SELECT u FROM Ticket u JOIN u.department d" +  " WHERE d.id = :x")
	Page<Ticket> searchByDepartmentId(@Param("x") int dId, Pageable pageable);
	
	@Query("SELECT u FROM Ticket u  WHERE u.clientPhone = :x")
	Page<Ticket> searchByClientPhone(@Param("x") String dPhone, Pageable pageable);
	
	@Query("SELECT u FROM Ticket u JOIN u.department d" +  " WHERE d.name = :x")
	Page<Ticket> searchByClientName(@Param("x") String dName, Pageable pageable);
	
	Page<Ticket> findByStatus(Boolean status, Pageable pageable);
}
