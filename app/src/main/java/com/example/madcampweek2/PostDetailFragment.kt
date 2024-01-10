// PostDetailFragment.kt

package com.example.madcampweek2

import CommentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context
import android.os.Bundle
import android.telecom.Call as TelecomCall  // 이름 충돌 방지를 위한 alias 사용
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
//import com.android.volley.Response
import com.example.madcampweek2.network.RetrofitClient
import com.google.android.material.textfield.TextInputEditText
import org.w3c.dom.Comment

class PostDetailFragment : Fragment() {

    companion object {

        fun newInstance(title: String, content: String, created_at: String): PostDetailFragment {
            val fragment = PostDetailFragment()
            val args = Bundle()
            args.putString("title", title)
            args.putString("content", content)
            args.putString("created_at", created_at)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_detail, container, false)

        // 전달된 데이터를 가져와서 화면에 표시
        val title = arguments?.getString("title")
        val content = arguments?.getString("content")
        val created_at = arguments?.getString("created_at")

        // XML에서 정의한 TextView에 데이터 설정
        view.findViewById<TextView>(R.id.titleTextView).text = title
        view.findViewById<TextView>(R.id.contentTextView).text = content
        view.findViewById<TextView>(R.id.createdAtTextView).text = created_at

        // 툴바의 뒤로가기 버튼
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            // 이전 페이지로 이동
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 댓글 입력과 댓글 올리기 버튼
        val commentInputEditText = view.findViewById<TextInputEditText>(R.id.commentInputEditText)
        val btnPostComment = view.findViewById<Button>(R.id.btnPostComment) // 수정된 부분

        btnPostComment.setOnClickListener {
            // 댓글 입력 확인
            val comment = commentInputEditText.text.toString()
            if (comment.isEmpty()) {
                // 댓글이 입력되지 않았을 때 메시지 표시
                Toast.makeText(requireContext(), "댓글을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                // 댓글이 입력된 경우, 여기서 서버에 댓글을 업로드하는 코드를 추가할 수 있습니다.
                // 이 예시에서는 간단하게 댓글이 입력되었다는 메시지만 출력합니다.
                Toast.makeText(requireContext(), "댓글이 올라갔습니다: $comment", Toast.LENGTH_SHORT).show()

                // 댓글 업로드 함수 호출
                // postId는 실제로 해당 포스트의 고유 식별자여야 합니다. 수정이 필요한 경우 수정하세요.
                val postId = 1  // 예시로 postId를 1로 지정
//                uploadComment(comment, postId)
            }
        }

        return view
    }

    data class YourCommentClass(
        val post: Post,  // Post 모델에 대한 정보가 들어갈 것입니다.
        val content: String,
        val created_at: String  // 날짜와 시간 형식에 따라 수정 필요
    )

//    private fun uploadComment(comment: String, postId: Int) {
//        val sharedPrefs = requireContext().getSharedPreferences("YourSharedPrefs", Context.MODE_PRIVATE)
//        val authToken = sharedPrefs.getString("authToken", null)
//
//        if (authToken != null) {
//            val commentService = RetrofitClient.getInstance(requireContext()).create(CommentService::class.java)
//            val call = commentService.uploadComment("Token $authToken", postId, comment)
//
//            call.enqueue(object : Callback<YourCommentClass?> {
//                override fun onResponse(call: Call<YourCommentClass?>, response: retrofit2.Response<YourCommentClass?>) {
//                    if (response.isSuccessful) {
//                        val newComment = response.body()
//                        // 필요한대로 새로운 댓글을 처리합니다.
//                        // 예: 새로운 댓글로 UI를 업데이트합니다.
//                    } else {
//                        // 처리되지 않은 응답을 처리합니다.
//                        // 응답 코드를 확인하고 다른 경우를 처리할 수 있습니다.
//                        Log.e("CommentUpload", "댓글 업로드 실패. 코드: ${response.code()}")
//                    }
//                }
//
//                override fun onFailure(call: Call<YourCommentClass?>, t: Throwable) {
//                    // 실패를 처리합니다.
//                    Log.e("CommentUpload", "댓글 업로드 중 오류 발생", t)
//                }
//            })
//
//        } else {
//            // authToken이 null인 경우 처리
//            Log.e("CommentUpload", "AuthToken이 null입니다.")
//        }
//    }
}
