package com.kingshuk.messaging.rabbitmqcourse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto {

	private long employeeId;

	private String firstName;

	private String lastName;

	private DepartmentDto department;

	private AddressDto address;

}
