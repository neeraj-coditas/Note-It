package com.example.noteit.editorscreen.customdialogfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.noteit.R
import com.example.noteit.databinding.FragmentCustomDialogBinding

private const val ALERT_MSG = "alertMsg"
private const val POSITIVE_BTN_TEXT = "positiveBtnText"
private const val NEGATIVE_BTN_TEXT = "negativeBtnText"
private const val ACTION_MSG = "actionMsg"


class CustomDialogFragment : DialogFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null

    private lateinit var binding: FragmentCustomDialogBinding
    private lateinit var listener: CustomDialogClickListener

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ALERT_MSG)
            param2 = it.getString(POSITIVE_BTN_TEXT)
            param3 = it.getString(NEGATIVE_BTN_TEXT)
            param4 = it.getString(ACTION_MSG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentCustomDialogTvAlert.text = param1
        binding.fragmentCustomDialogBtnPositive.text = param2
        binding.fragmentCustomDialogBtnNegative.text = param3
        binding.fragmentCustomDialogBtnPositive.setOnClickListener {

            if (param4 == "actionSave") {
                val saveResult = true
                requireActivity().supportFragmentManager.setFragmentResult(
                    "saveKey",
                    bundleOf("bundleKey" to saveResult)
                )
            } else {
                val discardNote = true
                requireActivity().supportFragmentManager.setFragmentResult(
                    "discardKey",
                    bundleOf("bundleKey" to discardNote)
                )
            }

            //listener.onPositiveClick()
        }

        binding.fragmentCustomDialogBtnNegative.setOnClickListener {
            if (param4 == "actionSave") {
                val saveResult = false
                requireActivity().supportFragmentManager.setFragmentResult(
                    "saveKey",
                    bundleOf("bundleKey" to saveResult)
                )
            } else {

                val discardNote = false
                requireActivity().supportFragmentManager.setFragmentResult(
                    "discardKey",
                    bundleOf("bundleKey" to discardNote)
                )
            }
            //listener.onNegativeClick()
        }
    }

    fun initClickListener(buttonCallback: CustomDialogClickListener) {
        this.listener = buttonCallback
    }

    interface CustomDialogClickListener {
        fun onPositiveClick()
        fun onNegativeClick()
    }

    companion object {

        fun newInstance(
            alertMessage: String,
            positiveBtntext: String,
            negativeBtnText: String,
            actionMsg: String
        ): CustomDialogFragment {
            /*BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(ALERT_MSG, alertMessage)
                    putString(POSITIVE_BTN_TEXT, positiveBtntext)
                    putString(NEGATIVE_BTN_TEXT, negativeBtnText)
                    putString(ACTION_MSG, actionMsg)
                }
            }*/

            val fragment = CustomDialogFragment()

            val frag = Bundle()
            frag.putString(ALERT_MSG,alertMessage)
            frag.putString(POSITIVE_BTN_TEXT,positiveBtntext)
            frag.putString(NEGATIVE_BTN_TEXT,negativeBtnText)
            frag.putString(ACTION_MSG,actionMsg)

            fragment.arguments = frag
            return fragment
        }

    }

}