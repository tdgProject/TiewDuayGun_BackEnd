package int222.project.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tagplace")
@JsonIgnoreProperties(value = {"tagPlaceId"})
public class TagPlace {
	
	@EmbeddedId
	private TagPlacePK tagPlaceId;
	
	@ManyToOne(optional = false)
	@MapsId("placeId")
	@JoinColumn(name = "pid")
	@JsonBackReference
	private Place place;
	
	@ManyToOne(optional = false)
	@MapsId("tagId")
	@JoinColumn(name = "tagid")
	private Tag tag;
}
