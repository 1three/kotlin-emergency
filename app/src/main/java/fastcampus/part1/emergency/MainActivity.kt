package fastcampus.part1.emergency

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        val goInputActivityButton = binding.goInputActivityButton
        goInputActivityButton.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            // intent.putExtra("message", "메시지")
            startActivity(intent)
        }
    }
}