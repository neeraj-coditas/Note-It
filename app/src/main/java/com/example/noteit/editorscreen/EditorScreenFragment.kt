package com.example.noteit.editorscreen

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteit.databinding.FragmentEditorScreenBinding
import com.example.noteit.editorscreen.customdialogfragment.CustomDialogFragment
import com.example.noteit.viewmodel.NoteViewModel
import com.example.noteit.model.Note
import java.util.*

class EditorScreenFragment : Fragment(), CustomDialogFragment.CustomDialogClickListener {
    private val viewModel: NoteViewModel by viewModels()
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

    private fun saveNote(){
        dialogInstance = CustomDialogFragment.newInstance("Save Note ?",
            "Save",
            "Discard",
            "actionSave")

        showDialog()

        requireActivity().supportFragmentManager
            .setFragmentResultListener("saveKey", viewLifecycleOwner) { requestKey, bundle ->
                val result = bundle.getBoolean("bundleKey")

                if (result) {
                    val noteTime =
                        SimpleDateFormat(
                            "dd-M HH:mm:ss",
                            Locale.getDefault()
                        ).format(Date())
                    if (noteId == 0) {
                        if (binding.fragmentEditorTextTitle.text.toString().isNotEmpty()) {
                            viewModel.insertNote(
                                Note(
                                    binding.fragmentEditorTextTitle.text.toString(),
                                    binding.fragmentEditorTextDescription.text.toString(),
                                    noteTime
                                )
                            )
                            Toast.makeText(context, "Note Saved!", Toast.LENGTH_SHORT).show()
                            dialogInstance.dismiss()

                        } else {
                            Toast.makeText(context, "Please Enter a Title", Toast.LENGTH_SHORT)
                                .show()
                            dialogInstance.dismiss()
                        }

                        findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())

                    } else {
                        val note = safeArgs.note
                        note.title = binding.fragmentEditorTextTitle.text.toString()
                        note.description = binding.fragmentEditorTextDescription.text.toString()
                        note.timeStamp = noteTime
                        viewModel.updateNote(note)
                        Toast.makeText(context, "Note Updated!", Toast.LENGTH_SHORT).show()
                        dialogInstance.dismiss()
                    }
                } else {
                    dialogInstance.dismiss()
                }

            }

    }

    private fun discardNote() {
        val note = safeArgs.note

        val currentTitle = binding.fragmentEditorTextTitle.text.toString()
        val currentDescription = binding.fragmentEditorTextDescription.text.toString()

        val isDataNotEmpty = binding.fragmentEditorTextTitle.text?.isNotEmpty() == true
                || binding.fragmentEditorTextDescription.text?.isNotEmpty() == true

        val hasDataChanged =
            note.title != currentTitle || note.description != currentDescription

        if (isDataNotEmpty && hasDataChanged) {
            dialogInstance = CustomDialogFragment.newInstance(
                "Are you sure you want to discard your changes",
                "Keep",
                "Discard",
                "actionDiscard"
            )

            showDialog()

            requireActivity().supportFragmentManager
                .setFragmentResultListener("discardKey", viewLifecycleOwner) { requestKey, bundle ->
                    val result = bundle.getBoolean("bundleKey")

                    if (result) {
                        dialogInstance.dismiss()
                    } else {
                        findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())
                    }
                }

        } else {
            findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())
        }

    }

    private fun prepareNoteForEditing() {
        val note = safeArgs.note
        binding.editorscreen = note
    }

    private fun previewNote() {

        binding.apply {
            fragmentEditorTextTitle.isEnabled = false
            fragmentEditorTextDescription.isEnabled = false
            fragmentEditorBtnBack.visibility = View.INVISIBLE
            fragmentEditorBtnPreview.visibility = View.INVISIBLE
            fragmentEditorBtnEdit.visibility = View.VISIBLE

            fragmentEditorBtnEdit.setOnClickListener {
                fragmentEditorTextTitle.isEnabled = true
                fragmentEditorTextDescription.isEnabled = true
                fragmentEditorBtnBack.visibility = View.VISIBLE
                fragmentEditorBtnPreview.visibility = View.VISIBLE
                fragmentEditorBtnEdit.visibility = View.INVISIBLE
            }

        }
    }

    private fun showDialog() {
        dialogInstance.show(childFragmentManager, SHOW_DIALOG)
        dialogInstance.initClickListener(this)
    }

    override fun onPositiveClick() {
        if (true) {
            if (noteId == 0) {
                if (binding.fragmentEditorTextTitle.text.toString().isNotEmpty()) {
                    val noteTime =
                        SimpleDateFormat("dd-M HH:mm", Locale.getDefault()).format(Date())
                    viewModel.insertNote(
                        Note(
                            binding.fragmentEditorTextTitle.text.toString(),
                            binding.fragmentEditorTextDescription.text.toString(),
                            noteTime
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
                val noteTime = SimpleDateFormat("dd-M HH:mm", Locale.getDefault()).format(Date())
                note.title = binding.fragmentEditorTextTitle.text.toString()
                note.description = binding.fragmentEditorTextDescription.text.toString()
                note.timeStamp = noteTime
                viewModel.updateNote(note)
                Toast.makeText(context, "Note Updated!", Toast.LENGTH_SHORT).show()
                dialogInstance.dismiss()
            }
        } else {
            dialogInstance.dismiss()
        }

    }

    override fun onNegativeClick() {
        if (true) {
            findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())

        } else {
            dialogInstance.dismiss()
        }

    }

    companion object {
        const val SHOW_DIALOG = "ShowDialog"
    }
}

