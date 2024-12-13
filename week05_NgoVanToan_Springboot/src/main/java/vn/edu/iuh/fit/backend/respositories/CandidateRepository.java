package vn.edu.iuh.fit.backend.respositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.entities.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    public Candidate findByEmail(String email);

    @Query("select c from Candidate c join c.candidateSkills cs " +
            "join JobSkill js on cs.skill.id = js.skill.id " +
            "where js.job.id = :jobId and cs.skillLevel >= js.skillLevel " +
            "group by c.id" )
    public Page<Candidate> findCandidatesForJobWithSkillLevel(@Param("jobId") Long jobId, Pageable pageable);
}
