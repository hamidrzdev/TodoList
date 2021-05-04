package com.hamiddev.todolist.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hamiddev.todolist.R
import io.reactivex.disposables.CompositeDisposable

open class BaseActivity : AppCompatActivity(), BaseView {
    override val viewContext: Context?
        get() = this
    override val rootView: CoordinatorLayout?
        get() {
            val viewGroup = window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
            } else
                return viewGroup
            throw IllegalStateException("rootView must be instance of Coordinator layout")
        }
}

open class BaseFragment : Fragment(), BaseView {
    override val viewContext: Context?
        get() = requireContext()
    override val rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout
}

open class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val progressBarLiveData = MutableLiveData<Boolean>()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

interface BaseView {
    val viewContext: Context?
    val rootView: CoordinatorLayout?
    fun setProgressIndicator(mustShow: Boolean) {
        rootView?.let {
            viewContext?.let { context ->
                var loadingView = it.findViewById<View>(R.id.loading_view)
                if (loadingView == null && mustShow) {
                    loadingView =
                        LayoutInflater.from(context).inflate(R.layout.loading_view, it, false)
                    it.addView(loadingView)
                }
                loadingView?.visibility = if (mustShow) View.VISIBLE else View.INVISIBLE
            }
        }
    }
}