package com.wildcats.project.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispenserRequest {

    private Integer id;

    private String fluidLevel;

    private String local;

    private String used;

    private String allUsedCount;

    private String macAddress;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String creationDate;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private String lastStockedTime;

    private boolean stocked;
}
