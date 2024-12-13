package vn.edu.iuh.fit.frontend.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.backend.entities.Skill;

import java.util.List;


@Component
public class SkillModel {
    @Autowired
    private RestTemplate restTemplate;

    public List<Skill> getSkills () {
        return restTemplate.exchange("http://localhost:8080/api/skills", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Skill>>() {}).getBody();
    }
}
