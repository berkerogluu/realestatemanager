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
    // In JPQL, we can select from entity name instead of table name properties
    @Query("SELECT p FROM Property p WHERE " +
       "UPPER(p.title) LIKE UPPER(CONCAT('%', :keyword, '%')) OR " +
       "UPPER(p.description) LIKE UPPER(CONCAT('%', :keyword, '%')) OR " +
       "UPPER(p.addressCity) LIKE UPPER(CONCAT('%', :keyword, '%'))")
    List<Property> findByKeyword(@Param("keyword") String keyword);

    // Filter using property type, property status and heating type
    // In JPQL, we can select from entity name instead of table name properties
    @Query("SELECT p FROM Property p WHERE " +
       "(:propertyType IS NULL OR p.propertyType = :propertyType) AND " +
       "(:propertyStatus IS NULL OR p.propertyStatus = :propertyStatus) AND " +
       "(:heatingType IS NULL OR p.heatingType = :heatingType)")
    List<Property> findByFilters(@Param("propertyType") PropertyType propertyType, @Param("propertyStatus") PropertyStatus propertyStatus, @Param("heatingType") HeatingType heatingType);
}
