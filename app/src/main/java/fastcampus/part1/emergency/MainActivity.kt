package fastcampus.part1.emergency

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import fastcampus.part1.emergency.databinding.ActivityMainBinding

/**
 * 응급 의료 정보 앱
 *
 * 1. 데이터 조회, 저장, 삭제
 *    유저 정보 (이름, 생년월일, 혈액형, 비상연락처, 주의사항)
 *    화면 전환 (데이터 저장)
 * 2. 전화 앱 실행
 * 3. 다양한 위젯, 테마 사용
 * */

/**
 * Intent (메시징 객체)
 * 다른 앱 구성 요소에 작업 요청 시 사용 (액티비티 시작, 서비스 시작, 브로드캐스트 전달에 사용)
 * */

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goInputActivityButton.setOnClickListener {
            // 명시적 intent "InputActivity 실행해"
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            deleteUserInformation()
        }

        binding.callButton.setOnClickListener {
            // 암시적 intent "전화 관련 앱 실행해"
            with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.userEmergencyContact.text.toString().replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getUserInformation()
    }

    private fun getUserInformation() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.userName.text = getString(NAME, getString(R.string.undefined))
            binding.userBirth.text = getString(BIRTH, getString(R.string.undefined))
            binding.userBloodType.text = getString(BLOOD_TYPE, getString(R.string.undefined))

            val emergencyContact = getString(EMERGENCY_CONTACT, getString(R.string.undefined))
            binding.callButton.isVisible = emergencyContact != "미정"
            binding.userEmergencyContact.text = emergencyContact

            val caution = getString(CAUTION, "")
            val isCautionVisible = caution.isNullOrEmpty().not()
            binding.caution.isVisible = isCautionVisible
            binding.userCaution.isVisible = isCautionVisible
            binding.userCaution.text = caution
        }
    }

    private fun deleteUserInformation() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            clear()
            apply()
        }
        Toast.makeText(this, R.string.userInfo_reset, Toast.LENGTH_SHORT).show()
        getUserInformation()
    }
}