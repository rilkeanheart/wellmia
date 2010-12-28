package com.wellmia



import com.wellmia.security.SecUser
import javax.persistence.*
import org.datanucleus.jpa.annotations.Extension
import com.google.appengine.api.datastore.Blob

// import com.google.appengine.api.datastore.Key;

@Entity
class ConsumerProfile implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    @OneToOne(mappedBy="consumerProfile")
    SecUser secUser

    String gender
    Date   birthDate
    Blob photo

    @Basic
    Set<String> interestTags = new HashSet<String>()

    @Basic
    Date feedExpirationDate = new Date() - 100

    @Basic
    @OneToMany(cascade=CascadeType.ALL,
               fetch=FetchType.LAZY,
               mappedBy="consumer")
	SortedSet<Condition> conditions = new TreeSet<Condition>()
    //TODO:  Ensure sorted set is comparable

  	@Basic
    @OneToMany(cascade=CascadeType.ALL,
               fetch=FetchType.LAZY,
               mappedBy="consumer")
	SortedSet<Treatment> treatments = new TreeSet<Treatment>()
    //TODO:  Ensure sorted set is comparable

    static constraints = {
    	id visible:false
        birthDate(blank: false, max:new Date())
        gender(inList:["Male","Female"])
	}
}
