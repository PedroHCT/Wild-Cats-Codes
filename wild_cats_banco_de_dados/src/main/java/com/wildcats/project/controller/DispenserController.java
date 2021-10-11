package com.wildcats.project.controller;

import com.wildcats.project.dto.DispenserDto;
import com.wildcats.project.entity.Dispenser;
import com.wildcats.project.mapper.DispenserMapper;
import com.wildcats.project.request.DispenserRequest;
import com.wildcats.project.response.DispenserResponse;
import com.wildcats.project.response.DispenserResponseList;
import com.wildcats.project.service.DispenserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dispenser")
public class DispenserController {

    private final DispenserService dispenserService;
    private final DispenserMapper dispenserMapper;

    @GetMapping
    public List<DispenserDto> getAll() {
        List<Dispenser> dispensers = this.dispenserService.getAll();
        return dispenserMapper.toListDto(dispensers);
    }

    @PostMapping
    @SendTo("/topic/dispenser")
    public DispenserResponse salvar(@RequestBody @Valid DispenserRequest request) throws Exception {
        Thread.sleep(1500);
        Dispenser dispenserMac = this.dispenserService.getByMacAddress(request.getMacAddress());
        if (dispenserMac == null) {
            Dispenser dispenser = this.dispenserService.salvar(request);
            return dispenserMapper.toSingleResult(dispenser);
        } else {
            Dispenser dispenser = new Dispenser();
            dispenser.setMacAddress("MacAddress aready exists");
            return dispenserMapper.toSingleResult(dispenser);
        }

    }

    @PutMapping("/id/{id}")
    @SendTo("/topic/dispenser")
    public DispenserResponse atualizaPorId(@PathVariable Integer id, @RequestBody @Valid DispenserRequest request) throws Exception {
        Thread.sleep(1500);
        Dispenser dispenser = this.dispenserService.update(id, request);
        return dispenserMapper.toSingleResult(dispenser);
    }

    @PutMapping("/mac/{macAddress}")
    @SendTo("/topic/dispenser")
    public DispenserResponse atualizaPorMacAddress(@PathVariable String macAddress, @RequestBody @Valid DispenserRequest request) throws Exception {
        Thread.sleep(1500);
        Dispenser dispenserByMac = this.dispenserService.getByMacAddress(macAddress);
        Dispenser dispenser = this.dispenserService.update(dispenserByMac.getId(), request);
        return dispenserMapper.toSingleResult(dispenser);
    }

    @GetMapping("/{local}")
    public DispenserResponseList getByLocal(@PathVariable String local){
        List<Dispenser> dispensers = this.dispenserService.finByLocal(local);
        return dispenserMapper.toListResult(dispensers);
    }

    @GetMapping("/mac/{macAddress}")
    public DispenserResponse getByMacAddress(@PathVariable String macAddress){
        Dispenser dispenser = this.dispenserService.getByMacAddress(macAddress);
        return dispenserMapper.toSingleResult(dispenser);
    }

    @DeleteMapping("/mac/{macAddress}")
    @SendTo("/topic/dispenser")
    public String delete(@PathVariable String macAddress) throws Exception {
        Thread.sleep(1500);
        Dispenser dispenserMac = this.dispenserService.getByMacAddress(macAddress);
        if (dispenserMac == null) {
            return "MacAddress não existe";
        }else{
            this.dispenserService.getDispenserByid(dispenserMac.getId());
            return "Dispenser Apagado";
        }
    }

}
