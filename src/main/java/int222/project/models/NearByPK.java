package int222.project.models;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NearByPK implements Serializable {
	
	static final long serialVersionUID = 1L;

	@Column(name = "placeid")
	private Integer placeId;
	
	@Column(name = "hid")
	private Integer hotelId;
}
