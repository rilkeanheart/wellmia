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
    boolean isDrug

    // The following fields only apply if isDrug is TRUE
    String applicationNumber
    String productNumber
    String form
    String dosage
    String productMarketStatus
    String tecCode
    boolean isReferenceDrug
    String activeIngredients

    //@Basic
    //Set<String> interestCategories = new HashSet<String>()

    static constraints = {
    	id visible:false
        treatmentTypeName(blank:false, unique:false)
        isDrug(nullable:false)
	}

    String toString() {
		"$treatmentTypeName" + (activeIngredients == treatmentTypeName) ? "" : "  ($activeIngredients)"
	}
}
