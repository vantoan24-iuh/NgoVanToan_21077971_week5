package vn.edu.iuh.fit.backend.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.entities.Company;
import vn.edu.iuh.fit.backend.entities.Job;
import vn.edu.iuh.fit.backend.entities.JobSkill;
import vn.edu.iuh.fit.backend.entities.Skill;
import vn.edu.iuh.fit.backend.ids.JobSkillId;
import vn.edu.iuh.fit.backend.respositories.CompanyRepository;
import vn.edu.iuh.fit.backend.respositories.JobRepository;
import vn.edu.iuh.fit.backend.respositories.JobSkillRepository;
import vn.edu.iuh.fit.backend.respositories.SkillRepository;
import vn.edu.iuh.fit.backend.services.JobServiceImp;

import java.util.ArrayList;
import java.util.Collections;
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
        return jobRepository.findJobsByCompanyId(companyId);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public Job saveJob(Job jobDto) {
        Job job = new Job();

        if (jobDto.getCompany().getId() == null) {
            return null;
        }
        Company company = companyRepository.findById(jobDto.getCompany().getId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        job.setCompany(company);

        job.setJobSkills(Collections.emptyList());
        Job jobAdd = jobRepository.save(job);

        if (jobDto.getJobSkills() != null) {
            List<JobSkill> jobSkills = new ArrayList<>();
            jobDto.getJobSkills().forEach(jobSkillDTO -> {

                Skill skill = new Skill();
                // find skill by id
                if (jobSkillDTO.getSkill().getId() != null) {
                    skill = skillRepository.findById(jobSkillDTO.getSkill().getId())
                            .orElseThrow(() -> new RuntimeException("Skill not found"));

                } else {
                    // create new skill
                    skill.setSkillName(jobSkillDTO.getSkill().getSkillName());
                    skill.setSkillDescription(jobSkillDTO.getSkill().getSkillDescription());
                    skill.setType(jobSkillDTO.getSkill().getType());
                    skillRepository.save(skill);
                }

                // create job skill
                JobSkill jobSkill = new JobSkill();
                jobSkill.setSkill(skill);
                jobSkill.setJob(jobAdd);

                // create job skill id
                JobSkillId jobSkillId = new JobSkillId();
                jobSkillId.setJobId(jobAdd.getId());
                jobSkillId.setSkillId(skill.getId());

                jobSkill.setId(jobSkillId);

                jobSkillRepository.save(jobSkill);
                jobSkills.add(jobSkill);
            });
            jobAdd.setJobSkills(jobSkills);
        }
        System.out.println("job service"+jobAdd);
        return jobRepository.save(jobAdd);
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
        return (Page<Job>) jobRepository.findJobsForCandidateWithSkillLevel(canId, pageable).stream().toList();
    }
}
