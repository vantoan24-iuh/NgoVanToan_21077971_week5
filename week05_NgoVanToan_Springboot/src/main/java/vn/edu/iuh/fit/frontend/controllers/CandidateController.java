package vn.edu.iuh.fit.frontend.controllers;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.edu.iuh.fit.backend.entities.Address;
import vn.edu.iuh.fit.backend.entities.Candidate;
import vn.edu.iuh.fit.backend.entities.CandidateSkill;
import vn.edu.iuh.fit.backend.entities.Experience;
import vn.edu.iuh.fit.backend.respositories.AddressRepository;
import vn.edu.iuh.fit.backend.respositories.CandidateRepository;
import vn.edu.iuh.fit.backend.services.imp.CandidateService;
import vn.edu.iuh.fit.backend.services.imp.CandidateSkillServices;
import vn.edu.iuh.fit.backend.services.imp.ExperienceService;
import vn.edu.iuh.fit.backend.services.imp.SkillService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/candidates")
public class CandidateController {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CandidateService candidateServices;

    @Autowired
    private SkillService skillService;

    @Autowired
    private CandidateSkillServices candidateSkillServices;

    @Autowired
    private ExperienceService experienceService;


    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll());
        return "candidates/candidates";
    }

    @GetMapping("")
    public String showCandidateListPaging(Model model,
                                          @RequestParam("page") java.util.Optional<Integer> page,
                                          @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        Page<Candidate> candidatePage = candidateServices.findAll(
                currentPage - 1, pageSize, "id", "asc");
        model.addAttribute("candidatePage", candidatePage);
        int totalPages = candidatePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "candidates/candidates-paging";
    }

    @GetMapping("/add")
    public ModelAndView showAddCandidateForm() {
        ModelAndView mav = new ModelAndView("candidates/addCandidate");
        Candidate candidate = new Candidate();
        candidate.setAddress(new Address());
        Experience experience = new Experience();
        candidate.setCandidateSkills(new ArrayList<>());
//        candidate.setExperiences(new ArrayList<>());
        mav.addObject("candidate", candidate);
        mav.addObject("address", candidate.getAddress());
        mav.addObject("countries", CountryCode.values());
        mav.addObject("skills", skillService.getAllSkills());
        mav.addObject("experience", experience);

        return mav;
    }

    @PostMapping("/add")
    public String addCandidate(@ModelAttribute("candidate") Candidate candidate,
                               @ModelAttribute("address") Address address,
                               @ModelAttribute("experience") Experience experience) {
        if (candidate.getCandidateSkills() == null) {
            candidate.setCandidateSkills(new ArrayList<>());
        }
        candidate.getCandidateSkills().removeIf(Objects::isNull);

        addressRepository.save(address);
        candidate.setAddress(address);
        candidateRepository.save(candidate);

        Candidate canbyEmail = candidateRepository.findByEmail(candidate.getEmail());
        System.out.println(candidate.getCandidateSkills());

        // Lưu từng CandidateSkill
        for (CandidateSkill candidateSkill : candidate.getCandidateSkills()) {
            if (candidateSkill.getSkill() != null && candidateSkill.getSkillLevel() != null) { // Kiểm tra null trước khi lưu
                candidateSkill.setCandidate(canbyEmail);
                candidateSkillServices.save(candidateSkill);
            }
        }

        experience.setCandidate(canbyEmail);
        experienceService.save(experience);

        return "redirect:/candidates/list";
    }

    @GetMapping("form-update-candidate/{id}")
    public ModelAndView showFormEditCandidate(Model model, @PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("candidates/update-candidates");
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if (candidate.isPresent()) {
            mav.addObject("candidate", candidate.get());
            mav.addObject("address", candidate.get().getAddress());
            mav.addObject("countries", CountryCode.values());
        }
        return mav;
    }

    @PostMapping("edit")
    public String editCandidate(@ModelAttribute("candidate") Candidate candidate
            , @ModelAttribute("address") Address address) {
        addressRepository.save(address);
        candidate.setAddress(address);
        candidateRepository.save(candidate);
        return "redirect:/candidates/list";
    }

    @GetMapping("delete/{id}")
    public String deleteCandidate(@PathVariable("id") Long id) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid candidate Id:" + id));
        candidateRepository.delete(candidate);
        return "redirect:/candidates/list";
    }
}
