package vn.edu.iuh.fit.backend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.backend.entities.Candidate;
import vn.edu.iuh.fit.backend.services.imp.CandidateService;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateResource {
    @Autowired
    private CandidateService candidateService;

    @RequestMapping("")
    public ResponseEntity<Page<Candidate>> findAll(@RequestParam int pageNo, @RequestParam int pageSize) {
        Page<Candidate> candidates = candidateService.findAll(pageNo, pageSize);
        return ResponseEntity.ok(candidates);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Candidate> findById(@PathVariable Long id) {
        Candidate candidate = candidateService.getCandidate(id);
        return ResponseEntity.ok(candidate);
    }

    @RequestMapping("/job/{jobId}")
    public ResponseEntity<Page<Candidate>> findCandidatesForJobWithSkillLevel(
            @PathVariable Long jobId,
            @RequestParam int pageNo,
            @RequestParam int pageSize) {
        Page<Candidate> candidates = candidateService.findCandidatesForJobWithSkillLevel(jobId, pageNo, pageSize);
        return ResponseEntity.ok(candidates);
    }

}
