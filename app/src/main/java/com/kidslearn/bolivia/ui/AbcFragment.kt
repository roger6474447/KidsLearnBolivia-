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

class AbcFragment : Fragment() {
    private var index = 0
    private lateinit var tvLetter: TextView
    private lateinit var tvWord: TextView
    private lateinit var tvEmoji: TextView
    private lateinit var tvQuiz: TextView
    private lateinit var opts: List<Button>

    override fun onCreateView(inf: LayoutInflater, c: ViewGroup?, b: Bundle?): View =
        inf.inflate(R.layout.fragment_abc, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        tvLetter = v.findViewById(R.id.tvLetter)
        tvWord = v.findViewById(R.id.tvLetterWord)
        tvEmoji = v.findViewById(R.id.tvEmoji)
        tvQuiz = v.findViewById(R.id.tvQuiz)
        opts = listOf(v.findViewById(R.id.opt1), v.findViewById(R.id.opt2), v.findViewById(R.id.opt3))

        v.findViewById<View>(R.id.btnBack).setOnClickListener { (activity as MainActivity).goHome() }
        v.findViewById<View>(R.id.btnPrev).setOnClickListener { index = (index - 1 + Content.letters.size) % Content.letters.size; show(false) }
        v.findViewById<View>(R.id.btnNext).setOnClickListener { index = (index + 1) % Content.letters.size; show(false) }
        v.findViewById<View>(R.id.btnListen).setOnClickListener { speakCurrent() }
        v.findViewById<View>(R.id.btnQuiz).setOnClickListener { startQuiz() }
        opts.forEach { it.visibility = View.GONE }
        show(true)
    }

    private fun show(first: Boolean) {
        val item = Content.letters[index]
        tvLetter.text = item.letter
        tvWord.text = "${item.letter} de ${item.word}"
        tvEmoji.text = item.emoji
        tvQuiz.text = ""
        opts.forEach { it.visibility = View.GONE }
        if (first) speakCurrent() else Sound.speak(item.letter + ". " + item.word)
    }

    private fun speakCurrent() {
        val item = Content.letters[index]
        Sound.speak("${item.letter}. ${item.letter} de ${item.word}")
    }

    private fun startQuiz() {
        val item = Content.letters[index]
        tvQuiz.text = "Escucha y toca la letra: ${item.letter} 👀"
        Sound.speak("¿Dónde está la letra ${item.letter}? Tócala")
        val pool = Content.letters.map { it.letter }.filter { it != item.letter }.shuffled().take(2) + item.letter
        val shuffled = pool.shuffled()
        opts.forEachIndexed { i, btn ->
            btn.visibility = View.VISIBLE
            btn.text = shuffled[i]
            btn.setOnClickListener {
                if (btn.text == item.letter) {
                    val msg = Content.praise.random()
                    tvQuiz.text = "$msg ¡Era ${item.letter}! 🎉"
                    Sound.speak(msg)
                    lifecycleScope.launch {
                        Stars.add(requireContext(), "abc")
                        (activity as MainActivity).refreshStars()
                    }
                    opts.forEach { it.visibility = View.GONE }
                } else {
                    tvQuiz.text = Content.encourage.random()
                    Sound.speak("Casi. Busca la ${item.letter}")
                }
            }
        }
    }
}
