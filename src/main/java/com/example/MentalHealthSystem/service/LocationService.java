package com.example.MentalHealthSystem.service;


import com.example.MentalHealthSystem.Database.Doctor;
import com.example.MentalHealthSystem.Database.Location;
import com.example.MentalHealthSystem.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {


    private LocationRepository locationRepository;
    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getLocationByDoctor(Doctor doctor) {
        return locationRepository.findByDoctor(doctor);
    }

    public Location saveLocation(double latitude, double longitude, Doctor doctor) {
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setDoctor(doctor);
        return locationRepository.save(location);
    }
}
