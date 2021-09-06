package int222.project.models;

import javax.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tagplace")
//@JsonIgnoreProperties(value = {"productColorId"})
public class TagPlace {
	
	@EmbeddedId
	private TagPlacePK tagPlaceId;
	
	@ManyToOne(optional = false)
	@MapsId("placeId")
	@JoinColumn(name = "pid")
	private Place place;
	
	@ManyToOne(optional = false)
	@MapsId("tagId")
	@JoinColumn(name = "tagid")
	private Tag tag;
}
