package int222.project.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nearby")
@JsonIgnoreProperties(value = {"NearById"})
public class NearBy {
	
	@EmbeddedId
	private NearByPK NearById;

	@JsonBackReference
	@ManyToOne(optional = false)
	@MapsId("placeId")
	@JoinColumn(name = "pid")
	private Place place;
	
	@ManyToOne(optional = false)
	@MapsId("hotelId")
	@JoinColumn(name = "hid")
	private Hotel hotel;
}

