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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Boolean hasMessage = false;

    @Column(name = "message", nullable = true)
    private String message;

    @Column(name = "job_description", nullable = false)
    private String description;

    @Column(name = "date_info_at", nullable = true)
    private Long dateInfoAt;

    @Column(name = "rec_days", nullable = true)
    private Integer recDays;

    @Column(name = "repeat_time", nullable = true, length = 10)
    private Integer repeatTime;
}