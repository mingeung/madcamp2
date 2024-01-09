// WriteFragment.kt
package com.example.madcampweek2
import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Button
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import retrofit2.HttpException


class WriteFragment : Fragment() {
//    private val apiService = ApiClient.apiService
//    private var postResponseId: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_write, container, false)

        // 툴바의 뒤로가기 버튼
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 제목과 내용을 입력받는 EditText
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etContent = view.findViewById<EditText>(R.id.etContent)

        val btnUpload = view.findViewById<Button>(R.id.btnUpload)
        btnUpload.setOnClickListener {
            // 사용자가 입력한 제목과 내용을 가져오기 --> 이건 잘 됨
            val title = etTitle.text.toString()
            val content = etContent.text.toString()

            // 입력값이 비어있는지 확인
            if (title.isEmpty()) {
                showToast("제목을 입력하세요")
                return@setOnClickListener
            }

            if (content.isEmpty()) {
                showToast("내용을 입력하세요")
                return@setOnClickListener
            }


//            // 데이터베이스에 업로드
//            uploadToServer(title, content)

            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.popBackStack()
        }


        return view
    }
//    private fun uploadToServer(title: String, content: String) {
//        val call = apiService.createPost(PostRequest(title, content))
//
//        call.enqueue(object : Callback<PostResponse> {
//            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
//
//
//                if (response.isSuccessful) {
//                    // 업로드 성공
//                    val postResponse = response.body()
//                    if (postResponse != null) {
//                        // 서버로부터 받은 응답에서 필요한 정보를 사용할 수 있음
//                        Log.d("WriteFragment", "업로드 성공! ID: ${postResponseId}")
//                    } else {
//                        Log.e("WriteFragment", "응답이 null입니다.")
//                    }
//                } else {
//                    // 업로드 실패
//                    try {
//                        val errorBody = response.errorBody()?.string()
//                        val errorMessage = errorBody ?: "알 수 없는 오류"
//                        Log.e("WriteFragment", "업로드 실패. 상태 코드: ${response.code()}, 오류 메시지: $errorMessage")
//                    } catch (e: Exception) {
//                        Log.e("WriteFragment", "업로드 실패. 상태 코드: ${response.code()}", e)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
//                Log.e("WriteFragment", "통신 실패", t)
//                // 서버에서 전달한 오류 메시지를 확인합니다.
//                if (t is HttpException) {
//                    val errorResponse = t.response()
//                    val errorBody = errorResponse?.errorBody()?.string()
//                    val errorMessage = errorBody ?: "알 수 없는 오류"
//                    Log.e("WriteFragment", "서버 오류 메시지: $errorMessage")
//                }
//            }
//        })
//    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
