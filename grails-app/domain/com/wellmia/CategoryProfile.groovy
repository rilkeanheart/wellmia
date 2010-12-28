package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class CategoryProfile implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String categoryName

    double thresholdSensitivity

    @Lob
    Map<String, Integer> ngramCounts

    @Lob
    Map<String, Integer> ngramRanks

    boolean isExpired

    static constraints = {
    	id visible:false
        categoryName(blank: false)
	}

  	String toString() {
		"${categoryName}"
	}
}
