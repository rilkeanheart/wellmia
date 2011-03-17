package com.wellmia.security


import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class RegistrationCode implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

	String username

	String token = UUID.randomUUID().toString().replaceAll('-', '')
	Date dateCreated = new Date()

    static constraints = {
    	id visible:false
	}
}
