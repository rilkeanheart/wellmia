package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class Endpoint implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String endPointTypeId
    Date observationDate
    Long quantitativeMeasure1
    Long quantitativeMeasure2
    String qualitativeMeasure1
    String qualitativeMeasure2


    static constraints = {
    	id visible:false
        endPointTypeId(blank:false)
        observationDate(nullable:false)
	}
}
