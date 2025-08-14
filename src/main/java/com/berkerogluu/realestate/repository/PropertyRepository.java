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

    // Filter using property type, property status and heating type
    @Query(value = "SELECT * FROM properties WHERE " +
       "(:propertyType IS NULL OR property_type = :propertyType) AND " +
       "(:propertyStatus IS NULL OR property_status = :propertyStatus) AND " +
       "(:heatingType IS NULL OR heating_type = :heatingType)", nativeQuery = true)
    List<Property> findByFilters(@Param("propertyType") PropertyType propertyType, @Param("propertyStatus") PropertyStatus propertyStatus, @Param("heatingType") HeatingType heatingType);
}
