package br.com.mucatour.component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;

import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Component;

import br.com.mucatour.model.job.entity.SchedulerJobInfo;
import br.com.mucatour.model.travel.entity.Travel;

@Component
public class JobScheduleCreator {

    public Trigger triggerRepeatDays(SchedulerJobInfo info, Travel travel) {
        return TriggerBuilder.newTrigger()
                .withIdentity(info.getJobName() + "-" + travel.getId(), info.getJobGroup())
                .startNow()
                .withSchedule(calendarIntervalSchedule()
                        .withIntervalInDays(info.getRepeatTime()))
                .endAt(Date.from(Instant.ofEpochMilli(travel.getStartDate())))
                .build();
    }

    public Trigger triggerInDay(SchedulerJobInfo info, Travel travel) {
        LocalDate date = LocalDate.ofEpochDay(Instant.ofEpochMilli(travel.getStartDate()).getEpochSecond());

        return TriggerBuilder.newTrigger()
                .withIdentity(info.getJobName() + "-" + travel.getId(), info.getJobGroup())
                .withSchedule(cronSchedule(
                        "* * * " + date.getDayOfMonth() + " " + date.getMonthValue() + " ? " + date.getYear()))
                .build();
    }
}
