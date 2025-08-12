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
    public Optional<Property> createProperty(Property property) {
        return propertyRepository.save(property);
    }
}
