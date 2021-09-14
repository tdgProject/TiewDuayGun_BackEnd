package int222.project.models;

import java.io.Serializable;
import java.util.*;
import lombok.*;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "place")
public class Place implements Serializable,Comparable<Place>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pid", nullable = false)
	private Integer placeId;
	
	@Column(name = "pname", nullable = false, length = 50)
	private String placetName;
	
	@Column(name = "rating", precision = 4, scale = 2)
	private double placeRating;
	
	@Column(name = "pdescription", length = 1000)
	private String productDescription;

	@Column(name = "pimage")
	private String image;
	
	@OneToMany(mappedBy = "place",fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	private List<TagPlace> tags;
	
	@OneToMany(mappedBy = "place",fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "place",fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	private List<NearBy> hotels;
	
	@Override
	public int compareTo(Place other) {
		return this.placeId-other.placeId;
	}

}
