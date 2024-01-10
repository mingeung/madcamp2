//CommentService.kt
import com.example.madcampweek2.PostDetailFragment
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


data class CommentUploadRequest(
    val post_id: Int,
    val content: String
)

data class CommentUploadResponse(
    val post_id: Int,
    val content: String
)
interface CommentService {
    @POST("comments/upload/")
    fun uploadComment(
        @Header("Authorization") authToken: String,
        @Query("post_id") postId: Int,
        @Field("content") content: String
    ): Call<Comment>
}