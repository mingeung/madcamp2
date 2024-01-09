package com.example.madcampweek2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madcampweek2.R
import com.example.madcampweek2.Post
import com.kakao.sdk.user.model.User

private val User.nickname: CharSequence?
    get() =this.nickname

class PostAdapter(private val onPostClickListener: PostClickListener) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var posts: List<Post> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.titleTextView.text = post.title
        holder.contentTextView.text = post.content
//        holder.writerTextView.text = post.writer.nickname
        holder.createdAtTextView.text = DateUtils.convertToSeoulTime(post.created_at)

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    //게시글 불러오기
    fun setPosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun setPost(it: Post) {

    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
//        val writerTextView: TextView = itemView.findViewById(R.id.writerTextView)
        val createdAtTextView: TextView = itemView.findViewById(R.id.createdAtTextView)
    }
    interface PostClickListener {
        fun onPostClick(post: Post)
    }

}
