package int222.project.services;

import java.nio.file.Paths;

public class HotelFileService extends FileService{

    public HotelFileService(){
        super.rootLocation = Paths.get("./public/images/hotel");
    }
}
