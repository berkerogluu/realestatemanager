package com.berkerogluu.realestate.repository;

import com.berkerogluu.realestate.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public Interface CompanyRepository extends JpaRepository<Company, Long> {

    // Find company by name
    Optional<Company> findByCompanyName(String companyName);
}
