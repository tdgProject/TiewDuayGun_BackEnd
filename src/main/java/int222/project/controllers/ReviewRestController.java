package int222.project.controllers;

import int222.project.models.*;
import int222.project.repositories.PlaceRepository;
import int222.project.repositories.ReviewRepository;
import int222.project.repositories.UserRepository;
import int222.project.services.FileService;
import int222.project.services.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
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

    @GetMapping("/review/user/{id}")
    @PreAuthorize("hasAuthority('business') or hasAuthority('admin')")
    public List<Review> listReviewsByUserId(@PathVariable int id) {
        return reviewRepository.findAllByUserId(id);
    }

    @GetMapping("/image/user/{name}")
    @ResponseBody
    public ResponseEntity<Resource> getUserImage(@PathVariable String name) {
        Resource file = (Resource) fileService.loadAsResource(name);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }
    @PreAuthorize("hasAuthority('member') or hasAuthority('business') or hasAuthority('admin')")
    @PostMapping(value = "/review/add/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Review addReview(@RequestPart Review newReview,@PathVariable int id) throws Exception {
        newReview.setReviewId(new ReviewPK(id,newReview.getUser().getUserId()));
        newReview.setUser(userRepository.findById(newReview.getUser().getUserId()).orElse(null));
        newReview.setPlace(placeRepository.findById(id).orElse(null));
        return reviewRepository.saveAndFlush(newReview);
    }
    @PreAuthorize("hasAuthority('member') or hasAuthority('business') or hasAuthority('admin')")
    @PutMapping(value = "/review/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Review editReview(@RequestPart Review newReview,@PathVariable int id) {
        Review r = reviewRepository.findById(new ReviewPK(id,newReview.getUser().getUserId())).orElse(null);
        r.setReview(newReview.getReview());
        r.setRating(newReview.getRating());
        return reviewRepository.saveAndFlush(r);
    }
    @PreAuthorize("hasAuthority('member') or hasAuthority('business') or hasAuthority('admin')")
    @DeleteMapping("/review/delete/{pid}/{uid}")
    public void deleteReview(@PathVariable int pid,@PathVariable int uid) {
        reviewRepository.deleteById(new ReviewPK(pid,uid));
        placeRepository.flush();
    }

}
