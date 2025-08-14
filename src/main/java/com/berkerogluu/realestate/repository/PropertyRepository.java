package com.berkerogluu.realestate.repository;

import com.berkerogluu.realestate.model.Property;
import com.berkerogluu.realestate.enums.PropertyType;
import com.berkerogluu.realestate.enums.PropertyStatus;
import com.berkerogluu.realestate.enums.HeatingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Search by keyword (title, description, city)
    @Query(value = "SELECT * FROM properties WHERE " +
       "UPPER(title) LIKE UPPER(CONCAT('%', :keyword, '%')) OR " +
       "UPPER(description) LIKE UPPER(CONCAT('%', :keyword, '%')) OR " +
       "UPPER(address_city) LIKE UPPER(CONCAT('%', :keyword, '%'))", nativeQuery = true)
    List<Property> findByKeyword(@Param("keyword") String keyword);
}
