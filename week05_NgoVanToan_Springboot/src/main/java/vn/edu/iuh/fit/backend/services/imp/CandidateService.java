package vn.edu.iuh.fit.backend.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.entities.Candidate;
import vn.edu.iuh.fit.backend.respositories.CandidateRepository;
import vn.edu.iuh.fit.backend.services.CandidateServiceImp;

import java.util.List;

@Service
public class CandidateService implements CandidateServiceImp {

    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Page<Candidate> findAll(int pageNo, int pageSize, String sortBy,
                                   String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return candidateRepository.findAll(pageable);
    }

    @Override
    public List<Candidate> findAllNoPage() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate getByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }

    @Override
    public Candidate getCandidate(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }

    @Override
    public Integer countCandidates() {
        return (int) candidateRepository.count();
    }

    @Override
    public Page<Candidate> findCandidatesForJobWithSkillLevel(Long jobId, int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        Page<Candidate> candidate = candidateRepository.findCandidatesForJobWithSkillLevel(jobId, pageRequest);
        return candidate;
    }

    @Override
    public Page<Candidate> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return candidateRepository.findAll(pageable);
    }
}
