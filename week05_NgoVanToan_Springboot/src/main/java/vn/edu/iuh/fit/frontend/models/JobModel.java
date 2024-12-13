package vn.edu.iuh.fit.frontend.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.backend.entities.Job;
import vn.edu.iuh.fit.backend.entities.JobSkill;
import vn.edu.iuh.fit.backend.resources.JobResource;
import vn.edu.iuh.fit.backend.respositories.JobRepository;

import java.util.List;

@Component
public class JobModel {
    @Autowired
    private JobResource jobResources;
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Page<Job> getJobs(int pageNo, int pageSize) {
        return restTemplate.getForObject("http://localhost:8080/api/job"
                        + "?pageNo=" + pageNo
                        + "&pageSize=" + pageSize,
                Page.class);
    }

    // Example: Update JobModel
    public Page<Job> getJobsByCompanyI_Paging(Long companyId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return jobRepository.findJobByCompanyId(companyId, pageable);
    }


    public Job getJobById(Long id) {
        return restTemplate.getForObject("http://localhost:8080/api/job/" + id, Job.class);
    }

    public Integer countJobByCompanyId(Long companyId) {
        return restTemplate.getForObject("http://localhost:8080/api/job/company/count?companyId=" + companyId, Integer.class);
    }

    public List<JobSkill> getJobSkillsByJobId(Long jobId) {
        ResponseEntity<List<JobSkill>> response = restTemplate.exchange("http://localhost:8080/api/job/" + jobId + "/skills",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<JobSkill>>() {
                });
        return response.getBody();
    }

    public Job saveaJob(Job job) {
        System.out.println("job model "+job);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Job> request = new HttpEntity<>(job, headers);

        ResponseEntity<Job> response = restTemplate.exchange(
                "http://localhost:8080/api/jobs",
                HttpMethod.POST,
                request,
                Job.class);

        return response.getBody();
    }

    public Page<Job> findJobsForCandidateWithSkillLevel(Long canId, int pageNo, int pageSize) {
        return restTemplate.getForObject("http://localhost:8080/api/job/recruitment/" + canId + "/recommend"
                        + "?pageNo=" + pageNo
                        + "&pageSize=" + pageSize,
                Page.class);
    }
}
