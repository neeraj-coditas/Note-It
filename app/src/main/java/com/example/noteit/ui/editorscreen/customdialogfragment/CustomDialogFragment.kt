package com.example.noteit.ui.editorscreen.customdialogfragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.noteit.R
import com.example.noteit.databinding.FragmentCustomDialogBinding
import com.example.noteit.ui.editorscreen.EditorScreenFragment

private const val ALERT_MSG_BUNDLE_KEY = "alertMsg"
private const val POSITIVE_BTN_TEXT_BUNDLE_KEY = "positiveBtnText"
private const val NEGATIVE_BTN_TEXT_BUNDLE_KEY = "negativeBtnText"
private const val REQUEST_KEY = EditorScreenFragment.REQUEST_KEY
private const val BUNDLE_KEY = EditorScreenFragment.BUNDLE_KEY


class CustomDialogFragment : DialogFragment() {
    private var alertMessage : String? = null
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
            alertMessage = it.getString(ALERT_MSG_BUNDLE_KEY)
            positiveButtonText = it.getString(POSITIVE_BTN_TEXT_BUNDLE_KEY)
            negativeButtonText = it.getString(NEGATIVE_BTN_TEXT_BUNDLE_KEY)
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

        binding.apply {
            fragmentCustomDialogTvAlert.text = alertMessage
            fragmentCustomDialogBtnPositive.text = positiveButtonText
            fragmentCustomDialogBtnNegative.text = negativeButtonText


            fragmentCustomDialogBtnPositive.setOnClickListener {
                val result = true
                requireActivity().supportFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(BUNDLE_KEY to result)
                )
            }

            fragmentCustomDialogBtnNegative.setOnClickListener {
                val result = false
                requireActivity().supportFragmentManager.setFragmentResult(
                    REQUEST_KEY,
                    bundleOf(BUNDLE_KEY to result)
                )
            }
        }
    }


    companion object {

        fun newInstance(
            alertMessage: String,
            positiveBtntext: String,
            negativeBtnText: String
        ): CustomDialogFragment {

            val fragment = CustomDialogFragment()

            fragment.arguments = Bundle().apply {
                putString(ALERT_MSG_BUNDLE_KEY, alertMessage)
                putString(POSITIVE_BTN_TEXT_BUNDLE_KEY, positiveBtntext)
                putString(NEGATIVE_BTN_TEXT_BUNDLE_KEY, negativeBtnText)
            }

            return fragment
        }

    }

}