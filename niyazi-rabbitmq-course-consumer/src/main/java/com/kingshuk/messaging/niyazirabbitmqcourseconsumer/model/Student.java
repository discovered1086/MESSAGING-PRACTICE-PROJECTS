package com.kingshuk.messaging.niyazirabbitmqcourseconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Student implements Serializable{

    private String name;
    private long studentId;
    private List<StudentTestScore> testScore;
}
