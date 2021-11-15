package int222.project.controllers;


import int222.project.models.*;
import int222.project.payload.response.MessageResponse;
import int222.project.repositories.UserRepository;
import int222.project.services.FileService;
import int222.project.services.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
public class UserRestController {
	
	@Autowired
	private UserRepository userRepository;

	private FileService fileService = new UserFileService();

	@GetMapping("/users")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> listUsers() {
		return ResponseEntity.ok(userRepository.findAll());
	}

	@GetMapping("/user/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<?> userById(@PathVariable int id) {
		return ResponseEntity.ok(userRepository.findById(id).orElse(null));
	}

//	@PutMapping(value = "/user/role/{id}")
//	@PreAuthorize("hasAuthority('admin')")
//	public ResponseEntity<?> changeUserRole(@RequestPart User newUser,@PathVariable int id) {
//		User u = userRepository.findById(id).orElse(null);
//		switch (newUser.getRole().toString()){
//			case "member":
//				u.setRole(UserRole.member);
//			case "business":
//				u.setRole(UserRole.business);
//				break;
//			case "admin":
//				u.setRole(UserRole.admin);
//				break;
//			default:
//				break;
//		}
//		return ResponseEntity.ok(userRepository.saveAndFlush(u));
//	}

	@PutMapping(value = "/user/edit/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	@PreAuthorize("hasAuthority('member') or hasAuthority('business') or hasAuthority('admin')")
	public ResponseEntity<?> editUser(@RequestParam(value = "image", required = false) MultipartFile userImage,@RequestPart User newUser,@PathVariable int id) throws Exception{
		User u = userRepository.findById(id).orElse(null);

		if (userRepository.existsByUsername(newUser.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already in use!"));
		}

		if (userRepository.existsByTelNumber(newUser.getTelNumber())&&!(u.getTelNumber().equalsIgnoreCase(newUser.getTelNumber()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Telephone Number is already in use!"));
		}
		if (userRepository.existsByTelNumber(newUser.getTelNumber())&&!(u.getTelNumber().equalsIgnoreCase(newUser.getTelNumber()))) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Telephone Number is already in use!"));
		}
		userEdit(u, newUser);
		if (userImage != null) {
			if (!u.getImage().equalsIgnoreCase("default_user.png")) {
				fileService.delete(u.getImage());
			}
			u.setImage(fileService.save(userImage, u.getUsername()));
		}
		return ResponseEntity.ok(userRepository.saveAndFlush(u));
	}

	private void userEdit(User old,User u){
		old.setUsername(u.getUsername());
		old.setTelNumber(u.getTelNumber());
	}



}

