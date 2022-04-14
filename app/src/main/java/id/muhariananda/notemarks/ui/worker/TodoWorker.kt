package id.muhariananda.notemarks.ui.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import id.muhariananda.notemarks.common.Constant.KEY_TASK
import id.muhariananda.notemarks.ui.common.NotificationHelper

class TodoWorker(
    val context: Context,
    val params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        NotificationHelper(context).createNotification(
            inputData.getString(KEY_TASK).toString()
        )

        return Result.success()
    }
}