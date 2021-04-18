package com.kingshuk.messaging.rabbitmqcourse.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressDto {
	private long addressId;
	
	private String addressLine1;
	
	private String addressLine2;
	
	private String city;
	
	private String state;
	
	private String zipCode;

	@Override
	public String toString() {
		return "AddressDto [addressId=" + addressId + ", addressLine1=" + addressLine1 + ", addressLine2="
				+ addressLine2 + ", city=" + city + ", state=" + state + ", zipCode=" + zipCode + "]";
	}
	
	

}
