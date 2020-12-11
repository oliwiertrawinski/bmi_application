package developer.android.laby3

import HistoryAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class HistoryActivity : AppCompatActivity() {

    lateinit var history:ArrayList<historyRecord>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val rvHistory = findViewById<View>(R.id.history_list) as RecyclerView
        history = DataManager.bmiHistory
        val adapter = HistoryAdapter(history)
        rvHistory.adapter = adapter
        rvHistory.layoutManager = LinearLayoutManager(this)
    }
    //kjafhagdfjk
}


