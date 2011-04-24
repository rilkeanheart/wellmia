package com.wellmia.security

import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class SecRole {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}

    boolean equals(Object obj) {
      boolean isEqual = false

      if (obj?.getProperty("authority")?.equals(this.authority))
         isEqual = true

      return isEqual
    }

    int hashCode() {
      int returnValue = 1
      if(this.authority)
         returnValue = 39 * this.authority?.hashCode()
    }

}
