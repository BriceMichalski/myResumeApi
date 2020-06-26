package com.bricemi.resumeapi.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

@Data
public class Job {

    @NonNull
    private String title;

    @NonNull
    private String company;

    @NonNull
    private LocalDate startDate;

    private LocalDate endDate;

    private List<Task> tasks;
}
