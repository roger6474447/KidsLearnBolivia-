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

class WordsFragment : Fragment() {
    private var index = 0
    private lateinit var tvEmoji: TextView
    private lateinit var tvHint: TextView
    private lateinit var tvMsg: TextView
    private lateinit var opts: List<Button>

    override fun onCreateView(inf: LayoutInflater, c: ViewGroup?, b: Bundle?): View =
        inf.inflate(R.layout.fragment_words, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        tvEmoji = v.findViewById(R.id.tvEmoji)
        tvHint = v.findViewById(R.id.tvHint)
        tvMsg = v.findViewById(R.id.tvMessage)
        opts = listOf(v.findViewById(R.id.opt1), v.findViewById(R.id.opt2), v.findViewById(R.id.opt3))
        v.findViewById<View>(R.id.btnBack).setOnClickListener { (activity as MainActivity).goHome() }
        v.findViewById<View>(R.id.btnListen).setOnClickListener {
            Sound.speak("¿Qué palabra es? ${Content.words[index].word}")
        }
        show()
    }

    private fun show() {
        val item = Content.words[index % Content.words.size]
        tvEmoji.text = item.emoji
        tvHint.text = item.hint
        tvMsg.text = "¿Qué palabra es? Toca la correcta 👇"
        Sound.speak("Mira el dibujo. ¿Qué palabra es?")
        val shuffled = item.options.shuffled()
        opts.forEachIndexed { i, btn ->
            btn.text = shuffled[i]
            btn.setOnClickListener {
                if (btn.text == item.word) {
                    val msg = Content.praise.random()
                    tvMsg.text = "$msg ¡Es ${item.word}! ${item.emoji}"
                    Sound.speak("$msg. ${item.word}")
                    lifecycleScope.launch {
                        Stars.add(requireContext(), "words")
                        (activity as MainActivity).refreshStars()
                    }
                    // siguiente tras acierto
                    tvMsg.postDelayed({
                        index++
                        show()
                    }, 1500)
                } else {
                    tvMsg.text = Content.encourage.random()
                    Sound.speak("Mira el dibujo otra vez")
                }
            }
        }
    }
}
