package br.com.mucatour.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.quartz.QuartzJobBean;
import br.com.mucatour.component.JobScheduleCreator;
import br.com.mucatour.model.job.entity.SchedulerJobInfo;
import br.com.mucatour.model.job.enums.ListJobs;
import br.com.mucatour.model.job.enums.ScheduleStatus;
import br.com.mucatour.model.travel.entity.Travel;
import br.com.mucatour.repository.SchedulerRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class SchedulerJobService {

	Scheduler scheduler;
	SchedulerFactoryBean schedulerFactoryBean;
	SchedulerRepository schedulerRepository;
	ApplicationContext context;
	JobScheduleCreator scheduleCreator;

	@Autowired
	public SchedulerJobService(Scheduler scheduler, SchedulerFactoryBean schedulerFactoryBean,
			SchedulerRepository schedulerRepository, ApplicationContext context, JobScheduleCreator scheduleCreator) {
		this.scheduler = scheduler;
		this.schedulerFactoryBean = schedulerFactoryBean;
		this.schedulerRepository = schedulerRepository;
		this.context = context;
		this.scheduleCreator = scheduleCreator;
	}

	public SchedulerMetaData getMetaData() throws SchedulerException {
		SchedulerMetaData metaData = scheduler.getMetaData();
		return metaData;
	}

	public List<SchedulerJobInfo> getAllJobList() {
		return schedulerRepository.findAll();
	}

	public boolean deleteJob(SchedulerJobInfo jobInfo) {
		try {
			return schedulerFactoryBean.getScheduler()
					.deleteJob(new JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
		} catch (SchedulerException e) {
			log.error("Failed to delete job - {}", jobInfo.getJobName(), e);
			return false;
		}
	}

	// public boolean pauseJob(SchedulerJobInfo jobInfo) {
	// try {
	// SchedulerJobInfo getJobInfo =
	// schedulerRepository.findByJobName(jobInfo.getJobName());
	// getJobInfo.setJobStatus(ScheduleStatus.PAUSED);
	// schedulerRepository.save(getJobInfo);

	// schedulerFactoryBean.getScheduler().pauseJob(new JobKey(jobInfo.getJobName(),
	// jobInfo.getJobGroup()));
	// log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " paused.");
	// return true;
	// } catch (SchedulerException e) {
	// log.error("Failed to pause job - {}", jobInfo.getJobName(), e);
	// return false;
	// }
	// }

	// public boolean resumeJob(SchedulerJobInfo jobInfo) {
	// try {
	// SchedulerJobInfo getJobInfo =
	// schedulerRepository.findByJobName(jobInfo.getJobName());
	// getJobInfo.setJobStatus(ScheduleStatus.RESUMED);
	// schedulerRepository.save(getJobInfo);

	// schedulerFactoryBean.getScheduler().resumeJob(new
	// JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
	// log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " resumed.");
	// return true;
	// } catch (SchedulerException e) {
	// log.error("Failed to resume job - {}", jobInfo.getJobName(), e);
	// return false;
	// }
	// }

	// @SuppressWarnings("unchecked")
	// public boolean startJobNow(SchedulerJobInfo jobInfo) {
	// try {
	// Optional<SchedulerJobInfo> getJobInfo =
	// schedulerRepository.findByJobName(jobInfo.getJobName());
	// getJobInfo.setJobStatus(ScheduleStatus.RUNNING);
	// schedulerRepository.save(getJobInfo);

	// schedulerFactoryBean.getScheduler().triggerJob(new
	// JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
	// log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " scheduled and
	// started now.");
	// return true;
	// } catch (SchedulerException e) {
	// log.error("Failed to start new job - {}", jobInfo.getJobName(), e);
	// return false;
	// }
	// }

	@SuppressWarnings("unchecked")
	public boolean newJob(Travel travel, String name) {
		Optional<SchedulerJobInfo> info = schedulerRepository.findByJobName(name);

		if (info.isPresent()) {
			SchedulerJobInfo jobConfig = info.get();

			try {
				Scheduler scheduler = schedulerFactoryBean.getScheduler();

				JobDataMap map = new JobDataMap();
				map.put("travel", travel);

				JobDetail jobDetail = JobBuilder
						.newJob((Class<? extends QuartzJobBean>) Class.forName(jobConfig.getJobClass()))
						.withIdentity(jobConfig.getJobName() + "-" + travel.getId(), jobConfig.getJobGroup())
						.setJobData(map).build();

				if (!scheduler.checkExists(jobDetail.getKey())) {
					Trigger trigger;

					if (jobConfig.getRepeatTime() != null) {
						trigger = scheduleCreator.triggerRepeatDays(jobConfig, travel, jobDetail);
					} else {
						trigger = scheduleCreator.triggerInDay(jobConfig, travel, jobDetail);
					}

					scheduler.scheduleJob(jobDetail, trigger);
				} else {
					return false;
				}
			} catch (SchedulerException e) {
				log.error("Failed to create new job - {}", jobConfig.getJobName(), e);
				return false;
			} catch (ClassNotFoundException e) {
				log.error("Failed to create new job - {}", jobConfig.getJobName(), e);
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean newJobTest(String name) {
		Optional<SchedulerJobInfo> info = schedulerRepository.findByJobName(name);

		if (info.isPresent()) {
			SchedulerJobInfo jobConfig = info.get();

			try {
				Scheduler scheduler = schedulerFactoryBean.getScheduler();

				JobDataMap map = new JobDataMap();
				map.put("travel", "caio");

				JobDetail jobDetail = JobBuilder
						.newJob((Class<? extends QuartzJobBean>) Class.forName(jobConfig.getJobClass()))
						.withIdentity(jobConfig.getJobName(), jobConfig.getJobGroup()).setJobData(map).build();

				if (!scheduler.checkExists(jobDetail.getKey())) {
					scheduler.scheduleJob(jobDetail, scheduleCreator.triggerTestDay(jobConfig, jobDetail));
					return true;
				}
			} catch (SchedulerException e) {
				log.error("Failed to create new job - {}", jobConfig.getJobName(), e);
			} catch (ClassNotFoundException e) {
				log.error("Failed to create new job - {}", jobConfig.getJobName(), e);
			}
		}
		return false;
	}

}