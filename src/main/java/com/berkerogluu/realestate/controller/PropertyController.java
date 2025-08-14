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
@CrossOrigin(origins = "*")
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

    // Get properties searching by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Property>> searchByKeyword(@RequestParam String keyword) {
        List<Property> properties = propertyService.searchByKeyword(keyword);
        return ResponseEntity.ok(properties);
    }

    // Filter properties by enum types
    @GetMapping("/filter")
    public ResponseEntity<List<Property>> filterProperties(@RequestParam(required = false) String propertyType, @RequestParam(required = false) String propertyStatus, @RequestParam(required = false) String heatingType) {
        List<Property> properties = propertyService.filterProperties(propertyType, propertyStatus, heatingType);
        return ResponseEntity.ok(properties);
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

    // Update property - apiKey is required !
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property property, @RequestParam String apiKey) {
        if(!companyService.checkApiKey(apiKey)) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Property> currentProperty = propertyService.getPropertyById(id);
        if (currentProperty.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Property updatedProperty = propertyService.updateProperty(id, property);
        return ResponseEntity.ok(updatedProperty);
    }

    // Delete property - apiKey is Required !
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id, @RequestParam String apiKey) {
        if(!companyService.checkApiKey(apiKey)) {
             return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        propertyService.deleteProperty(id);
        return ResponseEntity.ok().build();
    }

}
