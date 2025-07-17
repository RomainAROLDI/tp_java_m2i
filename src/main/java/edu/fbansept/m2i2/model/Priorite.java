package edu.fbansept.m2i2.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.m2i2.view.PrioriteView;
import edu.fbansept.m2i2.view.TicketView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Priorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({TicketView.class, PrioriteView.class})
    protected Integer id;

    @NotBlank
    @Column(unique=true , nullable=false)
    @JsonView({TicketView.class, PrioriteView.class})
    private String nom;
}