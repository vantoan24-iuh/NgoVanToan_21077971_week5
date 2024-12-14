package vn.edu.iuh.fit.backend.respositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.entities.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("SELECT j FROM Job j WHERE j.company.id = :companyId")
    Page<Job> findJobByCompanyId(Long companyId, Pageable pageable);


    @Query("SELECT j FROM Job j WHERE j.company.id = ?1")
    List<Job> findJobsByCompanyId(Long companyId);

    @Query("SELECT COUNT(j) FROM Job j WHERE j.company.id = :companyId")
    int countJobByCompanyId(Long companyId);

    @Query("select jb from Job jb join jb.jobSkills js " +
            "join CandidateSkill cs on cs.skill.id = js.skill.id " +
            "where cs.candidate.id = :canId and cs.skillLevel >= js.skillLevel " +
            "group by jb.id" )
    Page<Job> findJobsForCandidateWithSkillLevel(@Param("canId") Long canId, Pageable pageable);
}
