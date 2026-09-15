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

class MemoryFragment : Fragment() {

    private data class Card(val key: String, val face: String, val speak: String)

    private lateinit var buttons: List<Button>
    private lateinit var tvInfo: TextView
    private lateinit var tvMsg: TextView
    private var cards: List<Card> = emptyList()
    private var firstIndex: Int? = null
    private var matched = 0
    private var lock = false

    override fun onCreateView(inf: LayoutInflater, c: ViewGroup?, b: Bundle?): View =
        inf.inflate(R.layout.fragment_memory, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        tvInfo = v.findViewById(R.id.tvInfo)
        tvMsg = v.findViewById(R.id.tvMsg)
        buttons = listOf(
            v.findViewById(R.id.c0), v.findViewById(R.id.c1), v.findViewById(R.id.c2),
            v.findViewById(R.id.c3), v.findViewById(R.id.c4), v.findViewById(R.id.c5),
            v.findViewById(R.id.c6), v.findViewById(R.id.c7), v.findViewById(R.id.c8),
            v.findViewById(R.id.c9), v.findViewById(R.id.c10), v.findViewById(R.id.c11)
        )
        v.findViewById<View>(R.id.btnBack).setOnClickListener { (activity as MainActivity).goHome() }
        v.findViewById<View>(R.id.btnAgain).setOnClickListener { newGame() }
        buttons.forEachIndexed { i, btn -> btn.setOnClickListener { flip(i) } }
        newGame()
    }

    private fun newGame() {
        // 6 pares letra <-> emoji, ideal 3-6 años
        val picked = Content.letters.shuffled().take(6)
        val deck = mutableListOf<Card>()
        for (p in picked) {
            deck.add(Card(p.letter, p.letter, "Letra ${p.letter}"))
            deck.add(Card(p.letter, p.emoji, p.word))
        }
        cards = deck.shuffled()
        firstIndex = null
        matched = 0
        lock = false
        buttons.forEach {
            it.text = "❓"
            it.isEnabled = true
            it.alpha = 1f
        }
        tvInfo.text = "Encuentra los 6 pares 🧠"
        tvMsg.text = "Toca una carta: A ↔ 🦙"
        Sound.speak("Busca la letra y su dibujo. Toca una carta")
    }

    private fun flip(i: Int) {
        if (lock || !buttons[i].isEnabled || buttons[i].text != "❓") return
        buttons[i].text = cards[i].face
        Sound.speak(cards[i].speak)
        val first = firstIndex
        if (first == null) {
            firstIndex = i
        } else if (first == i) {
            return
        } else {
            if (cards[first].key == cards[i].key) {
                // ¡par!
                lock = true
                buttons[first].postDelayed({
                    buttons[first].isEnabled = false
                    buttons[i].isEnabled = false
                    buttons[first].alpha = 0.45f
                    buttons[i].alpha = 0.45f
                    matched++
                    tvInfo.text = "Pares: $matched de 6 ⭐"
                    val msg = Content.praise.random()
                    tvMsg.text = "$msg ¡${cards[i].key} con ${cards[i].face}! 🎉"
                    Sound.speak(msg)
                    lifecycleScope.launch {
                        Stars.add(requireContext(), "memory")
                        (activity as MainActivity).refreshStars()
                    }
                    firstIndex = null
                    lock = false
                    if (matched == 6) {
                        tvMsg.text = "🏆 ¡Ganaste! Encontraste todos los pares"
                        Sound.speak("Felicidades. Encontraste todos los pares")
                    }
                }, 600)
            } else {
                lock = true
                val a = first
                buttons[a].postDelayed({
                    buttons[a].text = "❓"
                    buttons[i].text = "❓"
                    firstIndex = null
                    lock = false
                    tvMsg.text = "Casi. ¡Sigue buscando! 👀"
                }, 900)
            }
        }
    }
}
