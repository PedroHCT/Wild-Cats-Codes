package com.wildcats.project.mapper;

import com.wildcats.project.dto.DispenserDto;
import com.wildcats.project.entity.Dispenser;
import com.wildcats.project.request.DispenserRequest;
import com.wildcats.project.response.DispenserResponse;
import com.wildcats.project.response.DispenserResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DispenserMapper {

    public Dispenser toDispenser(@Valid DispenserRequest dispenser){
        return Dispenser.builder()
                .id(dispenser.getId())
                .fluidLevel(dispenser.getFluidLevel())
                .local(dispenser.getLocal())
                .macAddress(dispenser.getMacAddress())
                .allUsedCount(dispenser.getAllUsedCount())
                .lastStockedTime(LocalDateTime.parse(dispenser.getLastStockedTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) )
                .creationDate(LocalDateTime.parse(dispenser.getCreationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")))
                .stocked(dispenser.isStocked())
                .used(dispenser.getUsed()).build();
    }

    public DispenserDto toDto(Dispenser dispenser) {
        return DispenserDto.builder()
                .fluidLevel(dispenser.getFluidLevel())
                .local(dispenser.getLocal())
                .macAddress(dispenser.getMacAddress())
                .allUsedCount(dispenser.getAllUsedCount())
                .lastStockedTime(dispenser.getLastStockedTime())
                .creationDate(dispenser.getCreationDate())
                .used(dispenser.getUsed()).build();
    }

    public List<DispenserDto> toListDto(List<Dispenser> dispensers){
        return dispensers.stream().map(this::toDto).collect(Collectors.toList());
    }

    public DispenserResponse toSingleResult(Dispenser dispenser) {
        return DispenserResponse.builder().dispenser(this.toDto(dispenser)).build();
    }

    public DispenserResponseList toListResult(List<Dispenser> dispensers) {
        return DispenserResponseList.builder().dispensers(this.toListDto(dispensers)).build();
    }


}
