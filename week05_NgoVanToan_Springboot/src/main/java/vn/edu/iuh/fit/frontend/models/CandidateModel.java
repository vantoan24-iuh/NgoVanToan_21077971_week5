package vn.edu.iuh.fit.frontend.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.fit.backend.entities.Candidate;
import vn.edu.iuh.fit.backend.services.imp.CandidateService;

@Component
public class CandidateModel {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CandidateService candidateServices;

    public Candidate getCandidateById(Long id) {
        return restTemplate.getForObject("http://localhost:8080/api/candidates/" + id, Candidate.class);
    }

    public Page<Candidate> getCandidates(int pageNo, int pageSize) {
        return restTemplate.getForObject("http://localhost:8080/api/candidates?pageNo=" + pageNo
                        + "&pageSize=" + pageSize,
                Page.class);
    }
    public Integer countCandidates() {
        return candidateServices.countCandidates();
    }
}
