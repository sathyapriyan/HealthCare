package com.sathyapriyan.healthcare

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sathyapriyan.healthcare.data.remote.NetworkResult
import com.sathyapriyan.healthcare.databinding.FragmentDoctorDetailBinding
import com.sathyapriyan.healthcare.util.CommonUtil.loadImageFromCoil
import com.sathyapriyan.healthcare.viewmodels.DoctorDetailViewModel
import com.sathyapriyan.healthcare.viewmodels.PatientProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DoctorDetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDoctorDetailBinding

    private val doctorArgs: DoctorDetailFragmentArgs by navArgs()

    private val viewModel: DoctorDetailViewModel by viewModels()
    private val patientProfileViewModel: PatientProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDoctorDetailBinding.inflate(layoutInflater)

        binding.textViewName.text= doctorArgs.doctorData.name
        binding.textViewMail.text= doctorArgs.doctorData.email
        binding.textViewOnlineStatus.text= doctorArgs.doctorData.onlineStatus

        val rating = "${doctorArgs.doctorData.rating}/5"

        binding.textViewRating.text=rating
        binding.imgViewProfile.loadImageFromCoil(
            doctorArgs.doctorData.profilePicture
        )

        if(doctorArgs.isFavourite){
            binding.textViewFavourite.text= resources.getText(R.string.remove_txt)
            setTextViewDrawableColor(
                binding.textViewFavourite,
                R.color.gray
            )
        }else{
            binding.textViewFavourite.text= resources.getText(R.string.add_txt)
            setTextViewDrawableColor(
                binding.textViewFavourite,
                R.color.red
            )

        }

        binding.cardViewFav.setOnClickListener {

            viewModel.addOrRemoveFavourite(
                context = requireContext(),
                patientUserId = doctorArgs.patientData.patientData.patientProfile.userId,
                doctorUserId = doctorArgs.doctorData.id,
                isDeleted = doctorArgs.isFavourite,
                accessToken = doctorArgs.patientData.patientData.accessToken
            )

        }
        binding.cardViewVideoCall.setOnClickListener {

            val directions = DoctorDetailFragmentDirections.actionDoctorDetailFragmentToVideoCallFragment()
            findNavController().navigate(directions = directions)

        }

        viewModel.addOrRemoveResponse.observe(viewLifecycleOwner){ response ->

            when(response) {

                is NetworkResult.Success -> {

                    binding.progressBarAdd.visibility = View.INVISIBLE
                    binding.textViewFavourite.visibility = View.VISIBLE

                    Toast.makeText(
                        requireContext(),
                        response.data?.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    println("Add or Remove Response --> ${response.data}")

                    if (doctorArgs.isFavourite) {

                        patientProfileViewModel.getFavouriteDoctorsDetails(
                            doctorArgs.patientData.patientData.accessToken,
                            requireContext()
                        )

                    } else {

                        patientProfileViewModel.getAllDoctorsDetails(
                            requireContext()
                        )
                    }

                    dialog?.dismiss()

                }

                is NetworkResult.Error -> {

                    binding.progressBarAdd.visibility = View.INVISIBLE
                    binding.textViewFavourite.visibility = View.VISIBLE

                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is NetworkResult.Loading -> {

                    binding.progressBarAdd.visibility = View.VISIBLE
                    binding.textViewFavourite.visibility = View.INVISIBLE

                }

            }

        }

        return binding.root

    }

    private fun setTextViewDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(textView.context, color),
                        PorterDuff.Mode.SRC_IN
                    )
            }
        }
    }
}