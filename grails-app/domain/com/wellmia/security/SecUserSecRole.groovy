package com.wellmia.security

import org.apache.commons.lang.builder.HashCodeBuilder;
import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;
// import com.google.appengine.api.datastore.Key;

@Entity
class SecUserSecRole implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

    @Basic
    @ManyToOne
	SecUser secUser

	String secRoleId

	boolean equals(other) {
		if (!(other instanceof SecUserSecRole)) {
			return false
		}

		other.secUser?.id == secUser?.id &&
			other.secRoleId == secRoleId
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (secUser) builder.append(secUser.id)
		if (secRoleId) builder.append(secRoleId)
		builder.toHashCode()
	}

	//static SecUserSecRole get(long secUserId, long secRoleId) {
	//	find 'from SecUserSecRole where secUser.id=:secUserId and secRole.id=:secRoleId',
	//		[secUserId: secUserId, secRoleId: secRoleId]
	//}

	static SecUserSecRole create(SecUser secUser, SecRole secRole, boolean flush = false) {
		new SecUserSecRole(secUser: secUser, secRoleId: secRole.id).save(flush: flush)
	}

	static boolean remove(SecUser secUser, SecRole secRole, boolean flush = false) {
		SecUserSecRole instance = SecUserSecRole.findWhere(secUser: secUser, secRoleId: secRole.id)
		instance ? instance.delete(flush: flush) : false
	}

	static void removeAll(SecUser secUser) {
		//executeUpdate 'DELETE FROM SecUserSecRole WHERE secUser=:secUser', [secUser: secUser]
        SecUserSecRole.findAllBySecUser(secUser).each {item -> SecUserSecRole.remove(secUser, SecRole.findWhere(id : item.secRoleId))}
	}

	static void removeAll(SecRole secRole) {
		//executeUpdate 'DELETE FROM SecUserSecRole WHERE secRole=:secRole', [secRole: secRole]
        SecUserSecRole.findAllBySecRoleId(secRole.id).each {item -> SecUserSecRole.remove(item.secUser, ecRole.Id)}
	}

	static mapping = {
		id visible: false
		version false
	}
}