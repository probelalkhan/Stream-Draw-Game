package io.getstream.data

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class AppPreferences @Inject constructor(
    context: Context
) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    var userId: String?
        get() = sharedPreferences.getString(KEY_USER_ID, null)
        set(value) = sharedPreferences.edit().putString(KEY_USER_ID, value).apply()

    companion object {
        private const val SHARED_PREF_NAME = "stream-draw-prefs"
        private const val KEY_USER_ID = "key_user_id"
    }
}
