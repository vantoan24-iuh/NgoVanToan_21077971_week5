package vn.edu.iuh.fit;

import com.neovisionaries.i18n.CountryCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import vn.edu.iuh.fit.backend.entities.Address;
import vn.edu.iuh.fit.backend.entities.Candidate;
import vn.edu.iuh.fit.backend.entities.Role;
import vn.edu.iuh.fit.backend.entities.User;
import vn.edu.iuh.fit.backend.respositories.AddressRepository;
import vn.edu.iuh.fit.backend.respositories.CandidateRepository;
import vn.edu.iuh.fit.backend.respositories.RoleRepository;
import vn.edu.iuh.fit.backend.respositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

@SpringBootApplication
public class Week05NgoVanToanSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Week05NgoVanToanSpringbootApplication.class, args);
    }

//    @Autowired
//    private CandidateRepository candidateRepository;
//    @Autowired
//    private AddressRepository addressRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Bean
//    CommandLineRunner initData(){
//        return args -> {
//            Random rnd =new Random();
//
//            // Create sample Roles
//            Role companyRole = new Role(null, "COMPANY", "Company Role", new ArrayList<>());
//            Role candidateRole = new Role(null, "CANDIDATE", "Candidate Role", new ArrayList<>());
//
//            roleRepository.save(companyRole);
//            roleRepository.save(candidateRole);
//
//            // Create sample Users and assign roles
//            for (int i = 1; i <= 10; i++) {
//                String username = "user" + i;
//                String password = "password" + i; // You should encode passwords in a real application
//                User user = new User(null, username, password, new ArrayList<>());
//
//                // Assign roles
//                if (i % 2 == 0) {
//                    user.addRole(companyRole);
//                } else {
//                    user.addRole(candidateRole);
//                }
//
//                userRepository.save(user);
//                System.out.println("Added User: " + user);
//            }
//
//            for (int i = 1; i < 1000; i++) {
//                Address add = new Address(rnd.nextInt(1,1000)+"","Quang Trung","HCM",
//                        rnd.nextInt(70000,80000)+"", CountryCode.VN );
//                addressRepository.save(add);
//
//                Candidate can=new Candidate("Name #"+i,
//                        LocalDate.of(1998,rnd.nextInt(1,13),rnd.nextInt(1,29)),
//                        add,
//                        rnd.nextLong(1111111111L,9999999999L)+"",
//                        "email_"+i+"@gmail.com");
//                candidateRepository.save(can);
//                System.out.println("Added: " +can);
//            }
//        };
//    }
}
