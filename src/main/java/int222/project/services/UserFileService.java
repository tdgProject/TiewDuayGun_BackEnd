package int222.project.services;

import java.nio.file.Paths;

public class UserFileService extends FileService{

    public UserFileService(){
        super.rootLocation = Paths.get("./public/images/user");
    }
}
