package int222.project.controllers;


import java.util.List;

import int222.project.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import int222.project.models.*;
import int222.project.repositories.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class PlaceRestController {
	
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TagPlaceRepository tagPlaceRepository;

	private FileService fileService = new FileService();

	@GetMapping("")
	public List<Place> placeList() {
		return placeRepository.findAll();
	}

	@GetMapping("/place/{id}")
	public Place placeById(@PathVariable int id) {
		return placeRepository.findById(id).orElse(null);
	}

	@GetMapping("/image/{name}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String name) {
		Resource file = (Resource) fileService.loadAsResource(name);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
	}

	@PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Place create(@RequestParam(value = "image", required = false) MultipartFile placeImage, @RequestPart Place newPlace) throws Exception {

		if(placeImage != null) {
			newPlace.setImage(fileService.save(placeImage,newPlace.getPlacetName()));
		}
		addTagPlacePk(newPlace);
		return placeRepository.saveAndFlush(newPlace);
	}

	@PutMapping(value = "/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Place update(@RequestParam(value = "image", required = false) MultipartFile placeImage,@RequestPart Place newPlace,@PathVariable int id) {
		Place p = placeRepository.findById(id).orElse(null);
		editPlace(p,newPlace);
		if(placeImage != null) {
			fileService.delete(p.getImage());
			p.setImage(fileService.save(placeImage,p.getPlacetName()));
		}
		return placeRepository.saveAndFlush(p);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {
		Place p = placeRepository.findById(id).orElse(null);
		fileService.delete(p.getImage());
		placeRepository.deleteById(id);
		placeRepository.flush();
	}
	private void addTagPlacePk(Place place){
		for(TagPlace tp : place.getTags()){
			tp.setTagPlaceId(new TagPlacePK(place.getPlaceId(),tp.getTag().getTagId()));
			tp.setPlace(place);
		}
	}

	private void editPlace(Place old,Place p){
		old.setPlacetName(p.getPlacetName());
		old.setPlaceRating(p.getPlaceRating());
		old.setPlaceDescription(p.getPlaceDescription());
		tagPlaceRepository.deleteByPlaceId(old.getPlaceId());
		old.setTags(p.getTags());
		addTagPlacePk(old);
	}



}

