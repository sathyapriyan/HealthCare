package com.sathyapriyan.healthcare.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sathyapriyan.healthcare.R
import com.sathyapriyan.healthcare.models.FavouriteDoctorData
import com.sathyapriyan.healthcare.models.RecyclerViewCommon
import com.sathyapriyan.healthcare.util.CommonUtil.loadImageFromCoil

class DoctorListRecyclerViewAdapter(
    private val items: List<RecyclerViewCommon>,
    private val onClickListener: ((doctorData: FavouriteDoctorData) -> Unit)
): RecyclerView.Adapter<DoctorListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_list_item, parent, false)

        return ViewHolder(view,onClickListener)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items[position]
        holder.bind(item)

    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(
        private val view : View,
        private val onClickListener: ((doctorData: FavouriteDoctorData) -> Unit))
        : RecyclerView.ViewHolder(view){

        private val imgViewProfile : ImageView = view.findViewById(R.id.imgViewProfile)
        private val imageViewFavourite : ImageView = view.findViewById(R.id.imageViewFavourite)
        private val textViewName : TextView = view.findViewById(R.id.textViewName)
        private val textViewMail : TextView = view.findViewById(R.id.textViewMail)
        private val textViewOnlineStatus : TextView = view.findViewById(R.id.textViewOnlineStatus)
        private val textViewRating : TextView = view.findViewById(R.id.textViewRating)

        fun bind(doctorData : RecyclerViewCommon){

            textViewName.text = doctorData.favouriteDoctorData.name
            textViewMail.text = doctorData.favouriteDoctorData.email
            textViewOnlineStatus.text = doctorData.favouriteDoctorData.onlineStatus
            val ratingValueString = "${doctorData.favouriteDoctorData.rating} / 5"
            textViewRating.text = ratingValueString

            imgViewProfile.loadImageFromCoil(doctorData.favouriteDoctorData.profilePicture)

            if (doctorData.isFavourite) {
                imageViewFavourite.setImageResource(R.drawable.ic_favorite)

            } else {
                imageViewFavourite.setImageResource(R.drawable.ic_unfavorite)
            }

            view.setOnClickListener { onClickListener.invoke(doctorData.favouriteDoctorData) }

        }

    }

}