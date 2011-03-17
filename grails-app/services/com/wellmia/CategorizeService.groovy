package com.wellmia


class CategorizeService {

    static transactional = false

    def categorizeText(ConsumerProfile user, String content) {
        def Set<String> categories = new HashSet()

        NGramProfiler profiler = new NGramProfiler(content)
        Map<String,Integer> profileNGramCount = profiler.createProfile()

        def interestCategories = user.getInterestTags();
        interestCategories.each { interest ->
            def category = CategoryProfile.findWhere(categoryName : interest);
            if(category) {
                double difference = NGramProfiler.calculateDifference(profileNGramCount,
                                                                      category.ngramCounts)

                double threshold =  (double) category.getProperty('thresholdSensitivity')
                if(difference <= threshold) {
                    categories.add(interest)
                }
            }
        }

        // Add "General" tag if no categories are identified
        //if(categories.isEmpty())
        //    categories.add("General");

        return categories
    }


}
