package int222.project.controllers;


import int222.project.models.*;
import int222.project.repositories.UserRepository;
import int222.project.services.FileService;
import int222.project.services.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class UserRestController {
	
	@Autowired
	private UserRepository userRepository;

	private FileService fileService = new UserFileService();

	@GetMapping("/user/{id}")
	public User userById(@PathVariable int id) {
		return userRepository.findById(id).orElse(null);
	}

	@PutMapping(value = "/user/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public User editUser(@RequestParam(value = "image", required = false) MultipartFile userImage,@RequestPart User newUser,@PathVariable int id) {
		User u = userRepository.findById(id).orElse(null);
		userEdit(u,newUser);
		if(userImage != null) {
			if(u.getImage() != "default_user.png."){
				fileService.delete(u.getImage());
			}
			u.setImage(fileService.save(userImage,u.getUsername()));
		}
		return userRepository.saveAndFlush(u);
	}

	private void userEdit(User old,User u){
		old.setUsername(u.getUsername());
		old.setTelNumber(u.getTelNumber());
	}



}

