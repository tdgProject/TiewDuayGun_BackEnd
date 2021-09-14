package int222.project.models;

import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User implements Comparable<User>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid", nullable = false)
	private Integer userId;
	
	@Column(name = "uname", nullable = false, length = 50)
	private String username;
	
	@Column(name = "password",nullable = false,length = 20)
	private String password;
	
	@Column(name = "role",nullable = false)
	private String role;
	
	@Column(name = "tel", length = 10)
	private String telNumber;
	
	@Column(name = "email",nullable = false)
	private String email;

	@Column(name = "uimage")
	private String image;

	@JsonBackReference
	@OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	private List<Review> reviews;
	  
	@Override
	public int compareTo(User other) {
		return this.userId-other.userId;
	}
}
