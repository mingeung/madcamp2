import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.madcampweek2.Fragment1_Home
import com.example.madcampweek2.Fragment2_Community
import com.example.madcampweek2.Fragment3_Profile

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3 // Number of tabs

    override fun createFragment(position: Int): Fragment {
        // Return a different fragment based on the position
        return when (position) {
            0 -> Fragment1_Home()
            1 -> Fragment2_Community()
            else -> Fragment3_Profile()
        }
    }
}