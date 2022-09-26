package br.com.mucatour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mucatour.model.job.entity.SchedulerJobInfo;

@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerJobInfo, Long> {

	SchedulerJobInfo findByJobName(String jobName);

}