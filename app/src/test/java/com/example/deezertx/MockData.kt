package com.example.deezertx

import com.example.deezertx.model.Album
import com.example.deezertx.model.Albums
import com.example.deezertx.model.Artist


val mockAlbums = Albums(
    albums = listOf(
        Album(
            idAlbum = 1581964,
            title = "Franky Knight",
            cover = "http://api.deezer.com/2.0/album/1581964/image",
            coverSmall = "",
            coverMedium = "",
            coverBig = "",
            nbTracks = 10,
            releaseDate = "2011-01-01",
            recordType = "album",
            isAvailable = true,
            timeAdd = 1587418247,
            type = "album",
            artist = Artist(
                idArtist = 1388,
                name = "Emilie Simon",
                picture = "http://api.deezer.com/2.0/artist/1388/image",
                pictureSmall = "http://e-cdn-images.dzcdn.net/images/artist/042ee8fe7dfc247553c87cbaaa7a0afc/56x56-000000-80-0-0.jpg",
                pictureMedium = "http://e-cdn-images.dzcdn.net/images/artist/042ee8fe7dfc247553c87cbaaa7a0afc/56x56-000000-80-0-0.jpg",
                pictureBig = "http://e-cdn-images.dzcdn.net/images/artist/042ee8fe7dfc247553c87cbaaa7a0afc/56x56-000000-80-0-0.jpg",
                trackList = "http://api.deezer.com/2.0/artist/1388/top?limit=50",
                type = "artist",
            )
        ), Album(
            idAlbum = 372073,
            title = "Motown Essentials",
            cover = "http://api.deezer.com/2.0/album/372073/image",
            coverSmall = "",
            coverMedium = "",
            coverBig = "",
            nbTracks = 124,
            releaseDate = "2009-05-15",
            recordType = "album",
            isAvailable = false,
            timeAdd = 1565476465,
            type = "album",
            artist = Artist(
                idArtist = 5080,
                name = "Various Artists",
                picture = "http://api.deezer.com/2.0/artist/5080/image",
                pictureSmall = "http://api.deezer.com/2.0/artist/5080/image",
                pictureMedium = "http://api.deezer.com/2.0/artist/5080/image",
                pictureBig = "http://api.deezer.com/2.0/artist/5080/image",
                trackList = "http://api.deezer.com/2.0/artist/5080/top?limit=50",
                type = "artist"
            )
        )
    ), nextAlbums = null
)