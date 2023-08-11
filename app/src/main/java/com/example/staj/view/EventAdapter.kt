import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.staj.R
import com.example.staj.difutil.RatioDifUtil
import com.example.staj.view.bigmodel.Mainmodel
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter() : RecyclerView.Adapter<EventAdapter.MyViewHolder>() {
    private var matchList= mutableListOf<Mainmodel>()
    private var indexOffset = 0 // Başlangıç index değeri
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val adjustedPosition = position + indexOffset
        holder.bindItems(matchList[position], adjustedPosition)
    }

    override fun getItemCount(): Int = matchList.size

    // Veriyi güncelleme işlevi
    fun updateData(newList: List<Mainmodel>) {
        //var copyOldList=oldList
        val equal=matchList==newList
        if(equal){
            Log.d("SOCKET","THEY ARE EQUAL")
        }
        else{
            Log.d("SOCKET","THEY ARE NOT EQUAL")
        }
        val diffCallback = RatioDifUtil(matchList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        // Yeni veri listesini atayın


        // Farkı hesapla ve adapterı güncelle
        diffResult.dispatchUpdatesTo(this)
        matchList = newList as MutableList<Mainmodel>
    }






    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val matchText: TextView = view.findViewById(R.id.matches)
        val dateText: TextView = view.findViewById(R.id.dates)
        val ratio1: TextView = view.findViewById(R.id.ratio1)
        val ratiox: TextView = view.findViewById(R.id.ratioX)
        val ratio2: TextView = view.findViewById(R.id.ratio2)
        val upper: TextView = view.findViewById(R.id.plus)
        val lower: TextView = view.findViewById(R.id.minus)

        fun bindItems(item: Mainmodel, index: Int) {
            matchText.text = "$index "+item.matchTag.name
            dateText.text = dateFormat(item.matchTag.date)
            ratio1.text = item.finalRatio.ratio1
            ratiox.text = item.finalRatio.ratiox
            ratio2.text = item.finalRatio.ratio2
            upper.text = item.exclusiveRatio.upper
            lower.text = item.exclusiveRatio.lower
        }

        fun dateFormat(date: Long): String {
            return SimpleDateFormat("dd MMM yyyy HH:mm ", Locale("TR", "tr")).format(date)
        }
    }
}
