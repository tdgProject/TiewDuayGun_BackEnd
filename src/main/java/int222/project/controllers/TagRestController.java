package int222.project.controllers;

import int222.project.models.Tag;
import int222.project.models.TagType;
import int222.project.repositories.TagRepository;
import int222.project.repositories.TagTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class TagRestController {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagTypeRepository tagTypeRepository;

    @GetMapping("/tags")
    public List<Tag> tags(){
        return tagRepository.findAll();
    }

    @GetMapping("/types")
    public List<TagType> types(){
        return tagTypeRepository.findAll();
    }
}
