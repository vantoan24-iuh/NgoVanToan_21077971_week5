/**
 * @ (#) CompanyResources.java      11/8/2024
 * <p>
 * Copyright (c) 2024 IUH. All rights reserved
 */

package vn.edu.iuh.fit.backend.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.backend.entities.Company;
import vn.edu.iuh.fit.backend.services.imp.CompanyService;

import java.util.List;
@RestController
@RequestMapping("/api/companies")
public class CompanyResource {
    @Autowired
    private CompanyService companyService;


    @RequestMapping("")
    public ResponseEntity<List<Company>> findAll() {
        List<Company> companies = companyService.getAll();
        return ResponseEntity.ok(companies);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<Company> getOne(@PathVariable Long id) {
        Company company = companyService.getById(id);
        return ResponseEntity.ok(company);
    }
}