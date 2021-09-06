package int222.project.models;

import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tagtype")
public class TagType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "typeid", nullable = false)
	private Integer typeId;
	
	@Column(name = "typename", nullable = false, length = 20)
	private String typeName;
	
	@OneToMany(mappedBy = "type",fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	private List<Tag> tag;
	
}
