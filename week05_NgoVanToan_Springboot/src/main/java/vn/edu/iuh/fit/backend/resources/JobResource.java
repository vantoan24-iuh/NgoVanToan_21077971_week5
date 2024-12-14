package vn.edu.iuh.fit.backend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.entities.Job;
import vn.edu.iuh.fit.backend.entities.JobSkill;
import vn.edu.iuh.fit.backend.services.imp.JobService;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobResource {

    @Autowired
    private JobService jobServices;

    // Get all jobs with pagination
    @GetMapping("")
    public ResponseEntity<Page<Job>> getJobs(
            @RequestParam int pageNo,
            @RequestParam int pageSize) {
        Page<Job> jobs = jobServices.getAllJobs(pageNo, pageSize);
        return ResponseEntity.ok(jobs);
    }

    // Get a job by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getOne(@PathVariable Long id) {
        Job job = jobServices.getJobById(id);
        return ResponseEntity.ok(job);
    }

    // Get jobs by company ID with pagination
    @GetMapping("/company")
    public ResponseEntity<Page<Job>> getJobByCompanyId_Paging(
            @RequestParam Long companyId,
            @RequestParam int pageNo,
            @RequestParam int pageSize) {
        Page<Job> jobs = jobServices.getJobsByCompanyI_Paging(companyId, pageNo, pageSize);
        return ResponseEntity.ok(jobs);
    }

    // Count jobs by company ID
    @GetMapping("/company/count")
    public ResponseEntity<Integer> countJobByCompanyId(@RequestParam Long companyId) {
        Integer count = jobServices.countJobByCompanyId(companyId);
        return ResponseEntity.ok(count);
    }

    // Get job skills by job ID
    @GetMapping("/{jobId}/skills")
    public ResponseEntity<List<JobSkill>> getJobSkillsByJobId(@PathVariable Long jobId) {
        List<JobSkill> jobSkills = jobServices.getJobSkillsByJobId(jobId);
        return ResponseEntity.ok(jobSkills);
    }

    // Save a new job
    @PostMapping
    public ResponseEntity<Job> save(@RequestBody Job job) {
        Job savedJob = jobServices.saveJob(job);
        return ResponseEntity.ok(savedJob);
    }

    // Get recommended jobs for a candidate with skill level
    @GetMapping("/recruitment/{canId}/recommend")
    public ResponseEntity<Page<Job>> findJobsForCandidateWithSkillLevel(
            @PathVariable Long canId,
            @RequestParam int pageNo,
            @RequestParam int pageSize) {
        Page<Job> jobs = jobServices.findJobsForCandidateWithSkillLevel(canId, pageNo, pageSize);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<Job>> getJobsByCompanyId(@PathVariable Long companyId) {
        List<Job> jobs = jobServices.getJobsByCompanyIda(companyId);
        return ResponseEntity.ok(jobs);
    }
}
