package int222.project.controllers;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import int222.project.models.*;
import int222.project.repositories.*;
import org.springframework.web.bind.annotation.*;


@RestController
public class PlaceRestController {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	@GetMapping("")
	public List<Place> placeList() {
		return placeRepository.findAll();
	}
	

	
}

