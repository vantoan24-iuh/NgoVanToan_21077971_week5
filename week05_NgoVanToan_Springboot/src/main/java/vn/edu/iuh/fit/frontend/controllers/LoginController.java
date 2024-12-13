package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.entities.Company;
import vn.edu.iuh.fit.backend.entities.User;
import vn.edu.iuh.fit.backend.services.imp.UserService;
import vn.edu.iuh.fit.frontend.models.CandidateModel;
import vn.edu.iuh.fit.frontend.models.CompanyModel;
import vn.edu.iuh.fit.frontend.models.JobModel;

@Controller
@RequestMapping("/")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyModel companyModels;

    @Autowired
    private CandidateModel candidateModels;

    @Autowired
    private JobModel jobModels;

    @GetMapping("/login")
    public String showFormLogin(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,Model model) {
        session.removeAttribute("userLogin");
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/do-login")
    public String login(HttpServletRequest request, @ModelAttribute("user") User user) {
        User user0 = userService.getUserByUsernameAndPassword(user.getUsername().trim(), user.getPassword().trim());
        System.out.println("hello " + user0);

        if (user0 != null && !user0.getRoles().isEmpty()) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            session = request.getSession(true);
            session.setAttribute("userLogin", user0);

            // Check the role of the user
            if (user0.getRoles().get(0).getCode().equals("COMPANY")) {
                return "redirect:company/list";
            } else if (user0.getRoles().get(0).getCode().equals("CANDIDATE")) {
                return "redirect:company/list";
            }
        }

        // Redirect back to login if roles are missing or user is not found
        return "redirect:/login";
    }


}
