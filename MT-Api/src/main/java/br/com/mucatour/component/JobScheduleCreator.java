package br.com.mucatour.component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import br.com.mucatour.model.job.entity.SchedulerJobInfo;
import br.com.mucatour.model.travel.entity.Travel;

@Component
public class JobScheduleCreator {

    public Trigger triggerRepeatDays(SchedulerJobInfo info, Travel travel, JobDetail job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(info.getJobName() + "-" + travel.getId(), info.getJobGroup())
                .startNow()
                .withSchedule(calendarIntervalSchedule()
                        .withIntervalInDays(info.getRepeatTime()))
                .endAt(Date.from(Instant.ofEpochMilli(travel.getStartDate())))
                .forJob(job)
                .build();
    }

    public Trigger triggerInDay(SchedulerJobInfo info, Travel travel, JobDetail job) {
        LocalDate date;
        //DateInfoAt são datas fixas onde são pré-definidas pelo job ou pelo usuário.
        if (info.getDateInfoAt() != null) {
            date = LocalDate.ofEpochDay(
                Instant.ofEpochMilli(info.getDateInfoAt())
                    .getEpochSecond());
        } else {
            date = LocalDate.ofEpochDay(
                Instant.ofEpochMilli(travel.getStartDate())
                    .getEpochSecond());
        }
        
        //Para ter Recuo de data, não pode ter data fixa, pois deve ser com base na data da viagem.
        if (info.getRecDays() != null) {
            date = date.minus(info.getRecDays(), ChronoUnit.DAYS); 
        }

        return TriggerBuilder.newTrigger()
                .withIdentity(info.getJobName() + "-" + travel.getId(), info.getJobGroup())
                .withSchedule(cronSchedule(
                        "* * * " + date.getDayOfMonth() + " " + date.getMonthValue() + " ? " + date.getYear()))
                .forJob(job)
                .build();
    }

    public Trigger triggerTestDay(SchedulerJobInfo info, JobDetail job) {
        return TriggerBuilder.newTrigger()
                .withIdentity(info.getJobName(), info.getJobGroup())
                .startNow()
                .withSchedule(cronSchedule("0 0/1 * * * ? *"))
                .endAt(Date.from(Instant.now().plus(4, ChronoUnit.MINUTES)))
                .forJob(job)
                .build();
    }
}
