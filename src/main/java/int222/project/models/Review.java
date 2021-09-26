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
@Table(name = "review")
@JsonIgnoreProperties(value = {"reviewId"})
public class Review {
	@EmbeddedId
	@JsonManagedReference
	private ReviewPK reviewId;

	@JsonBackReference(value = "review-place")
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
