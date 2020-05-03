package com.maddy.newsdemo.util

import android.os.AsyncTask

abstract class AsyncUtil<T>(val daoProcessCallback: DaoProcessCallback<T>?) {

    interface DaoProcessCallback<T> {
        fun onResult(result: T)
    }

    protected abstract fun doAsync(): T

    fun start() {
        DaoProcessAsyncTask().execute()
    }

     inner class DaoProcessAsyncTask : AsyncTask<Void, Void, T>() {

        override fun doInBackground(vararg params: Void): T {
            return doAsync()
        }

        override fun onPostExecute(t: T) {
            daoProcessCallback?.onResult(t)
        }
    }
}

