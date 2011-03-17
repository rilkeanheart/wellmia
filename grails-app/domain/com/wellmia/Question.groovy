package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class Question implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

	String subject
    String content
	Date dateCreated = new Date()
    String memberCreatorUsername

    Set<String> category = new HashSet<String>()

	// Key of Member who created comment
	String memberCreatorId

	@Basic
	@OneToMany(cascade=CascadeType.ALL,
               fetch = FetchType.EAGER)
	SortedSet<Comment> comments

    static constraints = {
    	id visible:false
        subject(blank:false)
		content(blank:false)
		memberCreatorId(blank:false)
        memberCreatorUsername(blank:false)
    }

	static mapping = {
		sort dateCreated
	}

    String toString() {
		"${content}"
	}
}
