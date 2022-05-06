package com.github.sjord.ignoreFooInDiff;

import com.intellij.diff.contents.DiffContent
import com.intellij.diff.lang.DiffIgnoredRangeProvider
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange


class IgnoreFooProvider : DiffIgnoredRangeProvider {
    override fun getDescription(): String {
        return "Ignores 'foo' in diff view"
    }

    override fun accepts(project: Project?, content: DiffContent): Boolean {
        if (content.contentType?.isBinary == true) {
            return false
        }
        return true
    }

    override fun getIgnoredRanges(project: Project?, text: CharSequence, content: DiffContent): MutableList<TextRange> {
        val result = ArrayList<TextRange>()
        var start = 0
        while (true) {
            val index = text.indexOf("foo", start)
            if (index == -1) {
                return result
            }
            var end = index + 3
            while (text[end].isWhitespace()) {
                end += 1
            }
            result.add(TextRange.create(index, end))
            start = end
        }
    }
}