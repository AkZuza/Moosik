package com.akzuza.moosik.screens.home

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akzuza.moosik.entities.Music
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val contentResolver: ContentResolver) : ViewModel() {

    private var _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state;

    fun addMultipleMusicFiles(uris: List<Uri>) {
        viewModelScope.launch {
            val musicList = mutableListOf<Music>()
            for (uri in uris) {
                // add index here for testing delete operation, to be removed
                // id is index here
                val newIndex = state.value.allMusic.size + musicList.size
                val music = getMusicFromUri(uri)
                music?.let { musicList.add(it.copy(id = newIndex)) }
            }

            _state.update {
                it.copy(
                    allMusic = it.allMusic + musicList
                )
            }
        }
    }

    suspend fun getMusicFromUri(uri: Uri): Music? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        val music = cursor?.use { cursor ->
            try {
                if (cursor.moveToNext()) {
                    val displayName = cursor.getString(
                        cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                    )

//                    val duration = cursor.getInt(
//                        cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DURATION)
//                    )
//
//                    val album = cursor.getString(
//                        cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.ALBUM)
//                    )

                    return@use Music(
                        title = displayName,
                        album = "No album, fix",
                        totalDurationMs = 0
                    )
                } else {
                    return null
                }
            } catch (err: Exception) {
                Log.d("Error", "getMusicFromUri: $err")
                return null
            }
        }

        return music
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