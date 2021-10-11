package com.wildcats.project.response;

import com.wildcats.project.dto.DeviceDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDataResponseList {
    private List<DeviceDataDto> deviceDatas;
}
