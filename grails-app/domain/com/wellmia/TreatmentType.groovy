package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class TreatmentType implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String treatmentTypeName

    String treatmentCategoryTypeId
    boolean isDosable = true
    boolean isInjectable = false

    static constraints = {
    	id visible:false
        treatmentTypeName(blank:false, unique:true)
        treatmentCategoryTypeId(blank:false)
	}
}
