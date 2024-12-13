package vn.edu.iuh.fit.backend.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.entities.Skill;
import vn.edu.iuh.fit.backend.respositories.SkillRepository;
import vn.edu.iuh.fit.backend.services.SkillServiceImp;

import java.util.List;
@Service
public class SkillService implements SkillServiceImp {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}
