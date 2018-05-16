package com.cyanandroid.permissionmasterexample

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.cyanandroid.permissionmaster.PermissionCallBack
import com.cyanandroid.permissionmaster.runWithPermission


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAskPermission: Button = findViewById(R.id.btnAskPermission)
        val btnAskPermissionDefault: Button = findViewById(R.id.btnAskPermissionDefault)

        btnAskPermission.setOnClickListener { openFile() }
        btnAskPermissionDefault.setOnClickListener { openFileDefault() }
    }

    private fun openFile() {
        runWithPermission(PermissionCallBack {

            setRationalTitle("Rational Title")
            setRationalMsg("Rational Msg")
            setPermissionPermanentlyDeniedTitle("Permanent D Title")
            setPermissionPermanentlyDeniedMsg("Permanent D Msg")

            withPermission {
                Log.d(TAG, "withPermission")
            }
            onShowRational {
                Log.d(TAG, "onShowRational")
                it.askAgain()
            }
            onCancel {
                Log.d(TAG, "onCancel")

            }
            onPermissionDenied {
                Log.d(TAG, "onPermissionDenied")
            }
            onPermissionsArePermanentlyDenied {
                Log.d(TAG, "onPermissionsArePermanentlyDenied")
            }
            onPartiallyGranted { grantedPermission, deniedPermission ->
                Log.d(TAG, "onPartiallyGranted =>$grantedPermission denied permission $deniedPermission")

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun openFileDefault() {


        runWithPermission(PermissionCallBack {

            setRationalTitle("Rational Title")
            setRationalMsg("Rational Msg")
            setPermissionPermanentlyDeniedTitle("Permanent D Title")
            setPermissionPermanentlyDeniedMsg("Permanent D Msg")
            withPermission {
                Log.d(TAG, "withPermission")
            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}
