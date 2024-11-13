package com.exo1.exo1.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "projet_task_count")
public class ProjetTaskCount {
    @Id
    private Long projet_id;
    private String name;
    @Column(name = "task_count")
    private Long taskCount;

}
