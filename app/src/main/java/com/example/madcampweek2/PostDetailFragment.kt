// PostDetailFragment.kt

package com.example.madcampweek2

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import java.util.HashSet

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

        val title = arguments?.getString("title")
        val content = arguments?.getString("content")
        val created_at = arguments?.getString("created_at")

        view.findViewById<TextView>(R.id.titleTextView).text = title
        view.findViewById<TextView>(R.id.contentTextView).text = content
        view.findViewById<TextView>(R.id.createdAtTextView).text = created_at

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val commentInputEditText = view.findViewById<TextInputEditText>(R.id.commentInputEditText)
        val btnPostComment = view.findViewById<Button>(R.id.btnPostComment)

        btnPostComment.setOnClickListener {
            val comment = commentInputEditText.text.toString()
            if (comment.isEmpty()) {
                Toast.makeText(requireContext(), "댓글을 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                val postId = 1
                Toast.makeText(requireContext(), "댓글이 올라갔습니다: $comment", Toast.LENGTH_SHORT).show()
                saveComment(comment, postId)
                displayComments(postId, view)
            }
        }

        return view
    }

    private fun displayComments(postId: Int, view: View) {
        val sharedPrefs = requireContext().getSharedPreferences("YourSharedPrefs", Context.MODE_PRIVATE)
        val comments = sharedPrefs.getStringSet("post_$postId", HashSet())

        val commentsTextView = view.findViewById<TextView>(R.id.commentsTextView)
        commentsTextView.text = comments?.joinToString("\n") ?: ""
    }

    private fun saveComment(comment: String, postId: Int) {
        val sharedPrefs = requireContext().getSharedPreferences("YourSharedPrefs", Context.MODE_PRIVATE)
        val comments = sharedPrefs.getStringSet("post_$postId", HashSet())?.toMutableSet()

        comments?.add(comment)

        sharedPrefs.edit().putStringSet("post_$postId", comments).apply()
    }
}
