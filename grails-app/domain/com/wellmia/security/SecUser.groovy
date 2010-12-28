package com.wellmia.security

import com.wellmia.ConsumerProfile
import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class SecUser {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    Date dateCreated = new Date()
    String username
	String password

    String firstName
    String lastName
    String email

    String country

    boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    @Basic
    @OneToOne(cascade=CascadeType.ALL)
    ConsumerProfile consumerProfile

  	@Basic
    @OneToMany(cascade=CascadeType.ALL,
               fetch=FetchType.EAGER,
               mappedBy="secUser")
	Set<SecUserSecRole> authorities = new HashSet<SecUserSecRole>()

	static constraints = {
		username blank: false, unique: true
		password blank: false
        dateCreated(visible: false)

        firstName(nullable: true)
        lastName(nullable: true)
        email(email: true, nullable: false)

        country(blank: false)
	}


	static mapping = {
		password column: '`password`'
	}

	Set<SecRole> getAuthorities() {
		authorities.collect { SecRole.findWhere(id : it.secRoleId) } as Set
	}
}
