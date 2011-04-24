package com.wellmia

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

class ClassDumperController {

    def garbageCollectorService

    def deleteClass = {
        def classToDump = params.id
        System.out.println("Begining delete task web call")
        garbageCollectorService.dumpClass(classToDump)
        redirect(uri: '/topics')
    }

    def deleteClassTask = {
        def classToDump = params.id
        System.out.println("Calling deleteClass task")
        Queue queue = QueueFactory.getDefaultQueue();
        TaskOptions taskOptions = TaskOptions.Builder.withUrl("/classDumper/deleteClass/" + classToDump)
        queue.add(taskOptions)
        redirect(uri: '/topics')
    }
}
