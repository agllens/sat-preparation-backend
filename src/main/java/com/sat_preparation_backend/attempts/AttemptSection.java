package com.sat_preparation_backend.attempts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attempt_sections")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttemptSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Attempt attempt;

    private String sectionName;

    private Integer correct;
    private Integer total;
    private Integer answered;
}