// ProfileActivity.kt
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView  // Add this import statement
import android.view.View  // Add this import statement
import androidx.appcompat.app.AppCompatActivity
import com.example.madcampweek2.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileImageView: ImageView

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profileImageView = findViewById(R.id.profileImageView)
    }

    // 프로필 이미지를 클릭했을 때 갤러리를 열도록 합니다.
    fun onProfileImageClick(view: View) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    // 갤러리에서 이미지를 선택한 후 호출되는 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            val selectedImage: Uri = data.data!!

            // Uri에서 비트맵으로 이미지 변환
            val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)

            // 이미지뷰에 선택한 이미지 설정
            profileImageView.setImageBitmap(bitmap)
        }
    }
}
