import android.content.Context
import android.content.SharedPreferences
import java.util.Date

private const val PREF_NAME = "app_shared_preferences"
private const val DATE_KEY = "date"
fun Context.saveDateToSharedPreferences(date: Date) {
    val sharedPreferences: SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = sharedPreferences.edit()
    editor.putLong(DATE_KEY, date.time)
    editor.apply()
}

fun Context.getDateFromSharedPreferences(): Date {
    val sharedPreferences: SharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    val timestamp = sharedPreferences.getLong(DATE_KEY, 0)
    return if (timestamp != 0L) {
        Date(timestamp)
    } else {
        Date()
    }
}