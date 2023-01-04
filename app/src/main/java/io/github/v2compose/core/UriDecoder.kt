package io.github.v2compose.core

import android.net.Uri
import javax.inject.Inject

class UriDecoder @Inject constructor(): StringDecoder {
    override fun decodeString(encodedString: String): String = Uri.decode(encodedString)
}