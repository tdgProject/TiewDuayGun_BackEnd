package int222.project.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagPlacePK implements Serializable {
	
	static final long serialVersionUID = 1L;

	@Column(name = "pid")
	private Integer placeId;
	
	@Column(name = "tagid")
	private Integer tagId;
}
