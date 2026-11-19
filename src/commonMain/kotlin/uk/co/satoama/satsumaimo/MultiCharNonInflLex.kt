package uk.co.satoama.satsumaimo

object MultiCharNonInflLex {
    val lexemes: List<Lexeme> = listOf(
        NonInflLexeme("いも:potato", "いも", "普名", listOf("いも", "イモ", "芋")),
        NonInflLexeme("さつ:bill", "さつ", "普名", listOf("さつ", "札")),
        NonInflLexeme("さつ:police", "さつ", "普名", listOf("さつ", "サツ", "察")),
        NonInflLexeme("つま:wife", "つま", "普名", listOf("つま", "妻")),
        NonInflLexeme("つま:garnish", "つま", "普名", listOf("つま", "ツマ", "妻")),
        NonInflLexeme("さつま:satsuma", "さつま", "普名", listOf("さつま", "サツマ", "薩摩")),
        NonInflLexeme("らめ:lamé", "らめ", "普名", listOf("らめ", "ラメ")),
        NonInflLexeme("べら:wrasse", "べら", "普名", listOf("べら", "ベラ")),
        NonInflLexeme("さーばー:server", "さーばー", "普名", listOf("さーばー", "さーば", "サーバー", "サーバ")),
        NonInflLexeme("くーらー:cooler", "くーらー", "普名", listOf("くーらー", "クーラー"))
    )
}
