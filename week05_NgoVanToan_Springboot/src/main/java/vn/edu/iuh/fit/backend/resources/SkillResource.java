package vn.edu.iuh.fit.backend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.backend.entities.Skill;
import vn.edu.iuh.fit.backend.services.imp.SkillService;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillResource {
    @Autowired
    private SkillService skillService;

    @GetMapping("")
    public ResponseEntity<List<Skill>> getSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }
}
