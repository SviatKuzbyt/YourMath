package ua.sviatkuzbyt.yourmath.data.repositories

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.sviatkuzbyt.yourmath.domain.repositories.FileRepository
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): FileRepository {
    override fun write(fileUri: String, text: String) {
        context.contentResolver.openOutputStream(Uri.parse(fileUri))?.use { output ->
            output.write(text.toByteArray())
        }
    }
}