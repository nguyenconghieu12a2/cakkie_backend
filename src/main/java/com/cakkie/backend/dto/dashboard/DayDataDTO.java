package com.cakkie.backend.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DayDataDTO {
    private int months;
    private List<DaySetDTO> dayset;
}
