package com.wellmia



import javax.persistence.*;
import org.datanucleus.jpa.annotations.Extension;
// import com.google.appengine.api.datastore.Key;


@Entity
class NewsSource implements Serializable {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	String id

	String sourceRSSLink
	String name
	String sourceHomeURL
	Date   mostRecentNewsDate
	Long newsSourceRank

    Set<String> defaultCategories = new HashSet<String>()
	boolean isActive = true

	@Basic
	@OneToMany(cascade=CascadeType.ALL,	mappedBy="newsSource")
	Set<NewsItem> newsItems

    static constraints = {
    	id visible:false
		name(blank: false)
		sourceRSSLink(url: true, blank: false)
		sourceHomeURL(url: true, blank: false)
	}

	String giveMeSomething() {
		String result = "You've got something"
		return result
	}

    void addToNewsItems(NewsItem item) {
		println newsItems
        if (newsItems.add(item)) {
            //item.newsSource = this.id
            //NewsItem.withTransaction {
            //    item.save(flush:true)
            //}
        }
    }

    String toString() {
        "${name} (${sourceRSSLink})"
    }
}
