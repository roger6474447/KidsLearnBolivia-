package com.kidslearn.bolivia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kidslearn.bolivia.MainActivity
import com.kidslearn.bolivia.R
import com.kidslearn.bolivia.data.Content
import com.kidslearn.bolivia.data.Stars
import com.kidslearn.bolivia.util.Sound
import kotlinx.coroutines.launch

class NumbersFragment : Fragment() {
    private var index = 0
    private lateinit var tvNumber: TextView
    private lateinit var tvWord: TextView
    private lateinit var tvCount: TextView
    private lateinit var tvQuiz: TextView
    private lateinit var opts: List<Button>

    override fun onCreateView(inf: LayoutInflater, c: ViewGroup?, b: Bundle?): View =
        inf.inflate(R.layout.fragment_numbers, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        tvNumber = v.findViewById(R.id.tvNumber)
        tvWord = v.findViewById(R.id.tvNumberWord)
        tvCount = v.findViewById(R.id.tvCount)
        tvQuiz = v.findViewById(R.id.tvQuiz)
        opts = listOf(v.findViewById(R.id.opt1), v.findViewById(R.id.opt2), v.findViewById(R.id.opt3))

        v.findViewById<View>(R.id.btnBack).setOnClickListener { (activity as MainActivity).goHome() }
        v.findViewById<View>(R.id.btnPrev).setOnClickListener { index = (index - 1 + Content.numbers.size) % Content.numbers.size; show() }
        v.findViewById<View>(R.id.btnNext).setOnClickListener { index = (index + 1) % Content.numbers.size; show() }
        v.findViewById<View>(R.id.btnListen).setOnClickListener { speakCurrent() }
        v.findViewById<View>(R.id.btnCountGame).setOnClickListener { startQuiz() }
        opts.forEach { it.visibility = View.GONE }
        show()
    }

    private fun show() {
        val item = Content.numbers[index]
        tvNumber.text = item.value.toString()
        tvWord.text = item.word
        tvCount.text = item.emoji.repeat(item.value.coerceAtMost(8))
        tvQuiz.text = "Cuenta los ${item.emoji} conmigo: 1... ${item.value}!"
        opts.forEach { it.visibility = View.GONE }
        Sound.speak("${item.value}. ${item.word}")
    }

    private fun speakCurrent() {
        val item = Content.numbers[index]
        Sound.speak("Número ${item.value}. ${item.word}")
    }

    private fun startQuiz() {
        val item = Content.numbers[index]
        val showCount = (1..5).random()
        val emoji = item.emoji
        tvCount.text = emoji.repeat(showCount)
        tvQuiz.text = "¿Cuántos $emoji hay? 👀"
        Sound.speak("¿Cuántos dibujitos hay? Cuenta y toca el número")
        val pool = ((showCount - 2)..(showCount + 2)).filter { it in 1..10 && it != showCount }.shuffled().take(2) + showCount
        val shuffled = pool.shuffled()
        opts.forEachIndexed { i, btn ->
            btn.visibility = View.VISIBLE
            btn.text = shuffled[i].toString()
            btn.setOnClickListener {
                if (btn.text.toString().toInt() == showCount) {
                    val msg = Content.praise.random()
                    tvQuiz.text = "$msg ¡Hay $showCount! 🎉"
                    Sound.speak(msg)
                    lifecycleScope.launch {
                        Stars.add(requireContext(), "numbers")
                        (activity as MainActivity).refreshStars()
                    }
                    opts.forEach { it.visibility = View.GONE }
                } else {
                    tvQuiz.text = Content.encourage.random()
                    Sound.speak("Cuenta otra vez despacio")
                }
            }
        }
    }
}
