package int222.project.controllers;

import int222.project.models.Tag;
import int222.project.models.TagPlace;
import int222.project.models.TagType;
import int222.project.repositories.TagPlaceRepository;
import int222.project.repositories.TagRepository;
import int222.project.repositories.TagTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://13.76.86.65:8080")
public class TagRestController {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagTypeRepository tagTypeRepository;
    @Autowired
    private TagPlaceRepository tagPlaceRepository;

    @GetMapping("/tags")
    public List<Tag> tags(){
        return tagRepository.findAll();
    }

    @GetMapping("/types")
    public List<TagType> types(){
        return tagTypeRepository.findAll();
    }

    @GetMapping("/tags/province")
    public List<Tag> provinceTags(){
        return tagTypeRepository.listProvinceTag();
    }

    @GetMapping("/tags/etc")
    public List<Tag> etcTags(){
        return tagTypeRepository.listEtcTag();
    }
}
