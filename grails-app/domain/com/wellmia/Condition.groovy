package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class Condition implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String conditionTypeId

    Date diagnosisDate
    Date endDate

    boolean isCurrentCondition = true

    @Basic
    @ManyToOne
	ConsumerProfile consumer

    @Basic
    @OneToMany(cascade=CascadeType.ALL,
               fetch=FetchType.LAZY)
    Set<Symptom> symptoms = new HashSet<Symptom>()



    @Basic
    @OneToMany(cascade=CascadeType.ALL,
               fetch=FetchType.LAZY)
    SortedSet<Endpoint> outcomes = new TreeSet<Endpoint>()

    static constraints = {
    	id visible:false
        diagnosisDate(max:new Date(),nullable:false)
        endDate(max:new Date(),nullable:true)
        conditionTypeId(blank:false)

	}
}
