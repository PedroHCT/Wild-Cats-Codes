package com.wildcats.project.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wildcats.project.entity.Dispenser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDataRequest {

    private Integer id;

    private Integer idDispenser;

    private String fluidLevel;

    private String local;

    private String used;

    private String allUsedCount;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String updatedTime;

    private boolean stocked;

}
