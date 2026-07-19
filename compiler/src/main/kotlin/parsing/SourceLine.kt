package io.cuttlefish.parsing


data class SourceLine(
    val tokens: List<Token>
) {
    val lineNumber: Int = tokens.firstOrNull()?.line ?: 0
}