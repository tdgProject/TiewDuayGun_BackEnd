package int222.project.controllers;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import int222.project.services.FileService;
import int222.project.services.PlaceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import int222.project.models.*;
import int222.project.repositories.*;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class PlaceRestController {
	
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private TagPlaceRepository tagPlaceRepository;
	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private ReviewRepository reviewRepository;

	private FileService fileService = new PlaceFileService();

	@GetMapping("/onstart")
	public void onstart() {
		List<Place> pl = placeRepository.findAll();
		ratingCal(pl);
		placeRepository.saveAllAndFlush(pl);
	}

	@GetMapping("/places")
	public List<Place> placeList() {
		return placeRepository.findAll();
	}

	@GetMapping("/place/tag/{id}")
	public List<Place> placeByTagId(@PathVariable int id) {
		return tagPlaceRepository.listPLaceByTagId(id);
	}

	@GetMapping("/place/name/{name}")
	public List<Place> placeByName(@PathVariable String name) {
		return placeRepository.listPLaceByName(name);
	}

	@GetMapping("/place/{id}")
	public Place placeById(@PathVariable int id) {
		return placeRepository.findById(id).orElse(null);
	}

	@GetMapping("/image/place/{name}")
	@ResponseBody
	public ResponseEntity<Resource> getPlaceImage(@PathVariable String name) {
		Resource file = (Resource) fileService.loadAsResource(name);
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
	}

	@PostMapping(value = "/place/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Place addPlace(@RequestParam(value = "image", required = false) MultipartFile placeImage, @RequestPart Place newPlace) {

		if(placeImage != null) {
			newPlace.setImage(fileService.save(placeImage, newPlace.getPlaceName()));
		}
		addTagPlace(newPlace);
		return placeRepository.saveAndFlush(newPlace);
	}

	@PutMapping(value = "/place/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Place editPlace(@RequestParam(value = "image", required = false) MultipartFile placeImage,@RequestPart Place newPlace,@PathVariable int id) {
		Place p = placeRepository.findById(id).orElse(null);
		editPlace(p,newPlace);
		if(placeImage != null) {
			fileService.delete(p.getImage());
			p.setImage(fileService.save(placeImage,p.getPlaceName()));
		}
		return placeRepository.saveAndFlush(p);
	}

	@DeleteMapping("/place/delete/{id}")
	public void deletePlace(@PathVariable int id) {
		Place p = placeRepository.findById(id).orElse(null);
		fileService.delete(p.getImage());
		placeRepository.deleteById(id);
		placeRepository.flush();
	}
	private void addTagPlace(Place place){
		for(TagPlace tp : place.getTags()){
			tp.setTagPlaceId(new TagPlacePK(place.getPlaceId(),tp.getTag().getTagId()));
			tp.setTag(tagRepository.findById(tp.getTag().getTagId()).orElse(null));
			tp.setPlace(place);
		}
	}

	private void editPlace(Place old,Place p){
		old.setPlaceName(p.getPlaceName());
		old.setPlaceRating(p.getPlaceRating());
		old.setPlaceDescription(p.getPlaceDescription());
		tagPlaceRepository.deleteByPlaceId(old.getPlaceId());
		old.setTags(p.getTags());
		addTagPlace(old);
	}

	private void ratingCal(List<Place> list){
		for(Place p : list){
			double pRating = 0.0,uRating = 0.0;
			List<Review> reviews = reviewRepository.findAllByPlaceId(p.getPlaceId());
			if(reviews.size()>0){
				for(Review r : reviews){
					uRating+=r.getRating();
				}
				pRating=uRating/reviews.size();
				p.setPlaceRating(pRating);
			}
		}
	}



}

