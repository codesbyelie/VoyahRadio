package elie.voyah.radio.app.utils

import android.app.Activity
import android.content.Intent

actual val appVersionName: String
    get() = runCatching {
        activityProvider.invoke().packageManager.getPackageInfo(activityProvider.invoke().packageName, 0).versionName ?: "?"
    }.getOrElse { "?" }

actual fun shareLink(url: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share Stream Link")
    activityProvider.invoke().startActivity(shareIntent)
}

private var activityProvider: () -> Activity = {
    throw IllegalArgumentException("Error")
}

fun setActivityProvider(provider: () -> Activity) {
    activityProvider = provider
}