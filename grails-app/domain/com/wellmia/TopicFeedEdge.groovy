package com.wellmia

import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class TopicFeedEdge implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String categoryTagId
    String feedItemId
    Date   sortedDate

    String feedItemClassName

    static constraints = {
    	id visible:false
        categoryTagId(blank:false)
        feedItemId(blank:false)
        sortedDate(nullable:false)
	}
}
