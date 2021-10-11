package com.wildcats.project.response;

import com.wildcats.project.dto.DeviceDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDataResponse {
    private DeviceDataDto deviceData;
}
