package com.sathyapriyan.healthcare

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.sathyapriyan.healthcare.data.remote.NetworkResult
import com.sathyapriyan.healthcare.databinding.FragmentPatientProfileBinding
import com.sathyapriyan.healthcare.models.RecyclerViewCommon
import com.sathyapriyan.healthcare.presentation.adapters.DoctorListRecyclerViewAdapter
import com.sathyapriyan.healthcare.util.CommonUtil
import com.sathyapriyan.healthcare.util.CommonUtil.loadImageFromCoil
import com.sathyapriyan.healthcare.util.SharedPreferenceKeys
import com.sathyapriyan.healthcare.viewmodels.PatientProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class PatientProfileFragment : Fragment() {

    private lateinit var binding: FragmentPatientProfileBinding

    @Inject
    @Named("userPreferences")
    lateinit var preferencesDataStoreUser: DataStore<Preferences>

    @Inject
    lateinit var ioDispatcher: CoroutineDispatcher

    private val viewModel: PatientProfileViewModel by viewModels()

    private val doctorDataList: MutableList<RecyclerViewCommon> = mutableListOf()

    private var isFavouriteDoctor = true

    private val patientArgs: PatientProfileFragmentArgs by navArgs()

    private val doctorListRecyclerViewAdapter : DoctorListRecyclerViewAdapter by lazy{

        DoctorListRecyclerViewAdapter(doctorDataList) {

            val directions = PatientProfileFragmentDirections.actionPatientProfileFragmentToDoctorDetailFragment(
                patientArgs.logindata,
                it,
                isFavouriteDoctor
            )

            findNavController().navigate(directions = directions)

        }

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPatientProfileBinding.inflate(layoutInflater)

        val userAccessToken = patientArgs.logindata.patientData.accessToken

        binding.textViewName.text = patientArgs.logindata.patientData.patientProfile.displayName

        val bmiValue = "H: ${patientArgs.logindata.patientData.patientProfile.height} " +
                " W: ${patientArgs.logindata.patientData.patientProfile.weight} " +
                " BMI: ${patientArgs.logindata.patientData.patientProfile.bmi}"

        binding.textViewBmi.text = bmiValue
        binding.textViewPhone.text = patientArgs.logindata.patientData.patientProfileLocation.phone1
        binding.textViewGender.text = patientArgs.logindata.patientData.patientProfile.gender
        binding.textViewAge.text = patientArgs.logindata.patientData.patientProfile.dob

        binding.imgViewProfile.loadImageFromCoil(
            patientArgs.logindata.patientData.patientProfile.profilePicture
        )

        lifecycleScope.launch(ioDispatcher) {

            // Storing Access Token
            preferencesDataStoreUser.edit { preferences ->

                preferences[SharedPreferenceKeys.USER_ACCESS_TOKEN] = userAccessToken

            }

            viewModel.getFavouriteDoctorsDetails(
                accessToken = userAccessToken,
                context = requireContext()
            )

        }

        binding.cardViewDocterAllDetails
            .getChildAt(1)
            .findViewById<CardView>(R.id.cardViewFavDoctorsList)
            .setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_700))

        binding.cardViewDocterAllDetails
            .getChildAt(1)
            .findViewById<TextView>(R.id.textViewFavourite)
            .setTextColor(ContextCompat.getColor(requireContext(),R.color.white))

        setTextViewDrawableColor(
            binding.cardViewDocterAllDetails
                .getChildAt(1)
                .findViewById(R.id.textViewFavourite),
            R.color.white
        )

        binding.cardViewDocterAllDetails
            .getChildAt(1)
            .findViewById<CardView>(R.id.cardViewAllDoctorsList)
            .setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.gray))

        binding.cardViewDocterAllDetails
            .getChildAt(1)
            .findViewById<TextView>(R.id.textViewAll)
            .setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

        setTextViewDrawableColor(
            binding.cardViewDocterAllDetails
                .getChildAt(1)
                .findViewById(R.id.textViewAll),
            R.color.black
        )

        binding.cardViewDocterAllDetails
            .getChildAt(1)
            .findViewById<CardView>(R.id.cardViewFavDoctorsList).setOnClickListener {

                binding.cardViewDocterAllDetails
                    .getChildAt(1)
                    .findViewById<CardView>(R.id.cardViewFavDoctorsList)
                    .setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_700))

                binding.cardViewDocterAllDetails
                    .getChildAt(1)
                    .findViewById<TextView>(R.id.textViewFavourite)
                    .setTextColor(ContextCompat.getColor(requireContext(),R.color.white))

                setTextViewDrawableColor(
                    binding.cardViewDocterAllDetails
                        .getChildAt(1)
                        .findViewById(R.id.textViewFavourite),
                    R.color.white
                )

                binding.cardViewDocterAllDetails
                    .getChildAt(1)
                    .findViewById<CardView>(R.id.cardViewAllDoctorsList)
                    .setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.gray))

                binding.cardViewDocterAllDetails
                    .getChildAt(1)
                    .findViewById<TextView>(R.id.textViewAll)
                    .setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

                setTextViewDrawableColor(
                    binding.cardViewDocterAllDetails
                        .getChildAt(1)
                        .findViewById(R.id.textViewAll),
                    R.color.black
                )

                viewModel.getFavouriteDoctorsDetails(
                    accessToken = userAccessToken,
                    context = requireContext()
                )

            }

        binding.cardViewDocterAllDetails
            .getChildAt(1)
            .findViewById<CardView>(R.id.cardViewAllDoctorsList).setOnClickListener {

                binding.cardViewDocterAllDetails
                    .getChildAt(1)
                    .findViewById<CardView>(R.id.cardViewAllDoctorsList)
                    .setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.purple_700))

                binding.cardViewDocterAllDetails
                    .getChildAt(1)
                    .findViewById<TextView>(R.id.textViewAll)
                    .setTextColor(ContextCompat.getColor(requireContext(),R.color.white))

                setTextViewDrawableColor(
                    binding.cardViewDocterAllDetails
                        .getChildAt(1)
                        .findViewById(R.id.textViewAll),
                    R.color.white
                )

                binding.cardViewDocterAllDetails
                    .getChildAt(1)
                    .findViewById<CardView>(R.id.cardViewFavDoctorsList)
                    .setCardBackgroundColor(ContextCompat.getColor(requireContext(),R.color.gray))

                binding.cardViewDocterAllDetails
                    .getChildAt(1)
                    .findViewById<TextView>(R.id.textViewFavourite)
                    .setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

                setTextViewDrawableColor(
                    binding.cardViewDocterAllDetails
                        .getChildAt(1)
                        .findViewById(R.id.textViewFavourite),
                    R.color.black
                )

                viewModel.getAllDoctorsDetails(context = requireContext())

            }

        binding.imgViewBackBtn.setOnClickListener {

            findNavController().navigate(R.id.action_patientProfileFragment_to_loginFragment)

        }


        viewModel.favouriteDoctorListResponse.observe(viewLifecycleOwner) { response ->

            when(response) {

                is NetworkResult.Success -> {

                    binding.cardViewDocterAllDetails.getChildAt(0).visibility = View.INVISIBLE
                    binding.cardViewDocterAllDetails.getChildAt(1).visibility = View.VISIBLE

                    doctorDataList.clear()
                    isFavouriteDoctor = true
                    response.data?.favouriteDoctorData?.forEach {

                        doctorDataList.add(
                            RecyclerViewCommon(it,true)
                        )

                    }

                    setTextViewDrawableColor(
                        binding.cardViewDocterAllDetails
                            .getChildAt(1)
                            .findViewById(R.id.textViewFavourite),
                        R.color.white
                    )

                    setupRecyclerView()

                }

                is NetworkResult.Error -> {

                    binding.cardViewDocterAllDetails.getChildAt(0).visibility = View.INVISIBLE
                    binding.cardViewDocterAllDetails.getChildAt(1).visibility = View.VISIBLE

                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is NetworkResult.Loading -> {

                    binding.cardViewDocterAllDetails.getChildAt(0).visibility = View.VISIBLE
                    binding.cardViewDocterAllDetails.getChildAt(1).visibility = View.INVISIBLE

                }

            }

        }

        viewModel.doctorListResponse.observe(viewLifecycleOwner) { response ->

            when(response) {

                is NetworkResult.Success -> {

                    binding.cardViewDocterAllDetails.getChildAt(0).visibility = View.INVISIBLE
                    binding.cardViewDocterAllDetails.getChildAt(1).visibility = View.VISIBLE

                    doctorDataList.clear()
                    isFavouriteDoctor = false

                    response.data?.doctorData?.forEach {

                        doctorDataList.add(
                            RecyclerViewCommon(
                                CommonUtil.mapAllDoctorDataToFavouriteDoctorData(it),
                                false
                            )
                        )

                    }

                    setupRecyclerView()

                }

                is NetworkResult.Error -> {

                    binding.cardViewDocterAllDetails.getChildAt(0).visibility = View.INVISIBLE
                    binding.cardViewDocterAllDetails.getChildAt(1).visibility = View.VISIBLE

                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }

                is NetworkResult.Loading -> {

                    binding.cardViewDocterAllDetails.getChildAt(0).visibility = View.VISIBLE
                    binding.cardViewDocterAllDetails.getChildAt(1).visibility = View.INVISIBLE

                }

            }

        }

        return binding.root

    }

    private fun setupRecyclerView() {
        binding.cardViewDocterAllDetails
            .getChildAt(1)
            .findViewById<RecyclerView>(R.id.recyclerViewDoctorList).apply {
            adapter = doctorListRecyclerViewAdapter
            layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = binding.cardViewDocterAllDetails
            .getChildAt(1)
            .findViewById<RecyclerView>(R.id.recyclerViewDoctorList).itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
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