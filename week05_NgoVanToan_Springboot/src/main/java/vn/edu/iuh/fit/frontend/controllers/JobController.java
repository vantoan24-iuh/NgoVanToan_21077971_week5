package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.entities.*;
import vn.edu.iuh.fit.backend.enums.SkillLevel;
import vn.edu.iuh.fit.backend.respositories.JobRepository;
import vn.edu.iuh.fit.backend.services.imp.CompanyService;
import vn.edu.iuh.fit.backend.services.imp.JobService;
import vn.edu.iuh.fit.frontend.models.CompanyModel;
import vn.edu.iuh.fit.frontend.models.JobModel;
import vn.edu.iuh.fit.frontend.models.SkillModel;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/job")
@SessionAttributes("userLogin")
public class JobController {
    @Autowired
    private JobService jobServices;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobModel jobModels;

    @Autowired
    private SkillModel skillModels;

    @Autowired
    private CompanyModel companyModels;

    @Autowired
    private CompanyService companyService;

    @GetMapping("")
    public String showJobsPaging(HttpSession session, Model model,
                                 @RequestParam(defaultValue = "0") Integer pageNo,
                                 @RequestParam(defaultValue = "8") Integer pageSize) {
        User user = (User) session.getAttribute("userLogin");
        if (user == null) {
            return "redirect:/login";
        }

        Company company = companyModels.getCompanyById(user.getId());
        Page<Job> jobPage = jobModels.getJobsByCompanyI_Paging(user.getId(), pageNo, pageSize);

        model.addAttribute("job", jobPage);
        model.addAttribute("company", company);

        return "company/jobs/job";
    }

    @GetMapping("/job/list")
    public String listJobs(Model model, @RequestParam("companyId") Long companyId) {
        List<Job> jobs = jobServices.getJobsByCompanyIda(companyId);
        Company company = companyService.getById(companyId); // Assuming you have a service for this

        model.addAttribute("jobs", jobs);
        model.addAttribute("company", company);

        return "company/jobs/job";
    }


    @GetMapping({"/edit/{jobId}", "/add"})
    public String actionFormJob(HttpSession session, Model model,
                                @ModelAttribute Job job,
                                @PathVariable(required = false) Long jobId,
                                @RequestParam(required = false) String action,
                                @RequestParam(required = false, defaultValue = "0") Integer numTagSkill) {

        User user = session.getAttribute("userLogin") != null ? (User) session.getAttribute("userLogin") : null;
        assert user != null;
        Company company = companyModels.getCompanyById(user.getId());
        if (jobId !=null) {
            job = jobModels.getJobById(jobId);
        } else {
            job = new Job();
            job.setCompany(company);

            List<JobSkill> jobSkills = new ArrayList<>();
            jobSkills.add(new JobSkill());

            job.setJobSkills(jobSkills);
        }

        List<Skill> skills = skillModels.getSkills();
        List<SkillLevel> skillLevels = List.of(SkillLevel.values());

        model.addAttribute("job", job);
        model.addAttribute("skills", skills);
        model.addAttribute("skillLevels", skillLevels);


        if("newTagSkill".equals(action)) {
            for (int i = 0; i < numTagSkill; i++) {
                JobSkill jobSkill = new JobSkill();
                jobSkill.setSkill(new Skill());
                jobSkill.getSkill().setId((long) -i);
                job.getJobSkills().add(jobSkill);
            }
        }

        model.addAttribute("numTagSkill", numTagSkill);
        return "company/jobs/form-job";
    }

    @PostMapping("/save")
    public String saveJob(HttpSession session, @ModelAttribute("job")  Job job) {
        System.out.println(job.toString());
        jobModels.saveaJob(job);
        return "redirect:/job";
    }

}
