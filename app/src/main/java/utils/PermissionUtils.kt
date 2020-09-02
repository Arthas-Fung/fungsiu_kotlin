package utils

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

internal object PermissionUtils {

    val MY_PERMISSIONS_REQUEST_CAMERA = 123
    var cameraPermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)

    val MY_PERMISSIONS_REQUEST_BLUETOOTH = 234
    var bluetoothPermissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.BLUETOOTH)

    val MY_PERMISSIONS_REQUEST_STORAGE = 345
    var storagePermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkCameraPermission(ctx: Context): Boolean {
        val readStorage = ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeStorage = ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val camera = ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA)
        val packageGranted = PackageManager.PERMISSION_GRANTED
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (readStorage != packageGranted || writeStorage != packageGranted || camera != packageGranted) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ctx as Activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.CAMERA)) {
                    ActivityCompat.requestPermissions(ctx, cameraPermissions, MY_PERMISSIONS_REQUEST_CAMERA)
                } else {
                    ActivityCompat.requestPermissions(ctx, cameraPermissions, MY_PERMISSIONS_REQUEST_CAMERA)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkBluetoothPermission(ctx: Context): Boolean {
        val location = ContextCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)
        val bluetooth = ContextCompat.checkSelfPermission(ctx, Manifest.permission.BLUETOOTH)
        val packageGranted = PackageManager.PERMISSION_GRANTED
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (location != packageGranted || bluetooth != packageGranted) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ctx as Activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                        || ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.BLUETOOTH)) {
                    ActivityCompat.requestPermissions(ctx, bluetoothPermissions, MY_PERMISSIONS_REQUEST_BLUETOOTH)
                } else {
                    ActivityCompat.requestPermissions(ctx, bluetoothPermissions, MY_PERMISSIONS_REQUEST_BLUETOOTH)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkStoragePermission(ctx: Context): Boolean {
        val readStorage = ContextCompat.checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE)
        val writeStorage = ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val packageGranted = PackageManager.PERMISSION_GRANTED
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (readStorage != packageGranted || writeStorage != packageGranted) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ctx as Activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        || ActivityCompat.shouldShowRequestPermissionRationale(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(ctx, storagePermissions, MY_PERMISSIONS_REQUEST_STORAGE)
                } else {
                    ActivityCompat.requestPermissions(ctx, storagePermissions, MY_PERMISSIONS_REQUEST_STORAGE)
                }
                return false
            } else {
                return true
            }
        } else {
            return true
        }
    }

}

//override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//}