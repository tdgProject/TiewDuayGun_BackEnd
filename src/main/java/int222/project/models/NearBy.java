package int222.project.models;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nearby")
public class NearBy {
	
	@EmbeddedId
	private NearByPK NearById;
	
	@ManyToOne(optional = false)
	@MapsId("placeId")
	@JoinColumn(name = "pid")
	private Place place;
	
	@ManyToOne(optional = false)
	@MapsId("hotelId")
	@JoinColumn(name = "hid")
	private Hotel hotel;
}

