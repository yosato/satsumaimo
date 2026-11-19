package uk.co.satoama.satsumaimo

data class InflectedForm(val form: String, val surface: String)

sealed class Lexeme {
    abstract val id: String
    abstract val lemma: String
    abstract val pos: String
    abstract val orthographies: List<String>
    abstract val inflections: List<InflectedForm>
}

data class NonInflLexeme(
    override val id: String,
    override val lemma: String,
    override val pos: String,
    override val orthographies: List<String>
) : Lexeme() {
    override val inflections: List<InflectedForm> = emptyList()
}

data class InflLexeme(
    override val id: String,
    override val lemma: String,
    override val pos: String,
    override val orthographies: List<String>,
    val inflection: String
) : Lexeme() {
    override val inflections: List<InflectedForm> =
        orthographies.flatMap { Inflector.forms(it, inflection) }.distinct()
}
