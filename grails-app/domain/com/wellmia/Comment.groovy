package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class Comment implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

	String content
	Date dateCreated

	// Key of Member who created comment
	String memberCreatorId

	// Key of Item Member commented on
	String commentableItemId
    java.lang.Class commentableItemType

    static constraints = {
    	id visible:false
		content(blank:false)
		memberCreatorId(blank:false)
		commentableItemId(blank:false)
        commentableItemType(blank:false, inList:[com.wellmia.NewsItem,com.wellmia.Post])
	}

	String toString() {
		"${content}"
	}
}
