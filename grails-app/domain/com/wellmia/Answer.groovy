package com.wellmia


import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class Answer implements Serializable, Comparable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

	String subject
    String content
	Date dateCreated = new Date()
    String memberCreatorUsername
    double answerRating;

	// Key of Member who created comment
	String memberCreatorId

	// Key of Item Member commented on
	String questionId

    static constraints = {
    	id visible:false
		content(blank:false)
		memberCreatorId(blank:false)
        memberCreatorUsername(blank:false)
		questionId(blank:false)
	}

	String toString() {
		"${content}"
	}

    // TODO:  Use an interface to define a class of sortable domains by date (use for Status, Question, etc.)
    int compareTo(Object o) {
      int returnValue = 0

      if(o.dateCreated != this.dateCreated)
        returnValue = (this.dateCreated < o.dateCreated) ? -1 : 1

      return returnValue
    }
}
