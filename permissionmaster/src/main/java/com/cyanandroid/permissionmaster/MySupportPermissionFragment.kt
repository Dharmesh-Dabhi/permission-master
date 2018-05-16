package com.cyanandroid.permissionmaster

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import java.util.*


/**
 * Created by HP on 22-Feb-18.
 */
class MySupportPermissionFragment : Fragment() {
    private val REQ_PERMISSION = 11
    private var mPermissionCallBack: PermissionCallBack? = null


    companion object {
        const val TAG: String = "SupportPermissionFrag"

        fun newInstance(): MySupportPermissionFragment {
            return MySupportPermissionFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQ_PERMISSION -> {
                val grantPermission = ArrayList<String>()
                val deniedPermission = ArrayList<String>()
                for (i in 0..(permissions.size - 1)) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        grantPermission.add(permissions[i])
                    } else {
                        deniedPermission.add(permissions[i])
                    }
                }
                if (mPermissionCallBack?.partiallyGranted != null && deniedPermission.size != 0 && grantPermission.size != 0) {
                    mPermissionCallBack?.partiallyGranted?.invoke(grantPermission, deniedPermission)
                    return
                } else {
                    if (deniedPermission.size == 0 && grantPermission.size > 0) {
                        //permission granted
                        mPermissionCallBack?.withPermission?.invoke()
                    } else {
                        //all permission may be not granted
                        if (getShouldShow(mPermission.orEmpty())) {
                            mPermissionCallBack?.permissionDenied?.invoke()
                        } else {
                            //permission permanently denied
                            if (mPermissionCallBack?.permissionsArePermanentlyDenied == null) {
                                openAppDetails()
                            } else {
                                mPermissionCallBack?.permissionsArePermanentlyDenied?.invoke()
                            }
                        }
                    }
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            }
        }
    }

    private fun openAppDetails() {
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(mPermissionCallBack?.getPermissionPermanentlyDeniedTitle(context))
            builder.setMessage(mPermissionCallBack?.getPermissionPermanentlyDeniedMsg(context))
            builder.setPositiveButton("Grant", { _, _ ->
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", it.packageName, null))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            })
            builder.setNegativeButton("Cancel", { _, _ -> mPermissionCallBack?.cancel?.invoke() })
            builder.create().show()
        }
    }

    private var mPermission: Array<out String>? = null

    public fun checkPermission(vararg permission: String) {
        mPermission = permission
        var isPermissionGranted = true
        for (s in permission) {
            if (context != null) {
                if (ContextCompat.checkSelfPermission(context!!, s) != PackageManager.PERMISSION_GRANTED) {
                    isPermissionGranted = false
                    break
                }
            } else {
                isPermissionGranted = false

            }
        }

        if (isPermissionGranted) {
            //already permission granted
            mPermissionCallBack?.withPermission?.invoke()
        } else {

            //ask for permission
            if (getShouldShow(permission)) {

                if (mPermissionCallBack?.showRational == null) {
                    showRationalDialog()
                } else {
                    //show rational
                    mPermissionCallBack?.showRational?.invoke(object : PermissionExecutor() {
                        override fun askAgain() {
                            this@MySupportPermissionFragment.requestPermissions(permission, REQ_PERMISSION)
                        }

                        override fun cancel() {
                            mPermissionCallBack?.cancel?.invoke()
                        }
                    })
                }
            } else {
                this@MySupportPermissionFragment.requestPermissions(permission, REQ_PERMISSION)
            }
        }
    }

    private fun showRationalDialog() {
        context?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(mPermissionCallBack?.getRationalTitle(context))
            builder.setMessage(mPermissionCallBack?.getRationalMsg(context))
            builder.setPositiveButton("Ask again", { _, _ ->
                this@MySupportPermissionFragment.requestPermissions(mPermission.orEmpty(), REQ_PERMISSION)
            })
            builder.setNegativeButton("Cancel", { _, _ -> mPermissionCallBack?.cancel?.invoke() })
            builder.create().show()
        }
    }

    private fun getShouldShow(permission: Array<out String>): Boolean {
        var shouldShowRationale = false
        for (p in permission) {
            if (shouldShowRequestPermissionRationale(p)) {
                shouldShowRationale = true
                break
            }
        }
        return shouldShowRationale
    }

    fun setPermissionCallBack(permissionCallBack: PermissionCallBack) {
        mPermissionCallBack = permissionCallBack
    }

}