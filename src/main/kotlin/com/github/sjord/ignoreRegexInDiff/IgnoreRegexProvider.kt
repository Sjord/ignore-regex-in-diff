package com.github.sjord.ignoreRegexInDiff;

import com.intellij.diff.contents.DiffContent
import com.intellij.diff.lang.DiffIgnoredRangeProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange


class IgnoreRegexProvider : DiffIgnoredRangeProvider {
    override fun getDescription(): String {
        return "Ignores regex in diff view"
    }

    override fun accepts(project: Project?, content: DiffContent): Boolean {
        if (content.contentType?.isBinary == true) {
            return false
        }
        return true
    }

    override fun getIgnoredRanges(project: Project?, text: CharSequence, content: DiffContent): MutableList<TextRange> {
        val regexList = listOf(
            Regex("\\s+"), // Whitespace
            Regex("\\s*#.*\\r?\\n"), // Python comments
            Regex("from [A-Za-z_.]+ import [A-Za-z_.]+\\r?\\n"), // Python imports
        )
        val result = ArrayList<TextRange>()
        for (regex in regexList) {
            val matches = regex.findAll(text);
            for (match in matches) {
                result.add(TextRange.create(match.range.start, match.range.endInclusive + 1));
            }
        }
        return result;
    }
}
