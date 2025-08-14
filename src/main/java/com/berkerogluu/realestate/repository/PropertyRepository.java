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
    @Query( "SELECT * FROM properties WHERE " +
       "title LIKE CONCAT('%', :keyword, '%') OR " +
       "description LIKE CONCAT('%', :keyword, '%') OR " +
       "address_city LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    List<Property> findByKeyword(@Param("keyword") String keyword);
}
