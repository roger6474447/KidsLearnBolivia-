package com.kidslearn.bolivia

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kidslearn.bolivia.data.Stars
import com.kidslearn.bolivia.ui.AbcFragment
import com.kidslearn.bolivia.ui.ChallengeFragment
import com.kidslearn.bolivia.ui.MemoryFragment
import com.kidslearn.bolivia.ui.MenuFragment
import com.kidslearn.bolivia.ui.NumbersFragment
import com.kidslearn.bolivia.ui.TraceFragment
import com.kidslearn.bolivia.ui.WordsFragment
import com.kidslearn.bolivia.util.Sound
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var tvStars: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Sound.init(this)
        tvStars = findViewById(R.id.tvStars)

        if (savedInstanceState == null) {
            open(MenuFragment())
        }
        refreshStars()
    }

    override fun onResume() {
        super.onResume()
        refreshStars()
    }

    override fun onDestroy() {
        Sound.shutdown()
        super.onDestroy()
    }

    fun open(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun goHome() {
        supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MenuFragment())
            .commit()
    }

    fun refreshStars() {
        lifecycleScope.launch {
            val total = Stars.total(this@MainActivity)
            tvStars.text = getString(R.string.stars, total)
        }
    }

    fun navigateTo(tag: String) {
        val f = when (tag) {
            "abc" -> AbcFragment()
            "numbers" -> NumbersFragment()
            "words" -> WordsFragment()
            "challenge" -> ChallengeFragment()
            "memory" -> MemoryFragment()
            "trace" -> TraceFragment()
            else -> MenuFragment()
        }
        open(f)
    }
}
