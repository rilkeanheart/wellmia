package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class Symptom implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String symptomTypeId
    String severity
    String comments


    static constraints = {
    	id visible:false
        symptomTypeId(blank:false)
        severity(inList:["Mild","Moderate","Severe"])
        comments(blank:true)
	}
}
