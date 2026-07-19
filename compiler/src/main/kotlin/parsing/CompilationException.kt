package io.cuttlefish.parsing


class CompilationException(
    val fileName: String, val sourceLine: SourceLine, errorMessage: String
) : Exception("errorMessage")

class SyntaxException(message: String) : Exception(message)