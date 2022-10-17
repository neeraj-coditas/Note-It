package com.example.noteit.ui.editorscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteit.data.Note
import com.example.noteit.databinding.FragmentEditorScreenBinding
import com.example.noteit.ui.editorscreen.customdialogfragment.CustomDialogFragment
import com.example.noteit.viewmodel.NoteViewModel

class EditorScreenFragment : Fragment() {
    private val viewModel: NoteViewModel by viewModels()
    private lateinit var binding: FragmentEditorScreenBinding
    private lateinit var safeArgs: EditorScreenFragmentArgs
    private var noteId = 0
    private val noteTime: Long by lazy {
        System.currentTimeMillis()
    }
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

        bindDataOnOpeningNote()

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

    private fun saveNote() {
        val dialogInstance = CustomDialogFragment.newInstance(
            SAVE_DIALOG_MESSAGE,
            SAVE_DIALOG_POSITIVE_BUTTON,
            SAVE_DIALOG_NEGATIVE_BUTTON
        )

        showDialog(dialogInstance)

        requireActivity().supportFragmentManager
            .setFragmentResultListener(REQUEST_KEY, viewLifecycleOwner) { _, bundle ->

                val result = bundle.getBoolean(BUNDLE_KEY)
                if (result) {
                    if (noteId == 0) {
                        if (binding.fragmentEditorTextTitle.text.toString().isNotEmpty()) {
                            viewModel.insertNote(
                                Note(
                                    binding.fragmentEditorTextTitle.text.toString(),
                                    binding.fragmentEditorTextDescription.text.toString(),
                                    noteTime
                                )
                            )
                            makeToast("Note Saved!")
                            findNavController().navigate(EditorScreenFragmentDirections.actionEditorScreenFragmentToHomeFragment())
                        } else {
                            makeToast("Please Enter a Title")
                        }

                    } else {
                        if ((binding.fragmentEditorTextTitle.text.toString().isNotEmpty())) {
                            val note = safeArgs.note
                            note.title = binding.fragmentEditorTextTitle.text.toString()
                            note.description = binding.fragmentEditorTextDescription.text.toString()
                            note.timeStamp = noteTime
                            viewModel.updateNote(note)
                            makeToast("Note Updated!")
                        } else {
                            makeToast("Please Enter a Title")
                        }

                    }
                }
                dialogInstance.dismiss()
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
            val dialogInstance = CustomDialogFragment.newInstance(
                DISCARD_DIALOG_MESSAGE,
                DISCARD_DIALOG_POSITIVE_BUTTON,
                DISCARD_DIALOG_NEGATIVE_BUTTON
            )

            showDialog(dialogInstance)

            requireActivity().supportFragmentManager
                .setFragmentResultListener(REQUEST_KEY, viewLifecycleOwner) { _, bundle ->

                    val result = bundle.getBoolean(BUNDLE_KEY)
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

    private fun bindDataOnOpeningNote() {
        val note = safeArgs.note
        binding.editorscreen = note
    }

    private fun previewNote() {

        binding.run {
            fragmentEditorTextTitle.isEnabled = false
            fragmentEditorTextDescription.isEnabled = false
            fragmentEditorTextTitle.hint = CLEAR_HINT
            fragmentEditorTextDescription.hint = CLEAR_HINT
            fragmentEditorBtnBack.visibility = View.INVISIBLE
            fragmentEditorBtnPreview.visibility = View.INVISIBLE
            fragmentEditorBtnEdit.visibility = View.VISIBLE

            fragmentEditorBtnEdit.setOnClickListener {
                fragmentEditorTextTitle.isEnabled = true
                fragmentEditorTextDescription.isEnabled = true
                fragmentEditorTextTitle.hint = EDITTEXT_TITLE_HINT
                fragmentEditorTextDescription.hint = EDITTEXT_DESCRIPTION_HINT
                fragmentEditorBtnBack.visibility = View.VISIBLE
                fragmentEditorBtnPreview.visibility = View.VISIBLE
                fragmentEditorBtnEdit.visibility = View.INVISIBLE
            }

        }
    }

    private fun showDialog(dialogInstance: CustomDialogFragment) {
        dialogInstance.show(childFragmentManager, EditorScreenFragment::class.java.simpleName)
    }

    private fun makeToast(toastMessage: String) {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val BUNDLE_KEY = "bundleKey"
        const val EDITTEXT_TITLE_HINT = "Title"
        const val EDITTEXT_DESCRIPTION_HINT = "Type Something..."
        const val CLEAR_HINT = ""
        const val SAVE_DIALOG_MESSAGE = "Save Note ?"
        const val SAVE_DIALOG_POSITIVE_BUTTON = "Save"
        const val SAVE_DIALOG_NEGATIVE_BUTTON = "Discard"
        const val DISCARD_DIALOG_MESSAGE = "Are you sure you want to discard your changes ?"
        const val DISCARD_DIALOG_POSITIVE_BUTTON = "Keep"
        const val DISCARD_DIALOG_NEGATIVE_BUTTON = "Discard"
    }
}

