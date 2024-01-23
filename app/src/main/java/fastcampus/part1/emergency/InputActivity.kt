package fastcampus.part1.emergency

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import fastcampus.part1.emergency.databinding.ActivityInputBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * ImageButton
 * RadioGroup - RadioButton
 * Spinner
 * CheckBox
 * */

/**
 * Adapter
 * list, array, collection 형태의 반복적인 데이터를 UI와 연결 시
 *
 * ArrayAdapter
 * Adapter에 배열의 데이터 넣을 경우
 *
 * DatePickerDialog
 * */

class InputActivity : AppCompatActivity() {
    companion object {
        private const val MONTH_OFFSET = 1
    }

    private lateinit var binding: ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentDate = LocalDate.now()

        setupBloodTypeSpinner()
        setupCurrentDate(currentDate)
        setupBirthLayerClickListener(currentDate)
        setupCautionCheckBox()
    }

    private fun setupBloodTypeSpinner() {
        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_spinner_dropdown_item
        )
    }

    private fun setupCurrentDate(currentDate: LocalDate) {
        binding.birthTextView.text = formatDate(currentDate)
    }

    private fun setupBirthLayerClickListener(currentDate: LocalDate) {
        binding.birthLayer.setOnClickListener {
            val listener = OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthTextView.text = formatDate(LocalDate.of(year, month + MONTH_OFFSET, dayOfMonth))
            }

            DatePickerDialog(
                this,
                listener,
                currentDate.year,
                currentDate.monthValue - MONTH_OFFSET,
                currentDate.dayOfMonth
            ).show()
        }
    }

    private fun setupCautionCheckBox() {
        binding.cautionEditText.isVisible = binding.cautionCheckBox.isChecked
        binding.cautionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.cautionEditText.isVisible = isChecked
        }
    }

    private fun formatDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}