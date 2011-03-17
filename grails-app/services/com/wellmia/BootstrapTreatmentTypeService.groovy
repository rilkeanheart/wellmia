package com.wellmia

import org.apache.commons.lang.WordUtils
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import static com.google.appengine.api.taskqueue.TaskOptions.Builder.withUrl;

class BootstrapTreatmentTypeService {

    static transactional = true

    def serviceMethod(int iOffset, int iMaxSourcesToProcess) throws Exception {

	    def startTime = System.currentTimeMillis()

        def defaultNewsDate = new Date() - 90 //90 days ago
        def fullCategoryList = []
        def treatmentTypeProperties
        def count = 0

        iOffset = (iOffset = 0) ? 1 : iOffset // Skip the first line (it shows column names)

        try {

            new File ('WEB-INF/bootstrap/drugTreatmentTypes.csv').eachLine { line  ->

                if( (count > iOffset + 1) &&
                    (count < (iMaxSourcesToProcess)) ){

                    // Parse line into elements
                    //newSourceProperties = line.split("\t: ")
                    treatmentTypeProperties = line.split("\t")

                    def applicationNumber = treatmentTypeProperties[0].toString()
                    def productNumber = treatmentTypeProperties[1].toString()
                    def form = treatmentTypeProperties[2].toString()
                    form = WordUtils.capitalizeFully(form, " -.".toCharArray())
                    //TODO:  Split formlist by semicolon

                    def dosage = treatmentTypeProperties[3].toString()
                    //TODO:  Split dosage list by semicolon (will need to create separate drugs)

                    def productMarketStatus = treatmentTypeProperties[4].toString()
                    def tecCode = treatmentTypeProperties[5].toString()
                    def referenceDrug = treatmentTypeProperties[6].toString()
                    def drugName = treatmentTypeProperties[7].toString()
                    drugName = WordUtils.capitalizeFully(drugName, " -.".toCharArray())

                    //drugName.replaceAll(/(\w)(\w*)/) { wholeMatch, initialLetter, restOfWord -> initialLetter.toUpperCase() + restOfWord.toLowerCase() }

                    def activeIngredients = treatmentTypeProperties[8].toString()
                    activeIngredients = WordUtils.capitalizeFully(activeIngredients, " -.".toCharArray())
                    //TODO: Split activeIngredientList by semicolon

                    Set<String> categoryList = new HashSet<String>()
                    categoryList << drugName
                    //TODO:  Add active ingredients to categoryList

                    // Add treatment type, if we don't already have it
                    if(TreatmentType.findWhere(treatmentTypeName: drugName,
                                               form: form,
                                               dosage: dosage) == null) {
                        if(!dosage.contains("withdrawn")) {
                            TreatmentType.withTransaction {
                                def temp = new TreatmentType(treatmentTypeName: drugName,
                                             isDrug: true,
                                             applicationNumber: applicationNumber,
                                             productNumber: productNumber,
                                             form: form,
                                             dosage: dosage,
                                             productMarketStatus: productMarketStatus,
                                             tecCode: tecCode,
                                             referenceDrug: referenceDrug,
                                             activeIngredients: activeIngredients,
                                             interestCategories: categoryList).save(failOnError: true, flush: true)
                            }
                        } else {
                            //TODO:  Mark this drug as withdrawn and delete the associated tag?? or do nothing??
                        }
                    }

                }

                count++
                def deltaTime = System.currentTimeMillis() - startTime        //Get System time
                if(deltaTime * 1000 > 25) {
                    log.info("Chaining BootstrapTreatmentTypeService.serviceMethod request at:  ${iOffset+count-1}")
                    throw new BootstrapTreatmentTypeServiceException(nextOffset: (iOffset + count - 1))
                }            }
        } catch(BootstrapTreatmentTypeServiceException e) {
            Queue queue = QueueFactory.getDefaultQueue();
            TaskOptions taskOptions = TaskOptions.Builder.withUrl("/postLaunchLoaderController/doLoadTreatmentService")
                                      .param("offset",e.nextOffset.toString())
                                      .param("max", iMaxSourcesToProcess.toString())
            queue.add(taskOptions)
        }
    }

    def categoryTagServiceMethod(int iOffset, int iMaxSourcesToProcess) throws Exception {
        // Create CategoryTags for all condition types
        def startTime = System.currentTimeMillis()
        def count = 0
        def treatments = TreatmentType.list(offset: iOffset)

        try {
            treatments?.each {treatmentType ->
                if(CategoryTag.findWhere(category: treatmentType.treatmentTypeName,
                                         categoryType: "Therapy") == null) {
                    CategoryTag.withTransaction {
                        def temp = new CategoryTag(category: treatmentType.treatmentTypeName,
                                                   categoryType: "Therapy").save(failOnError: true, flush: true)
                    }
                }
                count++
                def deltaTime = System.currentTimeMillis() - startTime
                if((count = 1000) ||
                    (delta * 1000 > 27) ) {
                    //Create a new task
                    log.info("Chaining BootstrapTreatmentTypeService.serviceMethod request at:  ${iOffset+count-1}")
                    throw new BootstrapTreatmentTypeServiceException(nextOffset: (iOffset + count - 1))
                }

            }
        } catch(BootstrapTreatmentTypeServiceException e) {
            Queue queue = QueueFactory.getDefaultQueue();
            TaskOptions taskOptions = TaskOptions.Builder.withUrl("/postLaunchLoaderController/doLoadTreatmentCategoryTagService")
                                      .param("offset",e.nextOffset.toString())
                                      .param("max", iMaxSourcesToProcess.toString())
            queue.add(taskOptions)
        }
    }
}

class BootstrapTreatmentTypeServiceException extends RuntimeException {
	double nextOffset
}