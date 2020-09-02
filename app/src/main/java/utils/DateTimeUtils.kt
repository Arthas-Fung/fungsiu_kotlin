package utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    fun getRemainingTime(startTs: Long, endTs: Long): List<Long> {
        val diff = (endTs - startTs) * 1000

        val diffDays = diff / (24 * 60 * 60 * 1000)
        val diffHours = diff / (60 * 60 * 1000) % 24
        val diffMinutes = diff / (60 * 1000) % 60
        val diffSeconds = diff / 1000 % 60

        Log.i(javaClass.name, "diffDays :$diffDays")
        Log.i(javaClass.name, "diffHours :$diffHours")
        Log.i(javaClass.name, "diffMinutes :$diffMinutes")
        Log.i(javaClass.name, "diffSeconds :$diffSeconds")

        val result = ArrayList<Long>()
        result.add(diffDays)
        result.add(diffHours)
        result.add(diffMinutes)
        result.add(diffSeconds)

        return result
    }

    fun getFormattedDate(ts: Long, pattern: String): String {
        val sdf = SimpleDateFormat(pattern)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = ts
        return sdf.format(calendar.time)
    }

}