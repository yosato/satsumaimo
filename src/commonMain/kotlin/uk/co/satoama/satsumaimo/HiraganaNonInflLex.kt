package uk.co.satoama.satsumaimo

object HiraganaNonInflLex {
    private const val hiragana = "あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほまみむめもやゆよらりるれろわん" +
        "がぎぐげござじずぜぞだぢづでどばびぶべぼぱぴぷぺぽ"

    val unknown: List<Lexeme> = hiragana.map { NonInflLexeme("$it:unk", "$it", "unk", listOf("$it")) }

    val known: List<Lexeme> = listOf(
        NonInflLexeme("を:object", "を", "格助", listOf("を")),
        NonInflLexeme("が:subject", "が", "格助", listOf("が")),
        NonInflLexeme("に:location", "に", "格助", listOf("に")),
        NonInflLexeme("の:possessive", "の", "格助", listOf("の")),
        NonInflLexeme("へ:direction", "へ", "格助", listOf("へ")),
        NonInflLexeme("と:with", "と", "格助", listOf("と")),
        NonInflLexeme("で:means", "で", "格助", listOf("で")),
        NonInflLexeme("は:topic", "は", "係助", listOf("は")),
        NonInflLexeme("も:also", "も", "係助", listOf("も")),
        NonInflLexeme("や:and", "や", "副助", listOf("や")),
        NonInflLexeme("か:question", "か", "副助", listOf("か")),
        NonInflLexeme("て:and_then", "て", "接助", listOf("て")),
        NonInflLexeme("ば:if", "ば", "接助", listOf("ば")),
        NonInflLexeme("し:and_also", "し", "接助", listOf("し")),
        NonInflLexeme("ね:tag", "ね", "終助", listOf("ね")),
        NonInflLexeme("よ:assert", "よ", "終助", listOf("よ")),
        NonInflLexeme("な:tag", "な", "終助", listOf("な")),
        NonInflLexeme("わ:tag", "わ", "終助", listOf("わ")),
        NonInflLexeme("ぞ:assert", "ぞ", "終助", listOf("ぞ")),
        NonInflLexeme("ぜ:assert", "ぜ", "終助", listOf("ぜ")),
        NonInflLexeme("さ:tag", "さ", "終助", listOf("さ")),
        NonInflLexeme("べ:suggest", "べ", "終助", listOf("べ")),
        NonInflLexeme("さ:nominaliser", "さ", "接尾", listOf("さ")),
        NonInflLexeme("ら:plural", "ら", "接尾", listOf("ら"))
    )

    val lexemes: List<Lexeme> = known + unknown
}
