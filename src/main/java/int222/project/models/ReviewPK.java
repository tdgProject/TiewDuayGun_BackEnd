package int222.project.models;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPK implements Serializable {
	
	static final long serialVersionUID = 1L;

	@Column(name = "pid")
	private Integer placeId;
	
	@Column(name = "uid")
	private Integer userId;
}
