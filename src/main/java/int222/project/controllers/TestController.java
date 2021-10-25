package int222.project.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/member")
    @PreAuthorize("hasAuthority('member') or hasAuthority('business') or hasAuthority('admin')")
    public String userAccess() {
        return "Member Content.";
    }

    @GetMapping("/business")
    @PreAuthorize("hasAuthority('business') or hasAuthority('admin')")
    public String moderatorAccess() {
        return "Business Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('admin')")
    public String adminAccess() {
        return "Admin Content.";
    }
}
