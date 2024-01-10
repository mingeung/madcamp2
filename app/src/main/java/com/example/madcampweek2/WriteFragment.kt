// WriteFragment.kt
package com.example.madcampweek2

import PostRequest
import PostService
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_write, container, false)

        // 툴바의 뒤로가기 버튼
        view.findViewById<AppCompatImageButton>(R.id.btnBack).setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 제목과 내용을 입력받는 EditText
        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etContent = view.findViewById<EditText>(R.id.etContent)

        val btnUpload = view.findViewById<Button>(R.id.btnUpload)
        btnUpload.setOnClickListener {
            // 사용자가 입력한 제목과 내용을 가져오기
            val title = etTitle.text.toString()
            val content = etContent.text.toString()

            // 입력값이 비어있는지 확인
            if (title.isEmpty() || content.isEmpty()) {
                showToast("제목과 내용을 모두 입력하세요.")
                return@setOnClickListener
            }

            // 데이터베이스에 업로드
            uploadToServer(title, content)

            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.popBackStack()
        }

        return view
    }

    private fun uploadToServer(title: String, content: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8001/") // Django 서버의 주소로 변경
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PostService::class.java)

        // PostRequest 객체를 생성하여 데이터를 전달
        val postRequest = PostRequest(title, content)
        val call = service.createPost(postRequest)

        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful) {
                    showToast("게시물이 성공적으로 업로드되었습니다.")
                } else {
                    showToast("게시물 업로드에 실패했습니다.")
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                showToast("네트워크 오류로 인해 게시물 업로드에 실패했습니다.")
            }
        })
    }

    private fun showToast(message: String) {
        // Toast 메시지를 띄우는 코드
    }
}