package com.wildcats.project.mapper;

import com.wildcats.project.dto.DeviceDataDto;
import com.wildcats.project.entity.DeviceData;
import com.wildcats.project.entity.Dispenser;
import com.wildcats.project.request.DeviceDataRequest;
import com.wildcats.project.response.DeviceDataResponse;
import com.wildcats.project.response.DeviceDataResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DeviceDataMapper {

   private final DispenserMapper dispenserMapper;

    public DeviceData toDeviceData(@Valid DeviceData deviceData) {
        return DeviceData.builder()
                .id(deviceData.getId())
                .fluidLevel(deviceData.getFluidLevel())
                .local(deviceData.getLocal())
                .used(deviceData.getUsed())
                .fluidLevel(deviceData.getFluidLevel())
                .updatedTime(deviceData.getUpdatedTime())
                .allUsedCount((deviceData.getAllUsedCount()))
                .stocked(deviceData.isStocked())
                .dispenser(deviceData.getDispenser()).build();
    }

    public DeviceDataDto toDto(DeviceData deviceData){
        return DeviceDataDto.builder()
                .dispenser(dispenserMapper.toDto(deviceData.getDispenser()))
                .local(deviceData.getLocal())
                .used(deviceData.getUsed())
                .fluidLevel(deviceData.getFluidLevel())
                .updatedTime(deviceData.getUpdatedTime())
                .allUsedCount(deviceData.getAllUsedCount())
                .stocked(deviceData.isStocked())
                .build();
    }

    public List<DeviceDataDto> toListDto(List<DeviceData> deviceDatas) {
        return deviceDatas.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DeviceDataResponse toSingleResult(DeviceData deviceData) {
        return DeviceDataResponse.builder().deviceData(this.toDto(deviceData)).build();
    }

    public DeviceDataResponseList toListResult(List<DeviceData> deviceDatas) {
        return DeviceDataResponseList.builder().deviceDatas(this.toListDto(deviceDatas)).build();
    }

}
