package com.example.noteit.editorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
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
    private lateinit var safeArgs: EditorScreenFragmentArgs
    private var noteId = 0
    private val onBackPressImpl = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            discardNote()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, onBackPressImpl)
        binding = FragmentEditorScreenBinding.inflate(inflater, container, false)
        safeArgs = EditorScreenFragmentArgs.fromBundle(requireArguments())
        noteId = safeArgs.note.id

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogInstance = CustomDialogFragment()
        prepareNoteForEditing()

        binding.fragmentEditorBtnSave.setOnClickListener {
            saveNote()
        }
        binding.fragmentEditorBtnBack.setOnClickListener {
            discardNote()
        }
        binding.fragmentEditorBtnPreview.setOnClickListener {
            previewNote()
        }
    }

    private fun prepareNoteForEditing() {
        arguments?.let {
            val safeArgs = EditorScreenFragmentArgs.fromBundle(it)
            val note = safeArgs.note
            binding.fragmentEditorTextTitle.setText(note.title)
            binding.fragmentEditorTextDescription.setText(note.description)
        }
    }

    private fun saveNote() {
        dialogInstance.createDialog(
            "Save Note ?",
            "Save",
            "Discard"
        )
        showDialog()
    }

    private fun discardNote() {
        arguments?.let {
            val safeArgs = EditorScreenFragmentArgs.fromBundle(it)
            val note = safeArgs.note

            val currentTitle = binding.fragmentEditorTextTitle.text.toString()
            val currentDescription = binding.fragmentEditorTextDescription.text.toString()

            val isDataNotEmpty = binding.fragmentEditorTextTitle.text?.isNotEmpty() == true
                    || binding.fragmentEditorTextDescription.text?.isNotEmpty() == true

            val hasDataChanged =
                note.title != currentTitle || note.description != currentDescription

            if (isDataNotEmpty && hasDataChanged) {
                dialogInstance.createDialog(
                    "Are you sure you want to discard your changes",
                    "Keep",
                    "Discard"
                )
                showDialog()
            } else {
                findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())
            }
        }

    }

    private fun previewNote() {
        binding.fragmentEditorTextTitle.isEnabled = false
        binding.fragmentEditorTextDescription.isEnabled = false
        binding.fragmentEditorBtnBack.visibility = View.INVISIBLE
        binding.fragmentEditorBtnPreview.visibility = View.INVISIBLE
        binding.fragmentEditorBtnEdit.visibility = View.VISIBLE

        binding.fragmentEditorBtnEdit.setOnClickListener {
            binding.fragmentEditorTextTitle.isEnabled = true
            binding.fragmentEditorTextDescription.isEnabled = true
            binding.fragmentEditorBtnBack.visibility = View.VISIBLE
            binding.fragmentEditorBtnPreview.visibility = View.VISIBLE
            binding.fragmentEditorBtnEdit.visibility = View.INVISIBLE
        }
    }

    private fun showDialog() {
        dialogInstance.show(childFragmentManager, DIALOG_TAG)
        dialogInstance.initClickListener(this)
        dialogInstance.isCancelable = false
    }

    override fun onPositiveClick() {
        if (noteId == 0) {
            if (binding.fragmentEditorTextTitle.text.toString().isNotEmpty()) {
                viewModel.insertNote(
                    Note(
                        binding.fragmentEditorTextTitle.text.toString(),
                        binding.fragmentEditorTextDescription.text.toString()
                    )
                )
                Toast.makeText(context, "Note Saved!", Toast.LENGTH_SHORT).show()
                dialogInstance.dismiss()
            } else {
                Toast.makeText(context, "Please Enter a Title", Toast.LENGTH_SHORT).show()
                dialogInstance.dismiss()
            }

            findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())

        } else {
            val note = safeArgs.note
            note.title = binding.fragmentEditorTextTitle.text.toString()
            note.description = binding.fragmentEditorTextDescription.text.toString()
            viewModel.updateNote(note)
            Toast.makeText(context, "Note Saved!", Toast.LENGTH_SHORT).show()
            dialogInstance.dismiss()
        }

    }

    override fun onNegativeClick() {
        findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())
    }

    companion object {
        const val DIALOG_TAG = "ShowDialog"
    }
}

