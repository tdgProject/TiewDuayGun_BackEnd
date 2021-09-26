package int222.project.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
	@JsonManagedReference
	private NearByPK NearById;

	@JsonBackReference(value = "nearby-place")
	@ManyToOne(optional = false)
	@MapsId("placeId")
	@JoinColumn(name = "pid")
	private Place place;
	
	@ManyToOne(optional = false)
	@MapsId("hotelId")
	@JoinColumn(name = "hid")
	private Hotel hotel;
}

