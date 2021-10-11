package com.wildcats.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DISPENSER")
public class Dispenser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fluid_level")
    private String fluidLevel;

    @Column(name = "local")
    private String local;

    @Column(name = "used_count")
    private String used;

    @Column(name = "all_used_count")
    private String allUsedCount;

    @Column(name = "MAC_ADDRESS")
    private String macAddress;

    @Column(name = "creation_date", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(name = "last_stocked_time")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime lastStockedTime;

    @Transient
    private boolean stocked;

}
