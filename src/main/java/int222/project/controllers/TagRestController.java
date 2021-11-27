package int222.project.controllers;

import int222.project.models.Place;
import int222.project.models.Tag;
import int222.project.models.TagPlace;
import int222.project.models.TagType;
import int222.project.repositories.TagPlaceRepository;
import int222.project.repositories.TagRepository;
import int222.project.repositories.TagTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/" )
@RestController
<<<<<<< HEAD
@CrossOrigin(origins = "http://13.76.86.65:8080")
=======
>>>>>>> 622c081fd32dd55f45c44a02e6b99fc19c902a36
public class TagRestController {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private TagTypeRepository tagTypeRepository;
    @Autowired
    private TagPlaceRepository tagPlaceRepository;

    @GetMapping("/tags")
    public ResponseEntity<?> tags(){
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @GetMapping("/types")
    public ResponseEntity<?>types(){
        return ResponseEntity.ok(tagTypeRepository.findAll());
    }

    @GetMapping("/tags/province")
    public ResponseEntity<?> provinceTags(){
        return ResponseEntity.ok(tagTypeRepository.listProvinceTag());
    }

    @GetMapping("/tags/etc")
    public ResponseEntity<?> etcTags(){
        return ResponseEntity.ok(tagTypeRepository.listEtcTag());
    }

    @GetMapping("/types/count")
    public ResponseEntity<?> typesWithCount(){
        List<TagType> ttList = tagTypeRepository.findAll();
        for (TagType tt : ttList){
            for(Tag t : tt.getTag()){
                List<Place> tpList = tagPlaceRepository.listPLaceByTagId(t.getTagId());
                t.setCount(tpList.size()) ;
            }
        }

        return ResponseEntity.ok(ttList);

    }
}
