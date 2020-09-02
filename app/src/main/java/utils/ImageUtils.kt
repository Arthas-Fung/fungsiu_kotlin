package utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

object ImageUtils {
    private val folderName = "FUNGSIU"

    fun saveImage(context: Context, bitmap: Bitmap): String {
        val appDir = File(Environment.getExternalStorageDirectory(), folderName)
        if (!appDir.exists()) {
            appDir.mkdir()
        }
        val fileName = System.currentTimeMillis().toString() + ".jpg"
        val file = File(appDir, fileName)
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val pathName = "/sdcard/$folderName/$fileName"
        context.sendBroadcast(
            Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(File(pathName))
            )
        )
        return pathName
    }

    fun getUri(context: Context, bitmap: Bitmap): Uri {
        var uriString = saveImage(context, bitmap)
        return Uri.parse(uriString)
    }

    fun getBitmap(context: Context, resId: Int): Bitmap {
        val res = context.resources
        return BitmapFactory.decodeResource(res, resId)
    }

}