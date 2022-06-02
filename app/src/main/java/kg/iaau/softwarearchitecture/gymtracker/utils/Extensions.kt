package kg.iaau.softwarearchitecture.gymtracker.utils

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import kg.iaau.softwarearchitecture.gymtracker.R

fun Context.startDialog() {
    val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
    builder.setTitle("Время вышло!")
    builder.setCancelable(false)
    builder.setPositiveButton("Ок") { dialog, _ ->
        dialog.cancel()
    }
    builder.show()
}

fun Fragment.vibratePhone() {
    val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    if (Build.VERSION.SDK_INT >= 26) {
        vibrator.vibrate(VibrationEffect.createOneShot(1500, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(1500)
    }
}