package com.wellmia

/**
 * Created by IntelliJ IDEA.
 * User: mike
 * Date: 12/26/10
 * Time: 6:35 AM
 * To change this template use File | Settings | File Templates.
 */
class JPQLQueryHelper {

  String queryString
  Map    params

  static JPQLQueryHelper BuildFindAllWhere(Map params) {
    /*def whereClause = params.keySet().collect {
       "${logicalName}.$it = :$it"
    }.join(" and ")
    String q = "select ${logicalName} from ${entityClass.name} as ${logicalName} where ${whereClause}"
    Query query = em.createQuery(q)

    params.each { String name, value ->
      if(value instanceof GString) value = val.toString()
      query.setParameter(name, value)


    def orderBy = ''
    def order = ''
    if(args?.sort) {
        if(args?.order) {
            order = args.order == 'desc' ? ' desc' : ' asc'
        }
        def sort = args.sort
        if(sort instanceof List) {
            orderBy = " order by ${sort.join(", ${logicalName}.")}${order}"
        }
        else {
            orderBy = " order by ${logicalName}.${sort}${order}"
        }
    }

      def q = em.createQuery("select ${logicalName} from ${entityClass.name} as ${logicalName} ${orderBy}")
      if(args?.max) {
          q.setMaxResults(args.max.toInteger())
      }
      if(args?.offset) {
          q.setFirstResult(args.offset.toInteger())
      }
      q.resultList */
  }
}
