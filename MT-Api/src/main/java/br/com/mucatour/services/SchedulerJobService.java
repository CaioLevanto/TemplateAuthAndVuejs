package br.com.mucatour.services;

import java.util.List;

import javax.transaction.Transactional;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.quartz.QuartzJobBean;
import br.com.mucatour.component.JobScheduleCreator;
import br.com.mucatour.model.job.entity.SchedulerJobInfo;
import br.com.mucatour.model.travel.entity.Travel;
import br.com.mucatour.repository.SchedulerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SchedulerJobService {

	Scheduler scheduler;
	SchedulerFactoryBean schedulerFactoryBean;
	SchedulerRepository schedulerRepository;
	ApplicationContext context;
	JobScheduleCreator scheduleCreator;

	public SchedulerMetaData getMetaData() throws SchedulerException {
		SchedulerMetaData metaData = scheduler.getMetaData();
		return metaData;
	}

	public List<SchedulerJobInfo> getAllJobList() {
		return schedulerRepository.findAll();
	}

	// public boolean deleteJob(SchedulerJobInfo jobInfo) {
	// try {
	// SchedulerJobInfo getJobInfo =
	// schedulerRepository.findByJobName(jobInfo.getJobName());
	// schedulerRepository.delete(getJobInfo);
	// log.info(">>>>> jobName = [" + jobInfo.getJobName() + "]" + " deleted.");

	// return schedulerFactoryBean.getScheduler().deleteJob(new
	// JobKey(jobInfo.getJobName(), jobInfo.getJobGroup()));
	// } catch (SchedulerException e) {
	// log.error("Failed to delete job - {}", jobInfo.getJobName(), e);
	// return false;
	// }
	// }

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

	// public boolean startJobNow(SchedulerJobInfo jobInfo) {
	// try {
	// SchedulerJobInfo getJobInfo =
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
		SchedulerJobInfo info = schedulerRepository.findByJobName(name);

		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();

			JobDataMap map = new JobDataMap();
			map.put("travel", travel);

			JobDetail jobDetail = JobBuilder
					.newJob((Class<? extends QuartzJobBean>) Class.forName(info.getJobClass()))
					.withIdentity(info.getJobName() + "-" + travel.getId(), info.getJobGroup())
					.setJobData(map)
					.build();

			if (!scheduler.checkExists(jobDetail.getKey())) {
				Trigger trigger;

				if (info.getRepeatTime() > 0) {
					trigger = scheduleCreator.triggerRepeatDays(info, travel);
				} else {
					trigger = scheduleCreator.triggerInDay(info, travel);
				}

				scheduler.scheduleJob(jobDetail, trigger);
			} else {
				return false;
			}
		} catch (SchedulerException e) {
			log.error("Failed to create new job - {}", info.getJobName(), e);
			return false;
		} catch (ClassNotFoundException e) {
			log.error("Failed to create new job - {}", info.getJobName(), e);
			return false;
		}
		return true;
	}
}