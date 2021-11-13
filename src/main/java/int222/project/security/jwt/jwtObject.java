package int222.project.security.jwt;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class jwtObject {

    private String token;
    private Date exp;

    public jwtObject(String accessToken, Date exp) {
        this.token = accessToken;
        this.exp = exp;
    }

}
