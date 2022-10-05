package com.example.noteit.editorscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.noteit.databinding.FragmentEditorScreenBinding
import com.example.noteit.editorscreen.customdialogfragment.CustomDialogFragment
import com.example.noteit.homescreen.viewmodel.HomeScreenViewModel
import com.example.noteit.model.Note

class EditorScreenFragment : Fragment(), CustomDialogFragment.ClickListenerSave {
    private val viewModel: HomeScreenViewModel by activityViewModels()
    private lateinit var binding: FragmentEditorScreenBinding
    private lateinit var dialogInstance: CustomDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditorScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentEditorBtnSave.setOnClickListener {
            saveNote()
        }
        binding.fragmentEditorBtnBack.setOnClickListener {
            discardNote()
        }

        dialogInstance = CustomDialogFragment()


    }

    private fun showDialog() {
        dialogInstance.show(childFragmentManager, "ShowDialog")
        dialogInstance.initClickListener(this)
        dialogInstance.isCancelable = false
    }

    private fun saveNote() {
        dialogInstance.createInstance("Save Note ?", "Save", "Discard")
        showDialog()
    }

    private fun discardNote() {
        if (binding.fragmentEditorTextTitle.text?.isNotEmpty() == true || binding.fragmentEditorTextDescription.text?.isNotEmpty() == true) {
            dialogInstance.createInstance(
                "Are you sure you want to discard your changes",
                "Keep",
                "Discard"
            )
            showDialog()
        } else {
            findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())
        }
    }

    override fun onSaveNote() {
        val noteTitle = binding.fragmentEditorTextTitle.text.toString()
        val noteDescription = binding.fragmentEditorTextDescription.text.toString()
        if (noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
            viewModel.insertNote(Note(noteTitle, noteDescription))
            Toast.makeText(context, "Note Saved!", Toast.LENGTH_SHORT).show()
            dialogInstance.dismiss()
        } else {
            Toast.makeText(context, "Please Create a Note", Toast.LENGTH_SHORT).show()
            dialogInstance.dismiss()
        }
    }

    override fun onCancelDialog() {
        dialogInstance.dismiss()
    }

    override fun onDiscardNote() {
        findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())
    }

}