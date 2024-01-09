// PostService.kt
import com.example.madcampweek2.Post
import retrofit2.Call
import retrofit2.http.GET

interface PostService {
    @GET("/posts/")  // Django 서버에서 데이터를 제공하는 API의 경로를 입력하세요.
    fun getPosts(): Call<List<Post>>
}
