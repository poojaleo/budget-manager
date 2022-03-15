package com.java.expensetracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    private Long id;
    @NonNull
    private String categoryName;
    /*@ManyToOne(cascade = CascadeType.PERSIST)
    private User user;*/
}
