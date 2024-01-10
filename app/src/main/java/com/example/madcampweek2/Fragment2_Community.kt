//Fragment2_Community
package com.example.madcampweek2

import PostService
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampweek2.network.RetrofitClient.gson
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.http.POST
//게시물 불러오기 때문에 import 하는 것들
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Fragment2_Community : Fragment(),PostAdapter.PostClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter


    override fun onResume() {
        super.onResume()
        // 화면이 다시 나타날 때마다 데이터를 새로 로드
        loadData()
    }

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
        val gson : Gson = GsonBuilder()
            .setLenient()
            .create()

        // Retrofit을 사용하여 Django 서버에서 데이터 가져오기
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8001/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(PostService::class.java)
        val call = service.getPosts()

        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    posts?.let { postAdapter.setPosts(it.reversed()) }
                } else {
                    // HTTP 요청이 성공하지 않은 경우, 오류 로그 출력
                    Log.e("NetworkError", "HTTP error code: ${response.code()}")
                    Log.e("NetworkError", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // 처리 실패 시 동작
                Log.e("NetworkError", "Network request failed", t)
            }
        })
//        // 예시 데이터
//        val examplePosts = listOf(
//            Post("제목 1", "내용 1"),
//            Post("제목 2", "내용 2"),
//            Post("제목 3", "내용 3"),
//            Post("제목 1", "내용 1"),
//            Post("제목 2", "내용 2"),
//            Post("제목 3", "내용 3"),
//            Post("제목 1", "내용 1"),
//            Post("제목 2", "내용 2"),
//            Post("제목 3", "내용 3"),
//            Post("제목 1", "내용 1"),
//            Post("제목 2", "내용 2"),
//            Post("제목 3", "내용 3")
//        )
//        // 어댑터에 데이터 설정
//        postAdapter.setPosts(examplePosts)

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
        // 화면이 열릴 때마다 데이터를 새로 로드
        loadData()
        onResume()

        return view
    }

    private fun loadData() {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        // Retrofit을 사용하여 Django 서버에서 데이터 가져오기
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8001/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(PostService::class.java)
        val call = service.getPosts()

        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    posts?.let {  postAdapter.setPosts(it.reversed())}
                } else {
                    // HTTP 요청이 성공하지 않은 경우, 오류 로그 출력
                    Log.e("NetworkError", "HTTP error code: ${response.code()}")
                    Log.e("NetworkError", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                // 처리 실패 시 동작
                Log.e("NetworkError", "Network request failed", t)
            }
        })
    }


    override fun onPostClick(post: Post) {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val created_at_date = DateUtils.convertToSeoulTime(post.created_at)
        val detailFragment = PostDetailFragment.newInstance(post.title, post.content, created_at_date)
        fragmentTransaction.replace(R.id.main_Container, detailFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

}
