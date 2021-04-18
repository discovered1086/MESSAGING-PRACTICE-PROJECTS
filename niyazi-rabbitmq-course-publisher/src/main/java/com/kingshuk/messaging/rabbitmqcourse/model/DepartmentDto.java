package com.kingshuk.messaging.rabbitmqcourse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepartmentDto {
	private long departmentId;
	
	private String departmentName;

	@Override
	public String toString() {
		return "DepartmentDto [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
	}
	
	
	
	

}
