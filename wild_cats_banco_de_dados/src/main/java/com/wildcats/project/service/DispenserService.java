package com.wildcats.project.service;

import com.wildcats.project.entity.DeviceData;
import com.wildcats.project.entity.Dispenser;
import com.wildcats.project.exception.NotFoundException;
import com.wildcats.project.mapper.DeviceDataMapper;
import com.wildcats.project.mapper.DispenserMapper;
import com.wildcats.project.request.DeviceDataRequest;
import com.wildcats.project.request.DispenserRequest;
import com.wildcats.project.response.DispenserResponseList;
import com.wildcats.project.respository.DeviceDataRepository;
import com.wildcats.project.respository.DispenserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Slf4j
@Service
public class DispenserService {

    private final DispenserRepository dispenserRepository;
    private final DispenserMapper dispenserMapper;
    private final DeviceDataRepository deviceDataRepository;

    public List<Dispenser> getAll() {
        return dispenserRepository.findAll();
    }

    public Dispenser salvar(DispenserRequest dispenserRequest) {


        dispenserRequest.setLastStockedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        dispenserRequest.setCreationDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

        Dispenser dispenser = this.dispenserRepository.save(this.dispenserMapper.toDispenser(dispenserRequest));

        DeviceDataRequest deviceDataRequest = new DeviceDataRequest();
        deviceDataRequest.setIdDispenser(dispenser.getId());
        deviceDataRequest.setFluidLevel(dispenserRequest.getFluidLevel());
        deviceDataRequest.setLocal(dispenserRequest.getLocal());
        deviceDataRequest.setUsed(dispenserRequest.getUsed());
        deviceDataRequest.setStocked(dispenserRequest.isStocked());
        deviceDataRequest.setAllUsedCount(dispenserRequest.getAllUsedCount());
        deviceDataRequest.setUpdatedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        DeviceDataService deviceDataService = new DeviceDataService(deviceDataRepository, new DeviceDataMapper(dispenserMapper), this);
        deviceDataService.salvar(deviceDataRequest);
        return  dispenser;
    }

    public void delete(Integer id) {
        this.dispenserRepository.deleteById(id);
    }

    public Dispenser getDispenserByid(Integer id) {
        return this.dispenserRepository.findById(id).orElseThrow(
                () -> new NotFoundException("não encontrado")
        );
    }

    public DispenserResponseList sendListResult(List<Dispenser> dispensers){
        return this.dispenserMapper.toListResult(dispensers);
    }

    public Dispenser getByMacAddress(String macAddress) {
        return this.dispenserRepository.findByMacAddress(macAddress);
    }

    public Dispenser update(Integer id, DispenserRequest dispenserRequest){
        return this.dispenserRepository.findById(id).map(dispenser -> {
            if (dispenserRequest.isStocked()) {
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String text = dateTime.format(formatter);
                LocalDateTime parsedTime = LocalDateTime.parse(text, formatter);
                dispenser.setLastStockedTime(parsedTime);
            }

            DeviceDataRequest deviceDataRequest = new DeviceDataRequest();
            deviceDataRequest.setIdDispenser(id);
            deviceDataRequest.setFluidLevel(dispenserRequest.getFluidLevel());
            deviceDataRequest.setLocal(dispenserRequest.getLocal());
            deviceDataRequest.setUsed(dispenserRequest.getUsed());
            deviceDataRequest.setStocked(dispenserRequest.isStocked());
            deviceDataRequest.setAllUsedCount(dispenserRequest.getAllUsedCount());
            deviceDataRequest.setUpdatedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            DeviceDataService deviceDataService = new DeviceDataService(deviceDataRepository, new DeviceDataMapper(dispenserMapper), this);
            deviceDataService.salvar(deviceDataRequest);

            dispenser.setFluidLevel(dispenserRequest.getFluidLevel());
            dispenser.setUsed(dispenserRequest.getUsed());
            dispenser.setLocal(dispenserRequest.getLocal());
            dispenser.setAllUsedCount(dispenserRequest.getAllUsedCount());
            return this.dispenserRepository.save(dispenser);
        }).orElseThrow(
                () -> new NotFoundException("id não encontrado")
        );
    }

    public List<Dispenser> finByLocal(String local){
        return this.dispenserRepository.findByLocalContainingIgnoreCase(local.toLowerCase(Locale.ROOT));
    }
}
