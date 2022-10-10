package com.example.noteit.editorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.noteit.databinding.FragmentEditorScreenBinding
import com.example.noteit.editorscreen.customdialogfragment.CustomDialogFragment
import com.example.noteit.homescreen.viewmodel.HomeScreenViewModel
import com.example.noteit.model.Note
import kotlin.properties.Delegates

class EditorScreenFragment : Fragment(), CustomDialogFragment.ClickListenerSave {
    private val viewModel: HomeScreenViewModel by activityViewModels()
    private lateinit var binding: FragmentEditorScreenBinding
    private lateinit var dialogInstance: CustomDialogFragment
    private lateinit var safeArgs : EditorScreenFragmentArgs
    private var noteId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEditorScreenBinding.inflate(inflater, container, false)
        safeArgs = EditorScreenFragmentArgs.fromBundle(requireArguments())
        noteId = safeArgs.note.id

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareNoteForEditing()

        binding.fragmentEditorBtnSave.setOnClickListener {
            saveNote()
        }
        binding.fragmentEditorBtnBack.setOnClickListener {
            discardNote()
        }
        dialogInstance = CustomDialogFragment()
    }

    private fun saveNote() {
        dialogInstance.createInstance("Save Note ?", "Save", "Discard")
        showDialog()
    }

    override fun onSaveNote() {

        if (noteId == 0) {
            if (binding.fragmentEditorTextTitle.text.toString().isNotEmpty()) {
                viewModel.insertNote(Note(binding.fragmentEditorTextTitle.text.toString(), binding.fragmentEditorTextDescription.text.toString()))
                Toast.makeText(context, "Note Saved!", Toast.LENGTH_SHORT).show()
                dialogInstance.dismiss()
            } else {
                Toast.makeText(context, "Please Enter a Title", Toast.LENGTH_SHORT).show()
                dialogInstance.dismiss()
            }
        } else {
            val noteTitle = binding.fragmentEditorTextTitle.text.toString()
            val noteDescription = binding.fragmentEditorTextDescription.text.toString()
            viewModel.updatenote(noteId,noteTitle,noteDescription)
            Toast.makeText(context, "Note Saved!", Toast.LENGTH_SHORT).show()
            dialogInstance.dismiss()
        }
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

    override fun onDiscardNote() {
        findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())
    }

    private fun showDialog() {
        dialogInstance.show(childFragmentManager, "ShowDialog")
        dialogInstance.initClickListener(this)
        dialogInstance.isCancelable = false
    }

    override fun onCancelDialog() {
        dialogInstance.dismiss()
    }

    private fun prepareNoteForEditing() {
        arguments?.let {
            val safeArgs = EditorScreenFragmentArgs.fromBundle(it)
            val note = safeArgs.note
            binding.fragmentEditorTextTitle.setText(note.title)
            binding.fragmentEditorTextDescription.setText(note.description)
            Toast.makeText(context,noteId.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}