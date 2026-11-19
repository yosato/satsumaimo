package uk.co.satoama.satsumaimo

object Inflection {
    const val ichidan = "一段"
    const val godan = "五段"
    const val henkaku = "変格"
    const val adj = "形容"
    const val keiyodoshi = "形動"
    const val aux = "助動"
    val inflecting: Set<String> = setOf(ichidan, godan, henkaku, adj, keiyodoshi, aux)
    fun is_inflecting(category: String): Boolean = category in inflecting

    const val mizen = "未然"
    const val renyo = "連用"
    const val renyoOnbin = "連用音便"
    const val shushi = "終止"
    const val rentai = "連体"
    const val katei = "仮定"
    const val meirei = "命令"
}
