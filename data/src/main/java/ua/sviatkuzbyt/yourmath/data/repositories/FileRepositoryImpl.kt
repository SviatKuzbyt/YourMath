package ua.sviatkuzbyt.yourmath.data.repositories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.sviatkuzbyt.yourmath.domain.repositories.FileRepository
import java.io.IOException
import javax.inject.Inject
import androidx.core.net.toUri

class FileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): FileRepository {
    override fun write(fileUri: String, text: String) {
        context.contentResolver.openOutputStream(fileUri.toUri())?.use { output ->
            output.write(text.toByteArray())
        }
    }

    override fun read(fileUri: String): String {
        return context.contentResolver.openInputStream(fileUri.toUri())?.use { input ->
            input.bufferedReader().readText()
        } ?: throw IOException("Unable to open input stream")
    }
}