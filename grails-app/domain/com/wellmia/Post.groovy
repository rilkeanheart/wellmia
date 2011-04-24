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

    int numberOfViews = 0
    double rating = 0

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

    // TODO:  Use an interface to define a class of sortable domains by date (use for Status, Question, etc.)
    int compareTo(Object o) {
      int returnValue = 0

      if(o.rating != this.rating)
        returnValue = (this.rating < o.rating) ? -1 : 1

      return returnValue
    }
}
