package br.com.mucatour.jobs.simple;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("SimpleJob Start................");

        log.info(context.getJobDetail().getJobDataMap().get("travel").toString());

        log.info("SimpleJob End................");
    }
}
