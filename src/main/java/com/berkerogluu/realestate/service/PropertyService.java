package com.berkerogluu.realestate.service;

import com.berkerogluu.realestate.model.Property;
import com.berkerogluu.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<Property> currentProperty = propertyRepository.findById(id).get();

        currentProperty.setPropertyStatus(property.getPropertyStatus());
        currentProperty.setOwnerId(property.getOwnerId());
        currentProperty.setTenantId(property.getTenantId());

    }
    // Delete property
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}
