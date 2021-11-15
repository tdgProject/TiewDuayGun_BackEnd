package int222.project.security.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class jwtObject {

    private String token;
    private long exp;

    public jwtObject(String accessToken, long exp) {
        this.token = accessToken;
        this.exp = exp;
    }

}
