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
    lateinit var alertMessage: String

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_shape)
    }

    fun createDialog(alertMessage: String, positiveBtnText: String, negativeBtnText:String) {
        bundle = Bundle()
        bundle.putString("alert", alertMessage)
        bundle.putString("positiveBtn", positiveBtnText)
        bundle.putString("negativeBtn", negativeBtnText)

        this.alertMessage =  alertMessage
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
            if (bundle.getString("positiveBtn") == "Save") listener.onPositiveClick()
            else dialog?.dismiss()

        }
        binding.fragmentCustomDialogBtnNegative.setOnClickListener {
            if(bundle.getString("positiveBtn")=="Save") dialog?.dismiss()
            else listener.onNegativeClick()
        }
    }

    fun initClickListener(buttonCallback: ClickListenerSave) {
        this.listener = buttonCallback
    }

    interface ClickListenerSave {
        fun onPositiveClick()
        fun onNegativeClick()
    }
}