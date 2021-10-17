package int222.project.controllers;

import int222.project.models.*;
import int222.project.repositories.HotelRepository;
import int222.project.repositories.NearByRepository;
import int222.project.repositories.PlaceRepository;
import int222.project.repositories.UserRepository;
import int222.project.services.FileService;
import int222.project.services.HotelFileService;
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
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private UserRepository userRepository;

    private FileService fileService = new HotelFileService();

    @GetMapping("/hotels")
    public List<Hotel> listAllHotel(){
        return hotelRepository.findAll();
    }

    @GetMapping("/hotel/{id}")
    public List<NearBy> listHotelByPlaceId(@PathVariable int id) {
        return nearByRepository.findAllByPlaceId(id);
    }

    @GetMapping("/hotel/user/{id}")
    public Hotel getMyHotel(@PathVariable int id) {
        List<Hotel> hList = hotelRepository.getHotelByOwnerId(id);
        Hotel hotel = null;
        for(Hotel h : hList){
            hotel = h;
        }
        return hotel;
    }

    @GetMapping("/image/hotel/{name}")
    @ResponseBody
    public ResponseEntity<org.springframework.core.io.Resource> getHotelImage(@PathVariable String name) {
        org.springframework.core.io.Resource file = (Resource) fileService.loadAsResource(name);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }

    @PostMapping(value = "/hotel/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Hotel addHotel(@RequestParam(value = "image", required = false) MultipartFile hotelImage, @RequestPart Hotel newHotel) {
        if(hotelImage != null) {
            newHotel.setImage(fileService.save(hotelImage, newHotel.getHotelName()));
        }
        User user = userRepository.findById(newHotel.getOwner().getUserId()).orElse(null);
        newHotel.setOwner(user);
        return hotelRepository.saveAndFlush(newHotel);
    }

    @PutMapping(value = "/hotel/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Hotel editHotel(@RequestParam(value = "image", required = false) MultipartFile hotelImage,@RequestPart Hotel newHotel,@PathVariable int id) {
        Hotel h = hotelRepository.findById(id).orElse(null);
        h.setHotelName(newHotel.getHotelName());
        h.setAddress(newHotel.getAddress());
        h.setEmail(newHotel.getEmail());
        h.setTelNumber(newHotel.getTelNumber());
        if(hotelImage != null) {
            fileService.delete(h.getImage());
            h.setImage(fileService.save(hotelImage, h.getHotelName()));
        }
        return hotelRepository.saveAndFlush(h);
    }

    @PostMapping(value = "/nearby/add/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public NearBy addNearby(@RequestPart NearBy newNearBy,@PathVariable int id) throws Exception {
        newNearBy.setNearById(new NearByPK(id,newNearBy.getHotel().getHotelId()));
        newNearBy.setHotel(hotelRepository.findById(newNearBy.getHotel().getHotelId()).orElse(null));
        newNearBy.setPlace(placeRepository.findById(id).orElse(null));
        return nearByRepository.saveAndFlush(newNearBy);
    }

    @DeleteMapping("/nearby/delete/{pid}/{hid}")
    public void deleteNearBy(@PathVariable int pid,@PathVariable int hid) {
        nearByRepository.deleteById(new NearByPK(pid,hid));
        nearByRepository.flush();
    }
}