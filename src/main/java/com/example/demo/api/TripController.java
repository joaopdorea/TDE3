package com.example.demo.api;


import com.example.demo.Repository.TripDAO;
import com.example.demo.model.Trip;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class TripController {

    @Autowired
    private final TripDAO tripRepository = null;

    @GetMapping(value = "/trips", produces = {"application/json", "application/xml"})
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    @GetMapping(value = "/trip/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return tripRepository.findById(id)
                .map(trip -> ResponseEntity.ok(trip))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/trip", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    private ResponseEntity<String> addDriver(@RequestBody Trip trip){
        tripRepository.save(trip);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }


    @DeleteMapping(value = "/trip/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        if (!tripRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tripRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/trip/{id}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml"})
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        return tripRepository.findById(id)
                .map(trip -> {
                    trip.setDestino(tripDetails.getDestino());
                    trip.setDriver(tripDetails.getDriver());
                    trip.setOrigem(tripDetails.getOrigem());
                    trip.setValor(tripDetails.getValor());
                    Trip updatedTrip = tripRepository.save(trip);
                    return ResponseEntity.ok(updatedTrip);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
