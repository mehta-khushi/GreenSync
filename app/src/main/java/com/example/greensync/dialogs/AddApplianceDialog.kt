package com.example.greensync.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.greensync.R
import com.example.greensync.data.Appliance
import com.example.greensync.viewmodels.ApplianceViewModel

class AddApplianceDialog : DialogFragment() {
    private val viewModel: ApplianceViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_appliance_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAdd = view.findViewById<ImageButton>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.edit_appliance_name).text.toString()
            val power = view.findViewById<EditText>(R.id.edit_appliance_power).text.toString().toDoubleOrNull() ?: 0.0
            val hours = view.findViewById<EditText>(R.id.edit_appliance_duration).text.toString().toDoubleOrNull() ?: 0.0

            if (name.isNotBlank() && power > 0 && hours > 0) {
                viewModel.addAppliance(Appliance(0, name, power, hours))
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Invalid input!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}