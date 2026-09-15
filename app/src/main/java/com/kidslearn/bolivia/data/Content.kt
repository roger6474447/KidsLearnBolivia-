package com.kidslearn.bolivia.data

data class LetterItem(val letter: String, val word: String, val emoji: String)
data class NumberItem(val value: Int, val word: String, val emoji: String)
data class WordItem(val word: String, val hint: String, val emoji: String, val options: List<String>)
data class QuizItem(val question: String, val emoji: String, val correct: String, val options: List<String>)

object Content {

    // Vocabulario cercano a Bolivia + fácil 3-6 años
    val letters = listOf(
        LetterItem("A", "Alpaca", "🦙"),
        LetterItem("B", "Burro", "🫏"),
        LetterItem("C", "Cóndor", "🦅"),
        LetterItem("D", "Delfín", "🐬"),
        LetterItem("E", "Elefante", "🐘"),
        LetterItem("F", "Foca", "🦭"),
        LetterItem("G", "Gato", "🐱"),
        LetterItem("H", "Hipopótamo", "🦛"),
        LetterItem("I", "Iguana", "🦎"),
        LetterItem("J", "Jirafa", "🦒"),
        LetterItem("K", "Koala", "🐨"),
        LetterItem("L", "León", "🦁"),
        LetterItem("M", "Mono", "🐵"),
        LetterItem("N", "Nube", "☁️"),
        LetterItem("Ñ", "Ñandú", "🐦"),
        LetterItem("O", "Oso", "🐻"),
        LetterItem("P", "Pato", "🦆"),
        LetterItem("Q", "Quena", "🎶"),
        LetterItem("R", "Ratón", "🐭"),
        LetterItem("S", "Sapo", "🐸"),
        LetterItem("T", "Tigre", "🐯"),
        LetterItem("U", "Uva", "🍇"),
        LetterItem("V", "Vaca", "🐮"),
        LetterItem("W", "Wiphala", "🏳️‍🌈"),
        LetterItem("X", "Xilófono", "🎵"),
        LetterItem("Y", "Yuca", "🍠"),
        LetterItem("Z", "Zorro", "🦊")
    )

    val numbers = listOf(
        NumberItem(1, "UNO", "🍎"),
        NumberItem(2, "DOS", "🐶"),
        NumberItem(3, "TRES", "🐱"),
        NumberItem(4, "CUATRO", "🦆"),
        NumberItem(5, "CINCO", "🐸"),
        NumberItem(6, "SEIS", "🐵"),
        NumberItem(7, "SIETE", "🐝"),
        NumberItem(8, "OCHO", "🐙"),
        NumberItem(9, "NUEVE", "🐢"),
        NumberItem(10, "DIEZ", "🦋")
    )

    val words = listOf(
        WordItem("GATO", "G _ T O", "🐱", listOf("GATO", "PATO", "SAPO")),
        WordItem("PATO", "P _ T O", "🦆", listOf("PATO", "GATO", "RATA")),
        WordItem("OSO", "O _ O", "🐻", listOf("OSO", "AVE", "PEZ")),
        WordItem("UVA", "U _ A", "🍇", listOf("UVA", "UVE", "OLA")),
        WordItem("VACA", "V _ C A", "🐮", listOf("VACA", "CASA", "BOCA")),
        WordItem("SAPO", "S _ P O", "🐸", listOf("SAPO", "SOPA", "SAPITO")),
        WordItem("LUNA", "L _ N A", "🌙", listOf("LUNA", "LANA", "CUNA")),
        WordItem("SOL", "S _ L", "☀️", listOf("SOL", "SAL", "SELVA")),
        WordItem("MAMA", "M _ M A", "👩", listOf("MAMA", "PAPA", "CAMA")),
        WordItem("AGUA", "A _ U A", "💧", listOf("AGUA", "ALAS", "AULA"))
    )

    val quiz = listOf(
        QuizItem("¿Con qué letra empieza ALPACA?", "🦙", "A", listOf("A", "E", "O")),
        QuizItem("¿Cuántas 🍎 hay aquí? 🍎🍎", "🍎", "2", listOf("1", "2", "3")),
        QuizItem("¿Qué animal hace Miau?", "🐱", "GATO", listOf("GATO", "PATO", "VACA")),
        QuizItem("¿Con qué letra empieza OSO?", "🐻", "O", listOf("O", "A", "E")),
        QuizItem("¿Cuántos 🐶 hay? 🐶🐶🐶", "🐶", "3", listOf("2", "3", "5")),
        QuizItem("¿Qué es ☀️?", "☀️", "SOL", listOf("SOL", "LUNA", "NUBE")),
        QuizItem("¿Con qué letra empieza SAPO?", "🐸", "S", listOf("S", "P", "T")),
        QuizItem("¿Qué palabra es? 🐮", "🐮", "VACA", listOf("VACA", "CASA", "LUNA"))
    )

    val praise = listOf("¡Muy bien! 🎉", "¡Genial! ⭐", "¡Bravo! 👏", "¡Increíble! 🌈", "¡Súper! 🚀")
    val encourage = listOf("Casi, casi. ¡Tú puedes! 💪", "Inténtalo otra vez 🍀", "Mira bien la imagen 👀")
}
