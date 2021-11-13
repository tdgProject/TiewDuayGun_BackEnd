package int222.project.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private Date exp;
    private String type = "Bearer";
    private Integer id;
    private String username;
    private String email;
    private List<String> roles;
    private String image;
    private String TelNumber;

    public JwtResponse(String accessToken,Date exp, Integer id, String username, String email, List<String> roles, String image,String TelNumber) {
        this.token = accessToken;
        this.exp = exp;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.image = image;
        this.TelNumber = TelNumber;
    }


}
