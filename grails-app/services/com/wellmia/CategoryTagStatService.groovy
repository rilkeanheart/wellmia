package com.wellmia

import org.datanucleus.sco.backed.SortedSet

class CategoryTagStatService {

    static transactional = true

    def List topCategoryStats() {

        // Get the top 100 categoryTags
        String query = "SELECT a from CategoryTag a ORDER BY a.numberOfFollowers desc"
        Map params = [max : "100"]
        List topCategories = CategoryTag.findAll(query,params)

        List topCategoriesStats = []

        int rank = 1
        topCategories.each { category ->
            def newItem = new CategoryTagRankStat(categoryTag : category.category,
                                                  interestRank : rank++,
                                                  lastInterestRank : category.previousInterestRank)
            topCategoriesStats << newItem

        }

        return topCategoriesStats
    }

    def updateCategoryStats() {
        // Get the top 1000 categoryTags
        String query = "SELECT a from CategoryTag a ORDER BY a.numberOfFollowers desc"
        Map params = [max : "1000"]
        List topCategories = CategoryTag.findAll(query,params)

        // for each categorytag set "last rank" value to current rank in list
        int rank = 1
        topCategories.each { category ->
            CategoryTag.withTransaction {
                category.setProperty("previousInterestRank" : rank++)
                category.save()
            }
        }
    }

    def getCategories(String categoryType, String beginsWith) {

        def categories = []
        def categoryTypes = ["Condition", "Therapy", "Test"]
        if(categoryTypes.contains(categoryType)) {
            String query = "Select a from CategoryTag a WHERE categoryType = :categoryType AND LOWER(category) LIKE :category"
            Map params = [categoryType: categoryType, category: "${beginsWith.toLowerCase()}%"]
            categories = CategoryTag.findAll(query,params)
        }

        return categories
    }
}

