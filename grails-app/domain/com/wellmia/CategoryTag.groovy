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
    String categoryType

    @Basic
    double thresholdSensitivity
    Date feedExpirationDate = new Date() - 10
    boolean feedIsStale = false
    int  numberOfFollowers = 0
    int  previousInterestRank = 1000

    @Basic
    Set<String> alias = new HashSet<String>()

    static constraints = {
    	id visible:false
        category(blank: false)
        thresholdSensitivity(blank:true)
        categoryType(inList:["Condition","Therapy","Test"])
	}

    String toString() {
        def label = ( (this.categoryType == "Therapy") &&
                    (!this.alias.isEmpty() )                 ) ? this.category + " (" + this.alias.asList().get(0) + ")" :
                                                              this.category
        label += " [" + this.categoryType + "]"
		"${label}"
	}

    void setNumberOfFollowers(int newValue) {
        if (newValue < 0)
            newValue = 0

        this.numberOfFollowers = newValue
    }
}
