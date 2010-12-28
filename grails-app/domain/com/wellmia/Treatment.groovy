package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class Treatment implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String treatmentTypeId
    Date startDate
    Date endDate
    Long dosage
    String stopReason
    String stopReasonComments

    @Basic
    @ManyToOne
	ConsumerProfile consumer

    @Basic
    @OneToMany
    Set<Symptom> sideEffects = new HashSet<Symptom>()

    static constraints = {
    	id visible:false
        treatmentTypeId(blank:false)
        startDate(blank:false,max:new Date())
        endDate(blank:true,max:new Date())
	}
}
