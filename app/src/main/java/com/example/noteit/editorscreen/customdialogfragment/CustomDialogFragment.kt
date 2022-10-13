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
const val REQUEST_KEY = "requestKey"
const val BUNDLE_KEY = "bundleKey"


class CustomDialogFragment : DialogFragment() {
    private var alertMessage: String? = null
    private var positiveButtonText: String? = null
    private var negativeButtonText: String? = null

    private lateinit var binding: FragmentCustomDialogBinding

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alertMessage = it.getString(ALERT_MSG)
            positiveButtonText = it.getString(POSITIVE_BTN_TEXT)
            negativeButtonText = it.getString(NEGATIVE_BTN_TEXT)
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

        binding.fragmentCustomDialogTvAlert.text = alertMessage
        binding.fragmentCustomDialogBtnPositive.text = positiveButtonText
        binding.fragmentCustomDialogBtnNegative.text = negativeButtonText

        binding.fragmentCustomDialogBtnPositive.setOnClickListener {
            val result = true
            requireActivity().supportFragmentManager.setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_KEY to result)
            )
        }

        binding.fragmentCustomDialogBtnNegative.setOnClickListener {
                val result = false
                requireActivity().supportFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(BUNDLE_KEY to result)
                )
            }
        }

    companion object {

        fun newInstance(
            alertMessage: String,
            positiveBtntext: String,
            negativeBtnText: String
        ): CustomDialogFragment {

            val fragment = CustomDialogFragment()

            val frag = Bundle()
            frag.putString(ALERT_MSG, alertMessage)
            frag.putString(POSITIVE_BTN_TEXT, positiveBtntext)
            frag.putString(NEGATIVE_BTN_TEXT, negativeBtnText)

            fragment.arguments = frag
            return fragment
        }

    }

}