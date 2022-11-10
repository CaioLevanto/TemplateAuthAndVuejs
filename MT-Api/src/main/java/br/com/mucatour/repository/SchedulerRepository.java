package br.com.mucatour.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mucatour.model.job.entity.SchedulerJobInfo;

@Repository
public interface SchedulerRepository extends JpaRepository<SchedulerJobInfo, Long> {

	@Query("select sj from SchedulerJobInfo sj where sj.jobName = :jobName")
	Optional<SchedulerJobInfo> findByJobName(@Param("jobName") String jobName);

}