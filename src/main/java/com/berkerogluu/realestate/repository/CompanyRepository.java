package com.berkerogluu.realestate.repository;

import com.berkerogluu.realestate.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public Interface CompanyRepository extends JpaRepository<Company, Long> {

}
