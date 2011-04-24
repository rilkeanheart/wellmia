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
	Set<String> category

    @Basic(fetch = FetchType.EAGER)
    Set<String> notifyList = new HashSet<String>()

	String imageTitle
	int imageWidth
	int imageHeight
	boolean isReviewed

    int numberOfViews = 0
    int rating = 0

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

    //TODO:  Test unique constraint on NewsItem titles
    static constraints = {
    	id visible:false
		title(blank: false, unique: true)
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

    // TODO:  Use an interface to define a class of sortable domains by date (use for Status, Question, etc.)
    int compareTo(Object o) {
      int returnValue = 0

      if(o.rating != this.rating)
        returnValue = (this.rating < o.rating) ? -1 : 1

      return returnValue
    }

}
