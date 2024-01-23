package fastcampus.part1.emergency

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import fastcampus.part1.emergency.databinding.ActivityInputBinding
import java.time.LocalDate
import java.time.LocalDateTime

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
    private lateinit var binding: ActivityInputBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentDate = LocalDate.now()
        val currentYear = currentDate.year
        val currentMonth = currentDate.monthValue - 1
        val currentDayOfMonth = currentDate.dayOfMonth

        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        binding.birthTextView.text = "$currentYear-${currentMonth.inc()}-$currentDayOfMonth"

        binding.birthLayer.setOnClickListener {
            val listener = OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }

            DatePickerDialog(
                this,
                listener,
                currentYear,
                currentMonth,
                currentDayOfMonth
            ).show()
        }

        binding.cautionEditText.isVisible = binding.cautionCheckBox.isChecked

        binding.cautionCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.cautionEditText.isVisible = isChecked
        }
    }
}