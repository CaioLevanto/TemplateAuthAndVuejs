package br.com.mucatour.controllers.job;

import java.util.Optional;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.mucatour.component.JobScheduleCreator;
import br.com.mucatour.model.job.entity.SchedulerJobInfo;
import br.com.mucatour.model.job.enums.ListJobs;
import br.com.mucatour.payload.response.Message;
import br.com.mucatour.repository.SchedulerRepository;
import br.com.mucatour.services.SchedulerJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/job")
public class JobController {

	private static final Logger log = LoggerFactory.getLogger(JobController.class);

	SchedulerRepository schedulerRepository;
	JobScheduleCreator scheduleCreator;
	SchedulerFactoryBean schedulerFactoryBean;
	SchedulerJobService scheduleJobService;

	@Autowired
	public JobController(SchedulerRepository schedulerRepository,
									JobScheduleCreator scheduleCreator,
									SchedulerFactoryBean schedulerFactoryBean,
									SchedulerJobService scheduleJobService) {
		this.schedulerRepository = schedulerRepository;
		this.scheduleCreator = scheduleCreator;
		this.schedulerFactoryBean = schedulerFactoryBean;
	 	this.scheduleJobService = scheduleJobService;

	}

	// @RequestMapping(value = "/saveOrUpdate", method = { RequestMethod.GET, RequestMethod.POST })
	// public Object saveOrUpdate(SchedulerJobInfo job) {
	// 	log.info("params, job = {}", job);
	// 	Message message = Message.failure();
	// 	try {
	// 		scheduleJobService.saveOrupdate(job);
	// 		message = Message.success();
	// 	} catch (Exception e) {
	// 		message.setMsg(e.getMessage());
	// 		log.error("updateCron ex:", e);
	// 	}
	// 	return message;
	// }

	// @RequestMapping("/metaData")
	// public Object metaData() throws SchedulerException {
	// 	SchedulerMetaData metaData = scheduleJobService.getMetaData();
	// 	return metaData;
	// }

	// @RequestMapping("/getAllJobs")
	// public Object getAllJobs() throws SchedulerException {
	// 	List<SchedulerJobInfo> jobList = scheduleJobService.getAllJobList();
	// 	return jobList;
	// }

	// @RequestMapping(value = "/runJob", method = { RequestMethod.GET, RequestMethod.POST })
	// public Object runJob(SchedulerJobInfo job) {
	// 	log.info("params, job = {}", job);
	// 	Message message = Message.failure();
	// 	try {
	// 		scheduleJobService.startJobNow(job);
	// 		message = Message.success();
	// 	} catch (Exception e) {
	// 		message.setMsg(e.getMessage());
	// 		log.error("runJob ex:", e);
	// 	}
	// 	return message;
	// }

	// @RequestMapping(value = "/pauseJob", method = { RequestMethod.GET, RequestMethod.POST })
	// public Object pauseJob(SchedulerJobInfo job) {
	// 	log.info("params, job = {}", job);
	// 	Message message = Message.failure();
	// 	try {
	// 		scheduleJobService.pauseJob(job);
	// 		message = Message.success();
	// 	} catch (Exception e) {
	// 		message.setMsg(e.getMessage());
	// 		log.error("pauseJob ex:", e);
	// 	}
	// 	return message;
	// }

	// @RequestMapping(value = "/resumeJob", method = { RequestMethod.GET, RequestMethod.POST })
	// public Object resumeJob(SchedulerJobInfo job) {
	// 	log.info("params, job = {}", job);
	// 	Message message = Message.failure();
	// 	try {
	// 		scheduleJobService.resumeJob(job);
	// 		message = Message.success();
	// 	} catch (Exception e) {
	// 		message.setMsg(e.getMessage());
	// 		log.error("resumeJob ex:", e);
	// 	}
	// 	return message;
	// }

	@SuppressWarnings("unchecked")
	@GetMapping("/test01")
	public void testJob01() throws Exception {
		Optional<SchedulerJobInfo> info = schedulerRepository.findByJobName(ListJobs.TRAVEL_PENDENCY.getName());
		
		if (info.isPresent()) {
			SchedulerJobInfo jobConfig = info.get();

			try {
				Scheduler scheduler = schedulerFactoryBean.getScheduler();
				
				JobDataMap map = new JobDataMap();
				map.put("travel", 1);

				JobDetail jobDetail = JobBuilder
							.newJob((Class<? extends QuartzJobBean>) Class.forName(jobConfig.getJobClass()))
							.withIdentity(jobConfig.getJobName(), jobConfig.getJobGroup())
							.setJobData(map)
							.build();

				if (scheduler.checkExists(jobDetail.getKey())) {
					scheduleJobService.deleteJob(jobConfig);
				}

				Trigger trigger = scheduleCreator.triggerTestDay(jobConfig, jobDetail); //montar trigger correta

				System.out.println("\n "+ jobConfig + "\n ");
				System.out.println(jobDetail + "\n ");
				System.out.println(trigger + "\n ");

				scheduler.scheduleJob(jobDetail, trigger); //Scheduler funcional
			} catch (SchedulerException e) {
				log.error("Failed to create new job - {}", jobConfig.getJobName(), e);
			} catch (ClassNotFoundException e) {
				log.error("Failed to create new job - {}", jobConfig.getJobName(), e);
			}
		} else {
			throw new Exception("Não foi possível encontrar a job");
		}
	}
}