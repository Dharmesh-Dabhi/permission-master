package com.cyanandroid.permissionmaster

import android.content.Context
import java.util.ArrayList

/**
 * Created by HP on 23-Feb-18.
 */
class PermissionCallBack(permissionFunction: PermissionCallBack.() -> Unit) {
    var withPermission: (() -> Unit)? = null
    var showRational: ((permissionExecutor: PermissionExecutor) -> Unit)? = null
    var permissionsArePermanentlyDenied: (() -> Unit)? = null
    var permissionDenied: (() -> Unit)? = null
    var cancel: (() -> Unit)? = null
    var partiallyGranted: ((grantedPermission: ArrayList<String>, deniedPermission: ArrayList<String>) -> Unit)? = null


    //message
    private var mRationalTitle: String? = "Permission"
    private var mRationalMsg: String? = "If you want to use this feature, please provide some permission."
    private var mPermissionPermanentlyDeniedTitle: String? = "Need some permission"
    private var mPermissionPermanentlyDeniedMsg: String? = "If you want to use this feature, please provide some permission."

    private var mRationalTitleResId: Int? = null
    private var mRationalMsgResId: Int? = null
    private var mPermissionPermanentlyDeniedTitleResId: Int? = null
    private var mPermissionPermanentlyDeniedMsgResId: Int? = null


    fun setPermissionPermanentlyDeniedTitle(title: String?) {
        mPermissionPermanentlyDeniedTitle = title
        mPermissionPermanentlyDeniedTitleResId = null

    }

    fun setPermissionPermanentlyDeniedTitle(title: Int) {
        mPermissionPermanentlyDeniedTitle = null
        mPermissionPermanentlyDeniedTitleResId = title

    }


    fun setPermissionPermanentlyDeniedMsg(msg: String?) {
        mPermissionPermanentlyDeniedMsg = msg
        mPermissionPermanentlyDeniedMsgResId = null
    }

    fun setPermissionPermanentlyDeniedMsg(msg: Int) {
        mPermissionPermanentlyDeniedMsg = null
        mPermissionPermanentlyDeniedMsgResId = msg
    }


    fun setRationalTitle(title: String?) {
        mRationalTitle = title
        mRationalTitleResId = null
    }

    fun setRationalTitle(title: Int) {
        mRationalTitle = null
        mRationalTitleResId = title
    }


    fun setRationalMsg(msg: String?) {
        mRationalMsg = msg
        mRationalMsgResId = null
    }

    fun setRationalMsg(msg: Int) {
        mRationalMsg = null
        mRationalMsgResId = msg
    }


    fun withPermission(function: () -> Unit) {
        withPermission = function
    }

    fun onShowRational(function: (permissionExecutor: PermissionExecutor) -> Unit) {
        showRational = function
    }

    fun onPermissionsArePermanentlyDenied(function: () -> Unit) {
        permissionsArePermanentlyDenied = function
    }

    fun onPermissionDenied(function: () -> Unit) {
        permissionDenied = function
    }

    fun onCancel(function: () -> Unit) {
        cancel = function
    }

    fun onPartiallyGranted(function: (grantedPermission: ArrayList<String>, deniedPermission: ArrayList<String>) -> Unit) {
        partiallyGranted = function
    }

    fun getRationalTitle(context: Context?): String {

        return if (context != null && mRationalTitleResId != null) {
            context.getString(mRationalTitleResId!!)
        } else {
            mRationalTitle as String
        }
    }

    fun getRationalMsg(context: Context?): String {
        return if (context != null && mRationalMsgResId != null) {
            context.getString(mRationalMsgResId!!)
        } else {
            mRationalMsg as String
        }
    }

    fun getPermissionPermanentlyDeniedTitle(context: Context?): String {
        return if (context != null && mPermissionPermanentlyDeniedTitleResId != null) {
            context.getString(mPermissionPermanentlyDeniedTitleResId!!)

        } else {
            mPermissionPermanentlyDeniedTitle as String
        }
    }

    fun getPermissionPermanentlyDeniedMsg(context: Context?): String {
        return if (context != null && mPermissionPermanentlyDeniedMsgResId != null) {
            context.getString(mPermissionPermanentlyDeniedMsgResId!!)
        } else {
            mPermissionPermanentlyDeniedMsg as String
        }
    }

    init {
        permissionFunction()
    }
}