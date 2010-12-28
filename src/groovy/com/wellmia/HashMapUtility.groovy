package com.wellmia

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 12/18/10
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
class HashMapUtility {

  static HashMap sortHashMapByValuesD(HashMap passedMap) {

      HashMap sortedMap = new HashMap()
      LinkedList myKeys = []
      LinkedList myValues = []

      passedMap.each { key, value ->

        if(myKeys.isEmpty()) {
          myKeys.add(key)
          myValues.add(value)
        } else {
          for(i in 0..myKeys.size())  {
            if(myValues[i] > value) {
              myKeys.add(i, key)
              myValues.add(i, value)
              break;
            }
          }
        }
      }

      for(i in 0..<myKeys.size()) {
        sortedMap.put(myKeys[i],myValues[i])
      }

      return sortedMap;
  }
}
