import com.wellmia.security.*
import com.wellmia.*

class BootStrap {
    def springSecurityService

    def init = { servletContext ->
      def userRole = SecRole.findByAuthority('ROLE_USER') ?: new SecRole(authority: 'ROLE_USER').save(failOnError: true)
      def adminRole = SecRole.findByAuthority('ROLE_ADMIN') ?: new SecRole(authority: 'ROLE_ADMIN').save(failOnError: true)

        //Create Category Tags and Profiles

        //Create News Sources
        def defaultNewsDate = new Date() - 90 //90 days ago

        if(NewsSource.findBySourceRSSLink("http://www.medicalnewstoday.com/rss/cardiovascular-cardiology.xml") == null) {
          new NewsSource(sourceRSSLink: "http://www.medicalnewstoday.com/rss/cardiovascular-cardiology.xml",
                         name: "Medical News Today",
                         sourceHomeURL: "http://www.medicalnewstoday.com",
                         mostRecentNewsDate: defaultNewsDate,
                         newsSourceRank: Long.getLong("1"),
                         isActive: true).save(failOnError: true)
        }

        if(NewsSource.findBySourceRSSLink("http://www.medicalnewstoday.com/rss/cholesterol.xml") == null) {
          new NewsSource(sourceRSSLink: "http://www.medicalnewstoday.com/rss/cholesterol.xml",
                         name: "Medical News Today",
                         sourceHomeURL: "http://www.medicalnewstoday.com",
                         mostRecentNewsDate: defaultNewsDate,
                         newsSourceRank: Long.getLong("2"),
                         isActive: true).save(failOnError: true)
        }
        if(NewsSource.findBySourceRSSLink("http://www.medicalnewstoday.com/rss/copd.xml") == null) {
          new NewsSource(sourceRSSLink: "http://www.medicalnewstoday.com/rss/copd.xml",
                         name: "Medical News Today",
                         sourceHomeURL: "http://www.medicalnewstoday.com",
                         mostRecentNewsDate: defaultNewsDate,
                         newsSourceRank: Long.getLong("3"),
                         isActive: true).save(failOnError: true)
        }
        if(NewsSource.findBySourceRSSLink("http://www.medicalnewstoday.com/rss/diabetes.xml") == null) {
          new NewsSource(sourceRSSLink: "http://www.medicalnewstoday.com/rss/diabetes.xml",
                         name: "Medical News Today",
                         sourceHomeURL: "http://www.medicalnewstoday.com",
                         mostRecentNewsDate: defaultNewsDate,
                         newsSourceRank: Long.getLong("4"),
                         isActive: true).save(failOnError: true)
        }
        if(NewsSource.findBySourceRSSLink("http://www.medicalnewstoday.com/rss/heart-disease.xml") == null) {
          new NewsSource(sourceRSSLink: "http://www.medicalnewstoday.com/rss/heart-disease.xml",
                         name: "Medical News Today",
                         sourceHomeURL: "http://www.medicalnewstoday.com",
                         mostRecentNewsDate: defaultNewsDate,
                         newsSourceRank: Long.getLong("5"),
                         isActive: true).save(failOnError: true)
        }
        if(NewsSource.findBySourceRSSLink("http://www.medicalnewstoday.com/rss/hypertension.xml") == null) {
          new NewsSource(sourceRSSLink: "http://www.medicalnewstoday.com/rss/hypertension.xml",
                         name: "Medical News Today",
                         sourceHomeURL: "http://www.medicalnewstoday.com",
                         mostRecentNewsDate: defaultNewsDate,
                         newsSourceRank: Long.getLong("6"),
                         isActive: true).save(failOnError: true)
        }
        if(NewsSource.findBySourceRSSLink("http://www.medicalnewstoday.com/rss/fitness-obesity.xml") == null) {
          new NewsSource(sourceRSSLink: "http://www.medicalnewstoday.com/rss/fitness-obesity.xml",
                         name: "Medical News Today",
                         sourceHomeURL: "http://www.medicalnewstoday.com",
                         mostRecentNewsDate: defaultNewsDate,
                         newsSourceRank: Long.getLong("7"),
                         isActive: true).save(failOnError: true)
        }
        //Administrator
        //
        //def adminProfile = new ConsumerProfile()
        def adminUser = SecUser.findByUsername('admin') ?: new SecUser(
        username: 'admin',
        password: springSecurityService.encodePassword('Michael@39'),
        email: "mike@familiaverde.com",
        country: "United States",
        enabled: true).save(failOnError: true)

        if (!adminUser.getAuthorities().contains(adminRole)) {
          SecUserSecRole.withTransaction {
            adminUser = SecUser.findByUsername('admin')
            SecUserSecRole.create adminUser, adminRole
          }
        }

        if (!adminUser.getAuthorities().contains(userRole)) {
          SecUserSecRole.withTransaction {
            adminUser = SecUser.findByUsername('admin')
            SecUserSecRole.create adminUser, userRole
          }
        }

        if (adminUser.getProperty("consumerProfile") == null) {
          SecUser.withTransaction {
            String birthDateString = "28/05/1971 00:00:00"
            def birthDate = new Date().parse("d/M/yyyy H:m:s", birthDateString)
            def adminConsumerProfile = new ConsumerProfile(gender : "Male", birthDate : birthDate)
            if(!adminConsumerProfile.interestTags.contains("Obesity"))
              adminConsumerProfile.interestTags << "Obesity"
            if(!adminConsumerProfile.interestTags.contains("High Cholesterol"))
              adminConsumerProfile.interestTags << "High Cholesterol"
            if(!adminConsumerProfile.interestTags.contains("High Blood Pressure"))
              adminConsumerProfile.interestTags << "High Blood Pressure"

            adminUser = SecUser.findByUsername('admin')
            adminUser.setProperty("consumerProfile", adminConsumerProfile)
          }
        }

        //User (non-admin)
        //
        def nonAdminUser = SecUser.findByUsername('ellalgreen') ?: new SecUser(
        username: 'ellalgreen',
        password: springSecurityService.encodePassword('mamasita@69'),
        email: "ellalgreen@aol.com",
        country: "United States",
        enabled: true).save(failOnError: true)

        if (!nonAdminUser.getAuthorities().contains(userRole)) {
          SecUserSecRole.withTransaction {
            nonAdminUser = SecUser.findByUsername('ellalgreen')
            SecUserSecRole.create nonAdminUser, userRole
          }
        }

        if (nonAdminUser.getProperty("consumerProfile") == null) {
          SecUser.withTransaction {
            String birthDateString = "13/06/1941 00:00:00"
            def birthDate = new Date().parse("d/M/yyyy H:m:s", birthDateString)
            def nonAdminConsumerProfile = new ConsumerProfile(gender : "Female", birthDate : birthDate)
            if(!nonAdminConsumerProfile.interestTags.contains("Diabetes - Type II"))
              nonAdminConsumerProfile.interestTags << "Diabetes - Type II"
            if(!nonAdminConsumerProfile.interestTags.contains("High Blood Pressure"))
              nonAdminConsumerProfile.interestTags << "High Blood Pressure"
            if(!nonAdminConsumerProfile.interestTags.contains("Chronic Pain"))
              nonAdminConsumerProfile.interestTags << "Chronic Pain"

            nonAdminUser = SecUser.findByUsername('ellalgreen')
            nonAdminUser.setProperty("consumerProfile", nonAdminConsumerProfile)
          }
        }

      println "Finished Bootstrap"
    }

    def destroy = {
    }
}
