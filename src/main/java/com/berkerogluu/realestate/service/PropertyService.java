package com.berkerogluu.realestate.service;

import com.berkerogluu.realestate.model.Property;
import com.berkerogluu.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.berkerogluu.realestate.enums.*;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    // Get all properties
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // Get property by id
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    // Create a property
    public Property createProperty(Property property) {
        return propertyRepository.save(property);
    }

    // Update property
    public Property updateProperty(Long id, Property property) {
        // Fetch the current property
        Optional<Property> currentProperty = propertyRepository.findById(id);

        Property currentPropertyObj = currentProperty.get();

        currentPropertyObj.setPropertyStatus(property.getPropertyStatus());
        currentPropertyObj.setOwnerId(property.getOwnerId());
        currentPropertyObj.setTenantId(property.getTenantId());

        return propertyRepository.save(currentPropertyObj);
    }
    // Delete property
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    // Search by keyword
    public List<Property> searchByKeyword(String keyword) {
        if (keyword == null) {
            return getAllProperties();
        }

        return propertyRepository.findByKeyword(keyword);
    }

    // Filter using property type, property status and heating type
    public List<Property> filterProperties(String propertyType, String propertyStatus, String heatingType) {
        PropertyType type = null;
        PropertyStatus status = null;
        HeatingType heating = null;

        if(!propertyType.equals("NONE")) {
            type = PropertyType.valueOf(propertyType);
        }

        if(!propertyStatus.equals("NONE")){
            status = PropertyStatus.valueOf(propertyStatus);
        }

        if(!heatingType.equals("NONE")) {
            heating = HeatingType.valueOf(heatingType);
        }

        return propertyRepository.findByFilters(type, status, heating);
    }
}
