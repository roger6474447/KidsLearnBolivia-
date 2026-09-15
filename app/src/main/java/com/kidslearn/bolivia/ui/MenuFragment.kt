package com.kidslearn.bolivia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kidslearn.bolivia.MainActivity
import com.kidslearn.bolivia.R
import com.kidslearn.bolivia.util.Sound

class MenuFragment : Fragment() {
    override fun onCreateView(inf: LayoutInflater, c: ViewGroup?, b: Bundle?): View =
        inf.inflate(R.layout.fragment_menu, c, false)

    override fun onViewCreated(v: View, b: Bundle?) {
        fun go(tag: String, phrase: String) {
            Sound.speak(phrase)
            (activity as MainActivity).navigateTo(tag)
        }
        v.findViewById<View>(R.id.cardAbc).setOnClickListener { go("abc", "Aprendamos el abecedario") }
        v.findViewById<View>(R.id.cardNumbers).setOnClickListener { go("numbers", "Contemos números") }
        v.findViewById<View>(R.id.cardWords).setOnClickListener { go("words", "Adivina la palabra") }
        v.findViewById<View>(R.id.cardChallenge).setOnClickListener { go("challenge", "Hora del reto final") }
        v.findViewById<View>(R.id.cardMemory).setOnClickListener { go("memory", "Juguemos a memoria") }
        v.findViewById<View>(R.id.cardTrace).setOnClickListener { go("trace", "Dibuja la letra con tu dedo") }
    }
}
