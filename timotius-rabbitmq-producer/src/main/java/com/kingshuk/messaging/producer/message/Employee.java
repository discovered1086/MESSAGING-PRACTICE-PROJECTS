package com.kingshuk.messaging.producer.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Data
@Builder
@ToString
public class Employee {

    private String name;

    private String employeeId;

    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dob;
}
