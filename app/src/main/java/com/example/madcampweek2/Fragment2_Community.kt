package com.example.madcampweek2

import PostService
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class Fragment2_Community : Fragment(), PostAdapter.PostClickListener {
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

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        setupWriteButton(view)

        // Initialize data loading
        initDataLoading()

        return view
    }

    override fun onResume() {
        super.onResume()
        loadData() // Reload data when the fragment comes into view
    }

    private fun setupWriteButton(view: View) {
        view.findViewById<Button>(R.id.btnWrite).setOnClickListener {
            val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            val nextFragment = WriteFragment()
            fragmentTransaction.replace(R.id.main_Container, nextFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    private fun initDataLoading() {
        // Initialize Retrofit or any other data fetching logic here
    }

    private fun loadData() {
        val gson: Gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.10.7.27:80/")
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
                    Log.e("NetworkError", "HTTP error code: ${response.code()}")
                    Log.e("NetworkError", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
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