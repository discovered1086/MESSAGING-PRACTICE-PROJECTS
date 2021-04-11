package com.kingshuk.messaging.rabbitmqcourse.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Student implements Serializable{

    private String name;
    private long studentId;
    private double testScore;
}
