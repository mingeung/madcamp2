import com.example.madcampweek2.PostDetailFragment
import org.w3c.dom.Comment
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Field

interface CommentService {
    // @POST 어노테이션을 사용하여 업로드할 엔드포인트 정의
    // 필요한 경우 @Body 어노테이션을 사용하여 요청 본문을 정의할 수 있습니다.
    @POST("comments/upload/")
    fun uploadComment(
        @Header("Authorization") authToken: String,
        @Query("post_id") postId: Int,
        @Field("content") content: String
    ): Call<Comment>
}
