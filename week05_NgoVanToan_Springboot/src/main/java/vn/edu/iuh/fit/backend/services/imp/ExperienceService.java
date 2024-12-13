package vn.edu.iuh.fit.backend.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.entities.Experience;
import vn.edu.iuh.fit.backend.respositories.ExperienceRepository;
import vn.edu.iuh.fit.backend.services.ExperienceServiceImp;

@Service
public class ExperienceService implements ExperienceServiceImp {
    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public void save(Experience experience) {
        experienceRepository.save(experience);

    }
}
