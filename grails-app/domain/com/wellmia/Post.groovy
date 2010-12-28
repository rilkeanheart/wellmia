package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class Post implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String postTypeId
	String content
	Date dateCreated = new Date()
	String urlLink

    Set<String> categories = new HashSet<String>()

	// Key of Member who created comment
	String memberCreatorId


    static constraints = {
    	id visible:false
		content(blank:false)
		memberCreatorId(blank:false)
	    postTypeId(blank:false)
        urlLink(url: true, blank:true)
    }

	String toString() {
		"${content}"
	}
}
