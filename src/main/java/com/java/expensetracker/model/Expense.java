package com.java.expensetracker.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "expense")
public class Expense {
    @Id
    private Long id;

    private Instant expenseDate;
    private String description;

    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;

}
