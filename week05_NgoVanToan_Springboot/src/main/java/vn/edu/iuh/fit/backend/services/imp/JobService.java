package vn.edu.iuh.fit.backend.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.entities.Job;
import vn.edu.iuh.fit.backend.entities.JobSkill;
import vn.edu.iuh.fit.backend.respositories.CompanyRepository;
import vn.edu.iuh.fit.backend.respositories.JobRepository;
import vn.edu.iuh.fit.backend.respositories.JobSkillRepository;
import vn.edu.iuh.fit.backend.respositories.SkillRepository;
import vn.edu.iuh.fit.backend.services.JobServiceImp;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService implements JobServiceImp {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private JobSkillRepository jobSkillRepository;

    @Override
    public Page<Job> getAllJobs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Job> page = jobRepository.findAll(pageable);
        return page;
    }

    @Override
    public Page<Job> getJobsByCompanyI_Paging(Long companyId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Job> page = jobRepository.findJobByCompanyId(companyId, pageable);
        return page;
    }

    @Override
    public List<Job> getJobsByCompanyIda(Long companyId) {
        return  jobRepository.findJobsByCompanyId(companyId);
    }


    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public Job saveJob(Job jobDto) {
        return null;
    }

    @Override
    public int countJobByCompanyId(Long companyId) {
        return jobRepository.countJobByCompanyId(companyId);
    }

    @Override
    public List<JobSkill> getJobSkillsByJobId(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job != null) {
            return job.getJobSkills();
        }
        return new ArrayList<>();
    }

    @Override
    public Page<Job> findJobsForCandidateWithSkillLevel(Long canId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return jobRepository.findJobsForCandidateWithSkillLevel(canId, pageable);
    }
}
