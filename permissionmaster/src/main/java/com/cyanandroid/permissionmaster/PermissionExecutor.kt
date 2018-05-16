package com.cyanandroid.permissionmaster

/**
 * Created by HP on 23-Feb-18.
 */
abstract class PermissionExecutor {
    public abstract fun askAgain()
    public abstract fun cancel()
}