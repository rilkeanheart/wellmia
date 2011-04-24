package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class FeedEdge implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String consumerId
    String feedItemId
    Date   sortedDate
    boolean isFollowed = false

    String feedItemClassName

    static constraints = {
    	id visible:false
        consumerId(blank:false)
        feedItemId(blank:false)
        sortedDate(nullable:false)
	}
}
