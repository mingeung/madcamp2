package com.example.madcampweek2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.http.POST


class Fragment2_Community : Fragment(),PostAdapter.PostClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment2_community, container, false)

        recyclerView = view.findViewById(R.id.recycler)
        postAdapter = PostAdapter(this)


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = postAdapter

        // 각 항목에 수직 경계선 추가
        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        recyclerView.addItemDecoration(itemDecoration)

        // 예시 데이터
        val examplePosts = listOf(
            Post("제목 1", "내용 1"),
            Post("제목 2", "내용 2"),
            Post("제목 3", "내용 3"),
            Post("제목 1", "내용 1"),
            Post("제목 2", "내용 2"),
            Post("제목 3", "내용 3"),
            Post("제목 1", "내용 1"),
            Post("제목 2", "내용 2"),
            Post("제목 3", "내용 3"),
            Post("제목 1", "내용 1"),
            Post("제목 2", "내용 2"),
            Post("제목 3", "내용 3")
        )
        // 어댑터에 데이터 설정
        postAdapter.setPosts(examplePosts)

        //글쓰기 버튼에 대한 클릭 이벤트 처리
        view.findViewById<Button>(R.id.btnWrite).setOnClickListener {
            // FragmentTransaction 시작
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            // 다음으로 이동할 프래그먼트 인스턴스 생성
            val nextFragment = WriteFragment()
            // 프래그먼트 교체
            fragmentTransaction.replace(R.id.main_Container, nextFragment)
            // 백 스택에 추가 (필요에 따라)
            fragmentTransaction.addToBackStack(null)
            // 변경사항 반영
            fragmentTransaction.commit()
        }
        return view

    }

    override fun onPostClick(post: Post) {
        // Handle click event for a post
    }

}
