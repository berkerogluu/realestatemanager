package com.berkerogluu.realestate.service;

import com.berkerogluu.realestate.model.Company;
import com.berkerogluu.realestate.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    //
    public Optional<Company> getCompany() {
        List<Company> companies = companyRepository.findAll();
        if(companies.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(companies.get(0));
        }
    }

    // Create a company if no company exist
    public Company createCompany(Company company) {
        if(isCompanyExist()) {
            return null;
        }

        return companyRepository.save(company);
    }

    // Check if company exist
    public boolean isCompanyExist() {
        if(companyRepository.count() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkApiKey(String apiKey) {
        Optional<Company> company = getCompany();

        return company.isPresent() && apiKey.equals(company.get().getApiKey());
    }
}
