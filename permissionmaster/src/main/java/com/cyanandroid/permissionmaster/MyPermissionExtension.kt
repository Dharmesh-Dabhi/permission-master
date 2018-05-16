package com.cyanandroid.permissionmaster

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity


/**
 * Created by HP on 22-Feb-18.
 */
fun AppCompatActivity.runWithPermission(permissionCallBack: PermissionCallBack, vararg permission: String) {
    getPermissionUtils(
            supportFragmentManager,
            permissionCallBack,
            *permission
    )
}



fun Fragment.runWithPermission(permissionCallBack: PermissionCallBack, vararg permission: String) {
    getPermissionUtils(
            childFragmentManager,
            permissionCallBack,
            *permission
    )
}


private fun getPermissionUtils(fragmentManager: android.support.v4.app.FragmentManager, permissionCallBack: PermissionCallBack, vararg permission: String) {
    var mHeadlessFrag: Fragment? = fragmentManager
            .findFragmentByTag(MySupportPermissionFragment.TAG)
    if (mHeadlessFrag == null) {
        mHeadlessFrag = MySupportPermissionFragment.newInstance()
        fragmentManager.beginTransaction()?.add(mHeadlessFrag, MySupportPermissionFragment.TAG)?.commitNow()
    }
    if (mHeadlessFrag is MySupportPermissionFragment) {
        mHeadlessFrag.setPermissionCallBack(permissionCallBack)
        mHeadlessFrag.checkPermission(*permission)
    }
}
