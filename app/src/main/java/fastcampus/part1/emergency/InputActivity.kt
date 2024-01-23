package fastcampus.part1.emergency

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
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
 * 날짜 선택 캘릭더
 *
 * SharedPreferences
 * 1) 양이 적고 단순 데이터 저장
 * 2) MODE_PRIVATE : 오직 자신만 접근 가능한 데이터
 * 3) 변경 사항 저장 : commit(), apply()
 * */

/**
 * commit()
 * 동기적 동작 (sync)
 * 데이터 저장 시, 현재 스레드(작업 공간)를 막고 다음 동작 실행
 *
 * apply()
 * 비동기적 동작 (async)
 * 데이터 저장 시, 현재 스레드(작업 공간)가 아닌 다른 스레드에서 데이터 저장
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

        binding.saveButton.setOnClickListener {
            saveUserInformation()
            finish()
        }
    }

    private fun setupBloodTypeSpinner() {
        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.blood_types, android.R.layout.simple_spinner_dropdown_item
        )
    }

    private fun setupCurrentDate(currentDate: LocalDate) {
        binding.birthTextView.text = formatDate(currentDate)
    }

    private fun setupBirthLayerClickListener(currentDate: LocalDate) {
        binding.birthLayer.setOnClickListener {
            val listener = OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthTextView.text =
                    formatDate(LocalDate.of(year, month + MONTH_OFFSET, dayOfMonth))
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

    private fun saveUserInformation() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.nameEditText.text.toString())
            putString(BIRTH, binding.birthTextView.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(EMERGENCY_CONTACT, binding.emergencyContactEditText.text.toString())
            putString(CAUTION, getCaution())
            apply()
        }

        Toast.makeText(this, "사용자 정보가 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String {
        val rh = if (binding.bloodTypePlus.isChecked) "Rh+" else "Rh-"
        val abo = binding.bloodTypeSpinner.selectedItem.toString()
        return "$rh $abo"
    }

    private fun getCaution(): String {
        return if (binding.cautionCheckBox.isChecked) binding.cautionEditText.text.toString() else ""
    }

    private fun formatDate(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}