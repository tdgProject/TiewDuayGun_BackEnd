package int222.project.models;

import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hotel")
public class Hotel implements Comparable<Hotel> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hid", nullable = false)
	private Integer hotelId;
	
	@Column(name = "hname", nullable = false, length = 50)
	private String hotelName;
	
	@Column(name = "tel",length = 50)
	private String telNumber;
	
	@Column(name = "address", length = 400)
	private String address;
	
	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "himage")
	private String image;
	
//	@OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
//	private List<NearBy> place;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User owner;
	
	@Override
	public int compareTo(Hotel other) {
		return this.hotelId-other.hotelId;
	}
}
