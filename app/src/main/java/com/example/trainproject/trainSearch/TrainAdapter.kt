package com.example.trainproject.trainSearch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainproject.R

class TrainAdapter(var trainList: List<TrainData>?) : RecyclerView.Adapter<TrainAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var trainNum = itemView.findViewById<TextView>(R.id.textViewTrainNumber)
        var trainName = itemView.findViewById<TextView>(R.id.textViewTrainName)
        var stationFrom = itemView.findViewById<TextView>(R.id.textViewStationFrom)
        var stationTo = itemView.findViewById<TextView>(R.id.textViewStationTo)
        var operationalDays = itemView.findViewById<TextView>(R.id.textViewOperationalDays)

        fun myBindData(trainData : TrainData) {
            trainNum.setText(trainData.trainNum.toString())
            trainName.setText(trainData.name)
            stationFrom.setText(trainData.trainFrom)
            stationTo.setText(trainData.trainTo)
            operationalDays.setText(trainData.data.days
                .filterValues { it == 1 }
                .keys
                .joinToString(", "))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.train_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return trainList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        trainList?.get(position)?.let { holder.myBindData(it) }
    }
}