package com.example.noteit.editorscreen.customdialogfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.noteit.R
import com.example.noteit.databinding.FragmentCustomDialogBinding

class CustomDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentCustomDialogBinding
    private lateinit var listener: ClickListenerSave
    private lateinit var bundle: Bundle

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
    }

    fun createInstance(alertMessage: String, positiveBtnText: String, negativeBtnText:String): CustomDialogFragment {
        val frag = CustomDialogFragment()
        bundle = Bundle()
        bundle.putString("alert", alertMessage)
        bundle.putString("positiveBtn", positiveBtnText)
        bundle.putString("negativeBtn", negativeBtnText)
        frag.arguments = bundle
        return frag
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCustomDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentCustomDialogTvAlert.text = bundle.getString("alert")
        binding.fragmentCustomDialogBtnPositive.text = bundle.getString("positiveBtn")
        binding.fragmentCustomDialogBtnNegative.text = bundle.getString("negativeBtn")

        binding.fragmentCustomDialogBtnPositive.setOnClickListener {
            if (bundle.getString("positiveBtn") == "Save") listener.onSaveNote()
            else listener.onCancelDialog()
        }
        binding.fragmentCustomDialogBtnNegative.setOnClickListener {
            if(bundle.getString("positiveBtn")=="Save") listener.onCancelDialog()
            else listener.onDiscardNote()
        }
    }

    fun initClickListener(buttonCallback: ClickListenerSave) {
        this.listener = buttonCallback
    }

    interface ClickListenerSave {
        fun onSaveNote()
        fun onCancelDialog()
        fun onDiscardNote()
    }
}