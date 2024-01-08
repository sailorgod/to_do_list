package main.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@Entity(name = "to_do_list")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Setter
    @Column(name = "creation_time")
    private LocalDateTime creationTime = LocalDateTime.now ();;

    @Setter
    @Column(name = "is_done")
    private boolean isDone = false;

    @Setter
    private String tittle;

    @Setter
    private String description;

}
