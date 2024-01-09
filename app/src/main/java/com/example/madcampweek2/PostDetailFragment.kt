// PostDetailFragment.kt

package com.example.madcampweek2

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment

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

        return view
    }
}
