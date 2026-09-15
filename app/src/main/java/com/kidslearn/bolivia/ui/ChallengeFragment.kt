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

class ChallengeFragment : Fragment() {
    private var qIndex = 0
    private var score = 0
    private lateinit var tvQ: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvFeed: TextView
    private lateinit var opts: List<Button>
    private var questions = listOf<com.kidslearn.bolivia.data.QuizItem>()

    override fun onCreateView(inf: LayoutInflater, c: ViewGroup?, b: Bundle?): View =
        inf.inflate(R.layout.fragment_challenge, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        tvQ = v.findViewById(R.id.tvQuestion)
        tvScore = v.findViewById(R.id.tvScore)
        tvFeed = v.findViewById(R.id.tvFeedback)
        opts = listOf(v.findViewById(R.id.opt1), v.findViewById(R.id.opt2), v.findViewById(R.id.opt3))
        v.findViewById<View>(R.id.btnBack).setOnClickListener { (activity as MainActivity).goHome() }
        v.findViewById<View>(R.id.btnListen).setOnClickListener { Sound.speak(questions[qIndex].question) }

        questions = Content.quiz.shuffled().take(5)
        qIndex = 0
        score = 0
        show()
    }

    private fun show() {
        if (qIndex >= questions.size) {
            tvQ.text = if (score >= 4) "🏆 ¡Campeón! Sacaste $score de ${questions.size}" else "🌟 ¡Bien! Sacaste $score de ${questions.size}. ¡Sigue jugando!"
            Sound.speak(if (score >= 4) "Felicidades campeón. Ganaste muchas estrellas" else "Bien hecho. Sigue jugando para ganar más estrellas")
            tvScore.text = "Fin del reto ⭐ $score"
            tvFeed.text = "Toca Menú para seguir aprendiendo 🏠"
            opts.forEach { it.visibility = View.GONE }
            return
        }
        val q = questions[qIndex]
        tvScore.text = "Pregunta ${qIndex + 1} de ${questions.size} ⭐ $score"
        tvQ.text = "${q.emoji} ${q.question}"
        tvFeed.text = ""
        Sound.speak(q.question)
        val shuffled = q.options.shuffled()
        opts.forEachIndexed { i, btn ->
            btn.visibility = View.VISIBLE
            btn.text = shuffled[i]
            btn.setOnClickListener {
                if (btn.text == q.correct) {
                    score++
                    val msg = Content.praise.random()
                    tvFeed.text = "$msg ✅"
                    Sound.speak(msg)
                    lifecycleScope.launch {
                        Stars.add(requireContext(), "challenge")
                        (activity as MainActivity).refreshStars()
                    }
                } else {
                    tvFeed.text = "Era: ${q.correct} ${Content.encourage.random()}"
                    Sound.speak("La respuesta era ${q.correct}")
                }
                tvFeed.postDelayed({
                    qIndex++
                    show()
                }, 1400)
            }
        }
    }
}
