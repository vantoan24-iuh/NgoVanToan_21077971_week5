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

    // Hiển thị jobs với phân trang
    @GetMapping("")
    public String showJobsPaging(HttpSession session, Model model,
                                 @RequestParam(defaultValue = "0", required = false) Integer pageNo,
                                 @RequestParam(defaultValue = "8", required = false) Integer pageSize) {
        User user = (User) session.getAttribute("userLogin");

        if (user == null) {
            return "redirect:/login"; // Redirect nếu user chưa đăng nhập
        }

        Company company = companyModels.getCompanyById(user.getId());

        Page<Job> jobPage = jobModels.getJobsByCompanyI_Paging(user.getId(), pageNo, pageSize);

        model.addAttribute("jobs", jobPage);
        model.addAttribute("company", company);

        return "company/job/jobs";
    }

    // Hiển thị jobs không phân trang
    @GetMapping("/list")
    public String showJobsNoPaging(Model model, @RequestParam("companyId") Long companyId) {
        if (companyId == null) {
            throw new IllegalArgumentException("CompanyId must not be null");
        }

        List<Job> jobPage = jobServices.getJobsByCompanyIda(companyId);
        if (jobPage == null) {
            jobPage = new ArrayList<>(); // Tránh null
        }

        model.addAttribute("jobPage", jobPage);

        System.out.println("Company ID: " + companyId);
        System.out.println("Jobs: " + jobPage.toString());

        return "company/job/jobs";
    }


    @GetMapping({"/edit/{jobId}", "/add"})
    public String actionFormJob(HttpSession session,Model model,
                                @ModelAttribute Job job,
                                @PathVariable(required = false) Long jobId,
                                @RequestParam(required = false) String action,
                                @RequestParam(required = false, defaultValue = "0") Integer numTagSkill) {

        User user = session.getAttribute("userLogin") != null ? (User) session.getAttribute("userLogin") : null;
        Company companyDto = companyModels.getCompanyById(user.getId());
        if (jobId !=null) {
            job = jobModels.getJobById(jobId);
        } else {
            job = new Job();
            job.setCompany(companyDto);

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
                JobSkill jobSkillDto = new JobSkill();
                jobSkillDto.setSkill(new Skill());
                jobSkillDto.getSkill().setId((long) -i);
                job.getJobSkills().add(jobSkillDto);
            }
        }

        model.addAttribute("numTagSkill", numTagSkill);
        return "company/job/form-job";
    }

    @PostMapping("/save")
    public String saveJob(HttpSession session, @ModelAttribute("job")  Job job) {
        System.out.println(job.toString());
        jobModels.saveaJob(job);
        return "redirect:/jobs";
    }

}
