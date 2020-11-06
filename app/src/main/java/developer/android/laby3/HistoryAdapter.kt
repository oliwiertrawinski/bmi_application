import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import developer.android.laby3.R
import developer.android.laby3.historyRecord

class HistoryAdapter (private val history: ArrayList<historyRecord>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>()
{
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        //val nameTextView = itemView.findViewById<TextView>(R.id.contact_name)
        val historyLine1 = itemView.findViewById<TextView>(R.id.line1)
        val historyLine2 = itemView.findViewById<TextView>(R.id.line2)
        val historyLine3 = itemView.findViewById<TextView>(R.id.line3)

    }

    // ... constructor and member variables
    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.recycler_view_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: HistoryAdapter.ViewHolder, position: Int) {
        // Get the data model based on position
        val historyRow: historyRecord = history.get(position)
        // Set item views based on your views and data model
        val textView1 = viewHolder.historyLine1
        textView1.setText((position + 1).toString() + " pomiar:")
        val textView2 = viewHolder.historyLine2
        textView2.setText(historyRow.getLine1())
        val textView3 = viewHolder.historyLine3
        textView3.setText(historyRow.getLine2())

    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return history.size
    }
}