package com.wildcats.project.service;

import com.wildcats.project.entity.DeviceData;
import com.wildcats.project.entity.Dispenser;
import com.wildcats.project.mapper.DeviceDataMapper;
import com.wildcats.project.request.DeviceDataRequest;
import com.wildcats.project.response.DeviceDataResponseList;
import com.wildcats.project.respository.DeviceDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DeviceDataService {

    private final DeviceDataRepository deviceDataRepository;
    private final DeviceDataMapper deviceDataMapper;
    private final DispenserService dispenserService;


    public DeviceData salvar(DeviceDataRequest deviceDataRequest) {
        Integer idDispenser = deviceDataRequest.getIdDispenser();

        LocalDateTime updatedTime = LocalDateTime.parse(deviceDataRequest.getUpdatedTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        Dispenser dispenser = dispenserService.getDispenserByid(idDispenser);

        DeviceData deviceData = new DeviceData();
        deviceData.setDispenser(dispenser);
        deviceData.setFluidLevel(deviceDataRequest.getFluidLevel());
        deviceData.setUsed(deviceDataRequest.getUsed());
        deviceData.setAllUsedCount(deviceDataRequest.getAllUsedCount());
        deviceData.setStocked(deviceDataRequest.isStocked());
        deviceData.setLocal(deviceDataRequest.getLocal());
        deviceData.setUpdatedTime(updatedTime);

        return deviceDataRepository.save(deviceData);
    }

    public List<DeviceData> getAll(){
        return deviceDataRepository.findAll();
    }

    public DeviceDataResponseList sendListResult(List<DeviceData> deviceDatas) {
        return this.deviceDataMapper.toListResult(deviceDatas);
    }

}
