package int222.project.controllers;

import int222.project.models.*;
import int222.project.repositories.PlaceRepository;
import int222.project.repositories.ReviewRepository;
import int222.project.repositories.UserRepository;
import int222.project.services.FileService;
import int222.project.services.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://13.76.86.65:8080")
public class ReviewRestController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;

    private FileService fileService = new UserFileService();

    @GetMapping("/reviews")
    public List<Review> listReviews() {
        return reviewRepository.findAll();
    }

    @GetMapping("/review/{id}")
    public List<Review> listReviewsByPlaceId(@PathVariable int id) {
        return reviewRepository.findAllByPlaceId(id);
    }

    @GetMapping("/image/user/{name}")
    @ResponseBody
    public ResponseEntity<Resource> getUserFile(@PathVariable String name) {
        Resource file = (Resource) fileService.loadAsResource(name);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }

    @PostMapping(value = "/review/add/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Review addReview(@RequestPart Review newReview,@PathVariable int id) throws Exception {
        newReview.setReviewId(new ReviewPK(id,newReview.getUser().getUserId()));
        newReview.setUser(userRepository.findById(newReview.getUser().getUserId()).orElse(null));
        newReview.setPlace(placeRepository.findById(id).orElse(null));
        return reviewRepository.saveAndFlush(newReview);
    }

    @PutMapping(value = "/review/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Review editReview(@RequestPart Review newReview,@PathVariable int id) {
        Review r = reviewRepository.findById(new ReviewPK(id,newReview.getUser().getUserId())).orElse(null);
        r.setReview(newReview.getReview());
        r.setRating(newReview.getRating());
        return reviewRepository.saveAndFlush(r);
    }

    @DeleteMapping("/review/delete/{pid}/{uid}")
    public void deleteReview(@PathVariable int pid,@PathVariable int uid) {
        reviewRepository.deleteById(new ReviewPK(pid,uid));
        placeRepository.flush();
    }

}
