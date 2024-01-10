// PostService.kt
import com.example.madcampweek2.Post
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface PostService {

    @POST("posts/") // posts/는 Django 서버의 게시물 생성 API endpoint에 해당
    fun createPost(@Body postRequest: PostRequest): Call<Post>
    @GET("posts/")
    fun getPosts(): Call<List<Post>>
    //댓글
    @FormUrlEncoded
    @POST("create_comment/")
    fun createComment(
        @Field("post_id") postId: Int,
        @Field("content") content: String
    ): Call<Comment>
}