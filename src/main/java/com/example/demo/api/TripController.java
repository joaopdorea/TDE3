package com.example.demo.api;


import com.example.demo.Repository.TripRepository;
import com.example.demo.model.Trip;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TripController {

    @Autowired
    private final TripRepository tripRepository;

    @GetMapping("/trips")
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @GetMapping(value = "/trip/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return tripRepository.findById(id)
                .map(trip -> ResponseEntity.ok(trip))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/trip")
    private ResponseEntity<String> addDriver(@RequestBody Trip trip){
        tripRepository.save(trip);
        log.info("Trip data added");
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }


    @DeleteMapping("/trip/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        if (!tripRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tripRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/trip/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        return tripRepository.findById(id)
                .map(trip -> {
                    trip.setDestino(tripDetails.getDestino());
                    trip.setDriver(tripDetails.getDriver()); // Se você quiser atualizar o driver também
                    Trip updatedTrip = tripRepository.save(trip);
                    return ResponseEntity.ok(updatedTrip);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
