package vn.edu.iuh.fit.backend.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.entities.CandidateSkill;
import vn.edu.iuh.fit.backend.respositories.CandidateSkillRepository;
import vn.edu.iuh.fit.backend.services.CandidateSkillServiceImp;

@Service
public class CandidateSkillServices implements CandidateSkillServiceImp {
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Override
    public void save(CandidateSkill candidateSkill) {
        candidateSkillRepository.save(candidateSkill);
    }
}
