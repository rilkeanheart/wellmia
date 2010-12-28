package com.wellmia

class DateTagLib {
    static namespace = "wellmia"

    def dateFromNow = { attrs->

        def date = attrs.date
        def niceDate = getNiceDate(date) // implement this below
        out << niceDate
    }

    static String getNiceDate(Date date) {
        def now = new Date()

        def diff = Math.abs(now.time - date.time)
        final long second = 1000
        final long minute = second * 60
        final long hour = minute * 60
        final long day = hour * 24

        def niceTime = ""

        long calc = 0;
        boolean significantEpochFound

        calc = Math.floor(diff / day)
        if (calc) {
            niceTime+=calc+" day"+(calc>1?"s ":" ")
            diff %= day
            significantEpochFound = true
        }

        calc = Math.floor(diff / hour)
        if (calc && !significantEpochFound)  {
            niceTime+=calc+" hour"+(calc>1?"s ":" ")
            diff %= hour
            significantEpochFound = true
        }

        calc = Math.floor(diff / minute)
        if (calc && !significantEpochFound) {
            niceTime+=calc+" minute"+(calc>1?"s ":" ")
            diff %= minute
        }

        if (!niceTime) {
            niceTime = "Right now"
        } else {
            niceTime += (date.time > now.time) ? "from now" : "ago"
        }

        return niceTime
    }

}