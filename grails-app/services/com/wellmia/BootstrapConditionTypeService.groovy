package com.wellmia

class BootstrapConditionTypeService {

    static transactional = true

    def serviceMethod() throws Exception {

        def defaultNewsDate = new Date() - 90 //90 days ago
        def fullCategoryList = []
        def conditionTypeProperties
        def count = 0

        new File ('WEB-INF/bootstrap/conditionTypes.csv').eachLine { line ->

            // Skip the first line (it shows column names)
            if(count > 0) {
                // Parse line into elements
                //newSourceProperties = line.split("\t: ")
                conditionTypeProperties = line.split(",")

                def conditionName = conditionTypeProperties[0].toString().replaceAll (/"/, '')
                Set<String> categoryList = new HashSet<String>()

                // Capture categories
                if(conditionTypeProperties.size() > 1) {
                    categoryList << (conditionTypeProperties[1].toString()).replaceAll (/"/, '')
                    if(conditionTypeProperties.size() > 2) {
                        categoryList << (conditionTypeProperties[2].toString()).replaceAll (/"/, '')
                        if(conditionTypeProperties.size() > 3) {
                            categoryList << (conditionTypeProperties[3].toString()).replaceAll (/"/, '')
                        }
                    }
                }

                // Add condition type, if we don't already have it
                if(ConditionType.findByConditionName(conditionName) == null) {
                    ConditionType.withTransaction {
                        def temp = new ConditionType(conditionName: conditionName,
                                     interestCategories: categoryList).save(failOnError: true, flush: true)
                    }
                }
            }

            count++
        }

        // Create CategoryTags for all condition types
        def conditions = ConditionType.list()
        conditions.each {conditiontype ->
            if(CategoryTag.findWhere(category: conditiontype.conditionName,
                                     categoryType: "Condition") == null) {
                CategoryTag.withTransaction {
                    //TODO:  Add aliases
                    def temp = new CategoryTag(category: conditiontype.conditionName,
                                               categoryType: "Condition").save(failOnError: true, flush: true)
                }
            }
        }
    }

}
