package com.kidslearn.bolivia.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.kidslearn.bolivia.MainActivity
import com.kidslearn.bolivia.R
import com.kidslearn.bolivia.data.Content
import com.kidslearn.bolivia.data.Stars
import com.kidslearn.bolivia.util.DrawingView
import com.kidslearn.bolivia.util.Sound
import kotlinx.coroutines.launch

class TraceFragment : Fragment() {
    private var index = 0
    private lateinit var tvBig: TextView
    private lateinit var tvWord: TextView
    private lateinit var tvGuide: TextView
    private lateinit var tvMsg: TextView
    private lateinit var draw: DrawingView

    // Primeras 10 letras para 3-6 años, sin saturar
    private val items get() = Content.letters.take(10)

    override fun onCreateView(inf: LayoutInflater, c: ViewGroup?, b: Bundle?): View =
        inf.inflate(R.layout.fragment_trace, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        tvBig = v.findViewById(R.id.tvBigLetter)
        tvWord = v.findViewById(R.id.tvWord)
        tvGuide = v.findViewById(R.id.tvGuide)
        tvMsg = v.findViewById(R.id.tvMsg)
        draw = v.findViewById(R.id.draw)

        v.findViewById<View>(R.id.btnBack).setOnClickListener { (activity as MainActivity).goHome() }
        v.findViewById<View>(R.id.btnPrev).setOnClickListener { index = (index - 1 + items.size) % items.size; show() }
        v.findViewById<View>(R.id.btnNext).setOnClickListener { index = (index + 1) % items.size; show() }
        v.findViewById<View>(R.id.btnListen).setOnClickListener { speak() }
        v.findViewById<View>(R.id.btnClear).setOnClickListener { draw.clear(); tvMsg.text = "Pizarra limpia. ¡Dibuja otra vez! 🧽" }
        v.findViewById<View>(R.id.btnRed).setOnClickListener { draw.setColor(Color.parseColor("#D81B60")) }
        v.findViewById<View>(R.id.btnBlue).setOnClickListener { draw.setColor(Color.parseColor("#0288D1")) }
        v.findViewById<View>(R.id.btnGreen).setOnClickListener { draw.setColor(Color.parseColor("#388E3C")) }
        v.findViewById<View>(R.id.btnDone).setOnClickListener {
            if (draw.drawCount < 3) {
                tvMsg.text = "Dibuja un poquito más con tu dedo 👆"
                Sound.speak("Dibuja un poquito más")
            } else {
                val msg = Content.praise.random()
                tvMsg.text = "$msg ¡Trazaste la ${items[index].letter}! ⭐"
                Sound.speak("$msg. Trazaste la letra ${items[index].letter}")
                lifecycleScope.launch {
                    Stars.add(requireContext(), "trace")
                    (activity as MainActivity).refreshStars()
                }
                draw.postDelayed({
                    index = (index + 1) % items.size
                    show()
                }, 1500)
            }
        }
        show()
    }

    private fun show() {
        val item = items[index]
        tvBig.text = item.letter
        tvGuide.text = item.letter
        tvWord.text = "${item.letter} de ${item.word} ${item.emoji}"
        tvMsg.text = "Dibuja la ${item.letter} sobre la sombra 👆"
        draw.clear()
        Sound.speak("Dibuja la letra ${item.letter}. ${item.word}")
    }

    private fun speak() {
        val item = items[index]
        Sound.speak("${item.letter}. ${item.letter} de ${item.word}")
    }
}
