package uk.co.satoama.satsumaimo

object InflLex {
    val lexemes: List<Lexeme> = listOf(
        InflLexeme("たべる:eat", "たべる", "動", listOf("たべる", "食べる"), Inflection.ichidan),
        InflLexeme("みる:see", "みる", "動", listOf("みる", "見る", "観る", "診る"), Inflection.ichidan),
        InflLexeme("かく:write", "かく", "動", listOf("かく", "書く"), Inflection.godan),
        InflLexeme("むかう:face", "むかう", "動", listOf("むかう", "向かう", "向う"), Inflection.godan),
        InflLexeme("かわる:change", "かわる", "動", listOf("かわる", "変わる", "変る"), Inflection.godan),
        InflLexeme("おこなう:perform", "おこなう", "動", listOf("おこなう", "行う", "行なう"), Inflection.godan),
        InflLexeme("すくない:few", "すくない", "形容", listOf("すくない", "少ない", "少い"), Inflection.adj),
        InflLexeme("みじかい:short", "みじかい", "形容", listOf("みじかい", "短い", "短かい"), Inflection.adj),
        InflLexeme("たい:want", "たい", "助動", listOf("たい"), Inflection.aux),
        InflLexeme("みたい:like", "みたい", "助動", listOf("みたい"), Inflection.aux),
        InflLexeme("た:past", "た", "助動", listOf("た"), Inflection.aux),
        InflLexeme("ぬ:negative", "ぬ", "助動", listOf("ぬ", "ん"), Inflection.aux)
    )
}
