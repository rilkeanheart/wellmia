package com.wellmia

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

class GarbageCollectorService {

    static transactional = true

    def dumpClass(String classToDump) {

        log.info("Getting first batch of ${classToDump} instances")
        System.out.println("Getting first batch of ${classToDump} instances")
        def max = 500
        def allInstances = (classToDump as Class).list([max: 500])
        log.info("Received ${allInstances.size()} instances")
        System.out.println("Test write")
        int size = allInstances.size()
        allInstances.each { item->
            try {
                System.out.println("Deleting: ${item}")
                item.delete(flush:true)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                log.error("Error deleting item ${item}")
                log.error(e)
            }
        }

        if(size == 500) {
            //Trigger another task to delete remaining
            Queue queue = QueueFactory.getDefaultQueue();
            TaskOptions taskOptions = TaskOptions.Builder.withUrl("/classDumper/deleteClass/" + classToDump)
            queue.add(taskOptions)
        }

    }
}
