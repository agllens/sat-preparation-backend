package com.sat_preparation_backend.duel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpponentDto {
    private Integer id;
    private String firstname;
    private String lastname;
}