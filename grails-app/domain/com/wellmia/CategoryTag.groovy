package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;
// import com.google.appengine.api.datastore.Key;

@Entity
class CategoryTag implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String category

    @Basic
    double thresholdSensitivity

    static constraints = {
    	id visible:false
        category(blank: false)
        thresholdSensitivity(blank:false)
	}
}
