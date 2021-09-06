package int222.project.models;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review {
	@EmbeddedId
	private ReviewPK reviewId;
	
	@ManyToOne(optional = false)
	@MapsId("placeId")
	@JoinColumn(name = "pid")
	private Place place;
	
	@ManyToOne(optional = false)
	@MapsId("userId")
	@JoinColumn(name = "uid")
	private User user;
	
	@Column(name = "review",length = 2000)
	private String review;
	
	@Column(name = "rating")
	private Double rating;
}
