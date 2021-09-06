package int222.project.controllers;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;

import int222.project.models.*;
import int222.project.repositories.*;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class PlaceRestController {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@GetMapping("")
	public List<Place> placeList() {
		return placeRepository.findAll();
	}
	

	
}

