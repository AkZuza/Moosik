package com.akzuza.moosik.screens.home

import android.content.ContentResolver
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import com.akzuza.moosik.entities.Music
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private var _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state;

    fun openMusicPicker() {

    }

    private fun mediaStoreGetMusic(contentResolver: ContentResolver) {
        val projections = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DURATION
        )

        var musics = mutableListOf<Music>()
        val collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val query = contentResolver.query(
            collection,
            projections,
            null, null, null
        )

        query?.use { cursor ->
            val idid = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val idDisplayName = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val idAlbumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val idDuration = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idid)
                val displayName = cursor.getString(idDisplayName)
                val album = cursor.getString(idAlbumn)
                val duration = cursor.getInt(idDuration)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val music = Music(
                    title = displayName,
                    album = album,
                    uri = contentUri.toString(),
                    totalDurationMs = duration
                )

                musics += music
            }

            Log.d("MediaStore", "openMusicPicker: $musics")
        }

        _state.update {
            it.copy(allMusic = musics)
        }
    }
}