package com.sathyapriyan.healthcare

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.sathyapriyan.healthcare.databinding.FragmentVideoCallBinding
import com.sathyapriyan.healthcare.util.Constants
import com.vidyo.VidyoClient.Connector.Connector
import com.vidyo.VidyoClient.Connector.Connector.IConnect
import com.vidyo.VidyoClient.Connector.ConnectorPkg

class VideoCallFragment : DialogFragment(),
    View.OnLayoutChangeListener,
    IConnect {

    private lateinit var binding: FragmentVideoCallBinding

    private val remoteParticipants = 15

    private var myVideoConnector: Connector? = null

    private var callBtnState = true
    private var microPhoneState = true
    private var speakerState = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentVideoCallBinding.inflate(layoutInflater)

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.fragment_video_call)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)

        ConnectorPkg.setApplicationUIContext(requireContext())
        ConnectorPkg.initialize()

        myVideoConnector = Connector(
            binding.root,
            Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default,
            remoteParticipants,
            "warning all@VidyoConnector info@VidyoClient",
            "",
            0
        )

        binding.cardViewBtnCallConnectAndDisconnect.setOnClickListener {

            if (callBtnState) {

                callBtnState = false
                binding.cardViewBtnCallConnectAndDisconnect.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(),R.color.red)
                )

                myVideoConnector?.connectToRoomAsGuest(
                    Constants.PORTAL,
                    Constants.NAME,
                    Constants.ROOM_KEY,
                    Constants.ROOM_PIN,
                    this
                )

            } else {

                callBtnState = true
                binding.cardViewBtnCallConnectAndDisconnect.setCardBackgroundColor(
                    ContextCompat.getColor(requireContext(),R.color.green)
                )

                myVideoConnector?.disconnect()
                dialog?.dismiss()

            }

        }

        binding.cardViewBtnMicrophone.setOnClickListener {

            if (microPhoneState) {

                microPhoneState = false
                myVideoConnector?.setMicrophonePrivacy(true)
                binding.imageViewMicrophone.setImageResource(R.drawable.ic_mic_disable)

            } else {

                microPhoneState = true
                myVideoConnector?.setMicrophonePrivacy(false)
                binding.imageViewMicrophone.setImageResource(R.drawable.ic_mic)

            }

        }

        binding.cardViewBtnSpeaker.setOnClickListener {

            if (speakerState) {

                speakerState = false
                myVideoConnector?.setSpeakerPrivacy(true)
                binding.imageViewSpeaker.setImageResource(R.drawable.ic_speaker_disable)

            } else {

                speakerState = true
                myVideoConnector?.setSpeakerPrivacy(false)
                binding.imageViewSpeaker.setImageResource(R.drawable.ic_speaker)

            }

        }

        binding.root.addOnLayoutChangeListener(this)

        return binding.root

    }

    override fun onLayoutChange(
        p0: View?,
        p1: Int,
        p2: Int,
        p3: Int,
        p4: Int,
        p5: Int,
        p6: Int,
        p7: Int,
        p8: Int
    ) {

        p0?.removeOnLayoutChangeListener(this)

        val width: Int = p0?.width!!
        val height: Int = p0.height

        myVideoConnector!!.showViewAt(
            p0,
            0,
            0,
            width,
            height
        )

    }

    override fun onSuccess() {
        println("MyVideoCall (onSuccess) --> Success")
    }

    override fun onFailure(p0: Connector.ConnectorFailReason?) {

        println("MyVideoCall (onFailure) --> ${p0?.name}")

    }

    override fun onDisconnected(p0: Connector.ConnectorDisconnectReason?) {

        println("MyVideoCall (onDisconnected) --> ${p0?.name}")

    }

}