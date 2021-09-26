package int222.project.services;

import java.nio.file.Paths;

public class PlaceFileService extends FileService{

    public PlaceFileService(){
        super.rootLocation = Paths.get("./public/images/place");
    }
}
