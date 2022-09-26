package br.com.mucatour.model.job.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mucatour.model.job.enums.ScheduleStatus;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
@Entity
@Table(name = "config_scheduler")
public class SchedulerJobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "job_group", nullable = false)
    private String jobGroup;

    @Column(name = "job_class", nullable = false)
    private String jobClass;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ScheduleStatus jobStatus;

    @Column(name = "has_message", nullable = false)
    private Boolean hasMessage;

    @Column(name = "message", nullable = true)
    private String message;

    @Column(name = "job_description", nullable = false)
    private String description;

    @Column(name = "rec_days", nullable = true)
    private Long recDays;

    @Column(name = "repeat_time", nullable = true)
    private int repeatTime;
}