package ua.sviatkuzbyt.yourmath.data.repositories

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.sviatkuzbyt.yourmath.domain.repositories.FileRepository
import java.io.IOException
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): FileRepository {
    override fun write(fileUri: String, text: String) {
        context.contentResolver.openOutputStream(Uri.parse(fileUri))?.use { output ->
            output.write(text.toByteArray())
        }
    }

    override fun read(fileUri: String): String {
        return context.contentResolver.openInputStream(Uri.parse(fileUri))?.use { input ->
            input.bufferedReader().readText()
        } ?: throw IOException("Unable to open input stream")
    }
}