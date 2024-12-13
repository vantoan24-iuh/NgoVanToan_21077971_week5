package vn.edu.iuh.fit.backend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.iuh.fit.backend.entities.Job;
import vn.edu.iuh.fit.backend.entities.JobSkill;

import java.util.List;

public interface JobServiceImp {
    public Page<Job> getAllJobs(int pageNo, int pageSize);
    public Page<Job> getJobsByCompanyI_Paging(Long companyId, int pageNo, int pageSize);
    public List<Job> getJobsByCompanyIda(Long companyId);
    public Job getJobById(Long id);

    public Job saveJob(Job jobDto);
    public int countJobByCompanyId(Long companyId);

    public List<JobSkill> getJobSkillsByJobId(Long jobId);

    public Page<Job> findJobsForCandidateWithSkillLevel(Long canId, int pageNo, int pageSize);
}
