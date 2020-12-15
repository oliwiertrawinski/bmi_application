import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import developer.android.laby3.BMI_IN_LBS
import developer.android.laby3.DataBase.TableInfo
import developer.android.laby3.R
import developer.android.laby3.HistoryRecord
import kotlinx.android.synthetic.main.recycler_view_item.view.*


class HistoryAdapter(val context: Context, val db: SQLiteDatabase): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val recyclerView_item = layoutInflater.inflate(R.layout.recycler_view_item, parent, false)
        return MyViewHolder(recyclerView_item)
    }

    override fun getItemCount(): Int {

        val cursor = db.query(TableInfo.TABLE_NAME, null, null, null, null, null, null, null)
        val rowCount = cursor.count
        cursor.close()
        return rowCount
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {

        val textView1 = viewHolder.view.line1
        val textView2 = viewHolder.view.line2
        val textView3 = viewHolder.view.line3

        val cursor = db.query(TableInfo.TABLE_NAME,null,BaseColumns._ID + "=?",arrayOf(viewHolder.adapterPosition
            .plus(1).toString()),null,null,null)

        if (cursor.moveToFirst()) {
            if (!cursor.getString(1).isNullOrEmpty() && !cursor.getString(2).isNullOrEmpty()) {
                val position = cursor.getString(0)
                val mass = cursor.getString(1)
                val height = cursor.getString(2)
                val unit = cursor.getInt(3)
                val bmi = cursor.getString(4)
                val date = cursor.getString(5)
                textView1.setText(getLine1(position))
                textView2.setText(getLine2(bmi, date))
                textView3.setText(getLine3(mass, height, unit))
            }else{
                //Toast.makeText(context, "Trkst do wyswietlenia", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

class MyViewHolder(val view:View): RecyclerView.ViewHolder(view){
    val historyLine1 = itemView.findViewById<TextView>(R.id.line1)
    val historyLine2 = itemView.findViewById<TextView>(R.id.line2)
    val historyLine3 = itemView.findViewById<TextView>(R.id.line3)
}

fun getLine1(position:String):String{
    val builder = StringBuilder()
    builder.append(position)
        .append(" pozycja:")

    return builder.toString()
}

fun getLine2(bmi:String, date:String):String{
    val builder = StringBuilder()
    builder.append("Bmi: ")
        .append(bmi)
        .append("; Date: ")
        .append(date)

    return builder.toString()
}

fun getLine3(mass:String, height:String, unit:Int):String{
    var massUnit = "kg"
    var heightUnit = "cm"
    if(unit == BMI_IN_LBS){
        massUnit = "lbs"
        heightUnit = "in"
    }
    val builder = StringBuilder()
    builder.append("Mass: ")
        .append(mass)
        .append(massUnit)
        .append("; Height: ")
        .append(height)
        .append(heightUnit)

    return builder.toString()
}
