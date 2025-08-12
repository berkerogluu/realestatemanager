package com.berkerogluu.realestate.controller;

import com.berkerogluu.realestate.model.Company;
import com.berkerogluu.realestate.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company")
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /*
        * Get  : no apikey
        * Post : yes apikey
        * Put  : yes apikey
    */

    @GetMapping
    public ResponseEntity<Company> getCompany(@RequestParam String apiKey) {
        if (!companyService.checkApiKey(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Company> company = companyService.getCompany();
        return company.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Initial creation, so we dont need to use an api key ?
    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
        if (companyService.isCompanyExist()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Company is already exist");
        }

        Company savedCompany = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.OK).body(savedCompany);
    }
}
