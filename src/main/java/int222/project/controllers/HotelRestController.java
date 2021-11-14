package int222.project.controllers;

import int222.project.models.*;
import int222.project.payload.response.MessageResponse;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/" )
@RestController
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
    public ResponseEntity<?> listAllHotel(){
        return ResponseEntity.ok(hotelRepository.findAll());
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<?> listHotelByPlaceId(@PathVariable int id) {
        return ResponseEntity.ok(nearByRepository.findAllByPlaceId(id));
    }

    @GetMapping("/hotel/user/{id}")
    @PreAuthorize("hasAuthority('business') or hasAuthority('admin')")
    public ResponseEntity<?> getMyHotel(@PathVariable int id) {
        List<Hotel> hList = hotelRepository.getHotelByOwnerId(id);
        Hotel hotel = null;
        for(Hotel h : hList){
            hotel = h;
        }
        return ResponseEntity.ok(hotel);
    }

    @GetMapping("/image/hotel/{name}")
    @ResponseBody
    public ResponseEntity<Resource> getHotelImage(@PathVariable String name) {
        org.springframework.core.io.Resource file = (Resource) fileService.loadAsResource(name);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }

    @PostMapping(value = "/hotel/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('business') or hasAuthority('admin')")
    public ResponseEntity<?> addHotel(@RequestParam(value = "image", required = false) MultipartFile hotelImage, @RequestPart Hotel newHotel) {
        if (hotelRepository.existsByHotelName(newHotel.getHotelName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: HotelName is already exists!"));
        }
        if (hotelRepository.existsByEmail(newHotel.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already exists!"));
        }
        if (hotelRepository.existsByTelNumber(newHotel.getTelNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Telephone Number is already exists!"));
        }
        if(hotelImage != null) {
            newHotel.setImage(fileService.save(hotelImage, newHotel.getHotelName()));
        }
        User user = userRepository.findById(newHotel.getOwner().getUserId()).orElse(null);
        newHotel.setOwner(user);
        return ResponseEntity.ok(hotelRepository.saveAndFlush(newHotel));
    }

    @PutMapping(value = "/hotel/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('business') or hasAuthority('admin')")
    public ResponseEntity<?> editHotel(@RequestParam(value = "image", required = false) MultipartFile hotelImage,@RequestPart Hotel newHotel,@PathVariable int id) {
        Hotel h = hotelRepository.findById(id).orElse(null);
        if (hotelRepository.existsByEmail(newHotel.getEmail())&&!(h.getEmail().equalsIgnoreCase(newHotel.getEmail()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already exists!"));
        }
        if (hotelRepository.existsByTelNumber(newHotel.getTelNumber())&&!(h.getTelNumber().equalsIgnoreCase(newHotel.getTelNumber()))) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Telephone Number is already exists!"));
        }
        h.setHotelName(newHotel.getHotelName());
        h.setAddress(newHotel.getAddress());
        h.setEmail(newHotel.getEmail());
        h.setTelNumber(newHotel.getTelNumber());
        if(hotelImage != null) {
            fileService.delete(h.getImage());
            h.setImage(fileService.save(hotelImage, h.getHotelName()));
        }
        return ResponseEntity.ok(hotelRepository.saveAndFlush(h));
    }

    @PostMapping(value = "/nearby/add/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('business') or hasAuthority('admin')")
    public ResponseEntity<?> addNearby(@RequestPart NearBy newNearBy,@PathVariable int id) throws Exception {
        newNearBy.setNearById(new NearByPK(id,newNearBy.getHotel().getHotelId()));
        newNearBy.setHotel(hotelRepository.findById(newNearBy.getHotel().getHotelId()).orElse(null));
        newNearBy.setPlace(placeRepository.findById(id).orElse(null));
        return ResponseEntity.ok(nearByRepository.saveAndFlush(newNearBy));
    }

    @DeleteMapping("/nearby/delete/{pid}/{hid}")
    @PreAuthorize("hasAuthority('business') or hasAuthority('admin')")
    public void deleteNearBy(@PathVariable int pid,@PathVariable int hid) {
        nearByRepository.deleteById(new NearByPK(pid,hid));
        nearByRepository.flush();
    }
}