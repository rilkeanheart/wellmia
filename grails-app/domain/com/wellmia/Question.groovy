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
    int rating = 0

    int numberOfViews = 0

    @Basic(fetch = FetchType.EAGER)
    Set<String> category = new HashSet<String>()

    @Basic(fetch = FetchType.EAGER)
    Set<String> notifyList = new HashSet<String>()

	// Key of Member who created comment
	String memberCreatorId

    @Basic(fetch = FetchType.EAGER)
	SortedSet<Answer> answers

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

    // TODO:  Use an interface to define a class of sortable domains by date (use for Status, Question, etc.)
    int compareTo(Object o) {
      int returnValue = 0

      if(o.rating != this.rating)
        returnValue = (this.rating < o.rating) ? -1 : 1

      return returnValue
    }
}
