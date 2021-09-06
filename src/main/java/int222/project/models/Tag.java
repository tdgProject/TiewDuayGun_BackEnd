package int222.project.models;

import java.util.List;
import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag")
public class Tag implements Comparable<Tag>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tagid", nullable = false)
	private Integer tagId;
	
	@Column(name = "tagname", nullable = false)
	private String tagName;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "typeid", referencedColumnName = "typeid")
	private TagType type;
	
	@OneToMany(mappedBy = "tag",fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
	private List<TagPlace> tagPlace;
	
	@Override
	public int compareTo(Tag other) {
		return this.tagId-other.tagId;
	}
}
