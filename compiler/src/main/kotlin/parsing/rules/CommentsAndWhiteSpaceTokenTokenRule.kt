package io.cuttlefish.parsing.rules

import io.cuttlefish.parsing.*

class CommentsAndWhiteSpaceTokenTokenRule : TokenRule {

    private val regularExpression = Regex(
        """
        ^(\s+|
        //.*|
        /\*[\s\S]*?\*/)
    """.trimIndent()
    )

    override fun match(
        source: String, index: Int, line: Int, column: Int
    ): TokenRule.MatchResult? {
        val match = regularExpression.find(source.substring(index)) ?: return null
        if (match.range.first != 0) return null // start of substring must match

        return TokenRule.MatchResult(SkipToken, match.value.length)
    }
}