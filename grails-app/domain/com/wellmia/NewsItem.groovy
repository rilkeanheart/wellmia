package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;

// import com.google.appengine.api.datastore.Key;

@Entity
class NewsItem implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

	Date dateCreated
	String title
	String urlLink
	Date publishDate

    @Basic(fetch = FetchType.EAGER)
	List<String> category
	String imageTitle
	int imageWidth
	int imageHeight
	boolean isReviewed

	//@Basic
	//Text content
	//TODO:  Decide on String versus Text for NewsItem content property
	String content

	@ManyToOne
	NewsSource newsSource

	//TODO:  Add comments class to NewsItem
	@Basic
	@OneToMany(cascade=CascadeType.ALL,
               fetch = FetchType.EAGER)
	SortedSet<Comment> comments

    static constraints = {
    	id visible:false
		title(blank: false)
		urlLink(url: true, blank: false)
		publishDate(blank: false)
		imageTitle(nullable: true)
		imageWidth(nullable: true)
		imageHeight(nullable: true)
	}

	static mapping = {
		sort publishDate
	}

	String toString() {
		"${title}"
	}

}
