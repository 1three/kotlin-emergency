package fastcampus.part1.emergency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * 응급 의료 정보 앱
 *
 * 1. 데이터 조회, 저장, 삭제
 *    유저 정보 (이름, 생년월일, 혈액형, 비상연락처, 기타)
 *    화면 전환 (데이터 저장)
 * 2. 전화 앱 실행
 * 3. 다양한 위젯, 테마 사용
 * */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}