package com.bricemi.resumeapi.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
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
