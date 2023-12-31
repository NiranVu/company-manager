package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SearchDTO {
	
	//@NotBlank(message = "{not.blank}")
	//@Size(min = 1, max = 20, message = "{size.msg}")
	private String keyword;
	
	private Integer currentPage;
	private Integer size;
	private String sortedField;
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getSortedField() {
		return sortedField;
	}
	public void setSortedField(String sortedField) {
		this.sortedField = sortedField;
	}
	
	
}
