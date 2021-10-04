package int222.project.controllers;

import int222.project.models.*;
import int222.project.repositories.HotelRepository;
import int222.project.repositories.NearByRepository;
import int222.project.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://13.76.86.65:8080")
public class HotelRestController {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private NearByRepository nearByRepository;

    @GetMapping("/hotels")
    public List<NearBy> listHotel(){
        return nearByRepository.findAll();
    }

    @GetMapping("/hotel/{id}")
    public List<NearBy> listHotelByPlaceId(@PathVariable int id) {
        return nearByRepository.findAllByPlaceId(id);
    }
}