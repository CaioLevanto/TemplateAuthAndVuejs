
package br.com.mucatour;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import br.com.mucatour.jobs.cron.SimpleCronJob;
import br.com.mucatour.model.job.entity.SchedulerJobInfo;
import br.com.mucatour.model.job.enums.ListJobs;
import br.com.mucatour.model.job.enums.ScheduleStatus;
import br.com.mucatour.repository.SchedulerRepository;

@SpringBootApplication
public class MucaTourApplication {

	private static final Logger log = LoggerFactory.getLogger(MucaTourApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MucaTourApplication.class, args);
	}

	@Bean
	public CommandLineRunner jobs(SchedulerRepository schedulerRepository) {
		return (args) -> {
			log.info("----------------------------");
			SchedulerJobInfo jobPendency = SchedulerJobInfo.builder().jobName(ListJobs.TRAVEL_PENDENCY.getName())
					.jobGroup("travel").jobClass(SimpleCronJob.class.getName())
					.description("Realiza a contagem de tempo no qual a solicitação ficará pendente")
					.jobStatus(ScheduleStatus.RUNNING).hasMessage(false).build();

			if (!schedulerRepository.findByJobName(jobPendency.getJobName()).isPresent()) {
				schedulerRepository.save(jobPendency);
			}

			for (SchedulerJobInfo info : schedulerRepository.findAll()) {
				log.info(info.toString());
			}
			log.info("----------------------------");
		};
	}

	@Bean
	public SchedulerFactoryBeanCustomizer schedulerFactoryBeanCustomizer() {
		System.out.println("entrou  no factory bean");
		return new SchedulerFactoryBeanCustomizer() {
			@Override
			public void customize(SchedulerFactoryBean bean) {
				bean.setQuartzProperties(createQuartzProperties());
			}
		};
	}

	private Properties createQuartzProperties() {
		System.out.println("entrou  no create quartz properties");

		// Could also load from a file
		Properties props = new Properties();
		props.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
		return props;
	}

}
