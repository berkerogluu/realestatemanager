package com.berkerogluu.realestate.controller;

import com.berkerogluu.realestate.model.Property;
import com.berkerogluu.realestate.service.PropertyService;
import com.berkerogluu.realestate.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private CompanyService companyService; // Required for checkApiKey() method

    // Get all properties - apiKey not required
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        List<Property> properties = propertyService.getAllProperties();
        return ResponseEntity.ok(properties);
    }

    // Get property by id - apiKey not required
    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        Optional<Property> property = propertyService.getPropertyById(id);
        return property.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create a property - apiKey is required !
    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody Property property, @RequestParam String apiKey) {
        if(!companyService.checkApiKey(apiKey)) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Property newProperty = propertyService.createProperty(property);
        return ResponseEntity.ok(newProperty);
    }

}
