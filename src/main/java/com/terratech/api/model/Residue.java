package com.terratech.api.model;

import com.terratech.api.model.enums.Status;
import com.terratech.api.model.enums.TypeResidue;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Residue {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private Integer quantity;
    private Status status;
    private TypeResidue type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
