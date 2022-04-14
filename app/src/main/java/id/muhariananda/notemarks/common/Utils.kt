package id.muhariananda.notemarks.common

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import id.muhariananda.notemarks.ui.common.SwipeToDelete

fun hideKeyboard(activity: Activity) {
    val inputMethodManager =
        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    val currentFocusedView = activity.currentFocus
    currentFocusedView?.let {
        inputMethodManager.hideSoftInputFromWindow(
            currentFocusedView.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}


fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner) { t -> observer.onChanged(t) }
}

fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun RecyclerView.swipeToDeleteItem(
    onSwipe: (RecyclerView.ViewHolder) -> Unit
) {
    val swipeToDeleteCallBack = object : SwipeToDelete() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwipe.invoke(viewHolder)
        }
    }
    val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
    itemTouchHelper.attachToRecyclerView(this)
}

fun SearchView.searchItems(onQueryTextChange: (String) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            newText?.let { onQueryTextChange.invoke(newText) }
            return true
        }
    })
}