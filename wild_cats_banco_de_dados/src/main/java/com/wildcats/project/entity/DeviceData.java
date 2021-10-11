package com.wildcats.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DEVICE_DATA")
public class DeviceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_dispenser", referencedColumnName = "id")
    private Dispenser dispenser;

    @Column(name = "fluid_level")
    private String fluidLevel;

    @Column(name = "local")
    private String local;

    @Column(name = "used_count")
    private String used;

    @Column(name = "all_used_count")
    private String allUsedCount;

    @Column(name = "updated_time")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updatedTime;

    @Column(name = "stocked")
    private boolean stocked;
}
