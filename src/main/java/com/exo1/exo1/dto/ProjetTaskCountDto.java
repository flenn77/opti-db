package com.exo1.exo1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjetTaskCountDto {

    private long project_id;
    private String name;
    private long taskCount;
}
