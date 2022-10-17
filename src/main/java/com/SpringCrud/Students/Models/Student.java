package com.SpringCrud.Students.Models;

import javax.persistence.*;

@Entity
@Table(name = "Students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(name = "Name")
    private String Name;


    public Student(Long id, String name) {
        Id = id;
        Name = name;
    }
    public Student(String name) {
        Name = name;
    }
    public Student() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
