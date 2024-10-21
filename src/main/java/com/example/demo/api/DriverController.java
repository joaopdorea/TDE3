package com.example.demo.api;

import com.example.demo.Repository.DriverRepository;
import com.example.demo.model.Driver;
import com.example.demo.model.Trip;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
public class DriverController {


    @Autowired
    private final DriverRepository driverRepository;

    @GetMapping(value = "/drivers" , produces = {"application/json", "application/xml"})
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @PostMapping(value = "/driver", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    private ResponseEntity<String> addDriver(@RequestBody Driver driver){
        driverRepository.save(driver);
        log.info("Driver data added");
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping(value = "/{id}/trips", produces = {"application/json", "application/xml"})
    public List<Trip> getTripsByDriver(@PathVariable Long id) {
        return driverRepository.findById(id)
                .map(Driver::getTrips)
                .orElseThrow(() -> new RuntimeException("Driver not found"));
    }


    @GetMapping(value = "/driver/{id}", produces =  {"application/json", "application/xml"})
    public ResponseEntity<Driver> getDriverById(@PathVariable Long id) {
        return driverRepository.findById(id)
                .map(driver -> ResponseEntity.ok(driver))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/driver/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Void> deleteDriver(@PathVariable Long id) {
        if (!driverRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        driverRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping(value = "/driver/{id}", consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public ResponseEntity<Driver> updateDriver(@PathVariable Long id, @RequestBody Driver driverDetails) {
        return driverRepository.findById(id)
                .map(driver -> {
                    driver.setName(driverDetails.getName());
                    Driver updatedDriver = driverRepository.save(driver);
                    return ResponseEntity.ok(updatedDriver);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
