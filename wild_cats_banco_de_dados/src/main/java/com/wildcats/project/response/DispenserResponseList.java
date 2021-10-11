package com.wildcats.project.response;

import com.wildcats.project.dto.DispenserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispenserResponseList {
    private List<DispenserDto> dispensers;
}
