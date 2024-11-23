package com.cakkie.backend.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinMaxYearDTO {

    private int minYear;
    private int maxYear;

}
