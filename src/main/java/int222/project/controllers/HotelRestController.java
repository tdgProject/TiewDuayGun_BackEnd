package int222.project.controllers;

import int222.project.models.*;
import int222.project.repositories.HotelRepository;
import int222.project.repositories.NearByRepository;
import int222.project.repositories.ReviewRepository;
import int222.project.services.FileService;
import int222.project.services.HotelFileService;
import int222.project.services.PlaceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class HotelRestController {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private NearByRepository nearByRepository;

    private FileService fileService = new HotelFileService();

    @GetMapping("/hotels")
    public List<NearBy> listHotel(){
        return nearByRepository.findAll();
    }

    @GetMapping("/hotel/{id}")
    public List<NearBy> listHotelByPlaceId(@PathVariable int id) {
        return nearByRepository.findAllByPlaceId(id);
    }
    @GetMapping("/image/hotel/{name}")
    @ResponseBody
    public ResponseEntity<org.springframework.core.io.Resource> getHotelImage(@PathVariable String name) {
        org.springframework.core.io.Resource file = (Resource) fileService.loadAsResource(name);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }
}