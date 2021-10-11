package com.wildcats.project.response;


import com.wildcats.project.dto.DispenserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispenserResponse  {
    private DispenserDto dispenser;

}
