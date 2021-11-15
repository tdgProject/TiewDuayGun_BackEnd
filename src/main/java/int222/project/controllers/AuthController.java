package int222.project.controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import int222.project.models.*;
import int222.project.payload.request.*;
import int222.project.payload.response.*;
import int222.project.repositories.*;
import int222.project.security.jwt.*;
import int222.project.security.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwtObject token = jwtUtils.generateJwtToken(authentication);
            String jwt = token.getToken();
            long exp = token.getExp();
            UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    exp,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles,
                    userDetails.getImage(),
                    userDetails.getTelNumber()));
        }catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Invalid Email or Password"));
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        if (userRepository.existsByTelNumber(signUpRequest.getTel())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Telephone Number is already in use!"));
        }

        // Create new user's account
        User  user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        user.setTelNumber(signUpRequest.getTel());
        user.setImage("default_user.png");
        Set<String> strRoles = signUpRequest.getRole();

        if (strRoles == null) {
            user.setRole(UserRole.member);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "business":
                        user.setRole(UserRole.business);
                        break;
                    case "admin":
                        user.setRole(UserRole.admin);
                        break;
                    default:
                        user.setRole(UserRole.member);
                }
            });
        }
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
