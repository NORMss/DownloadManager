package com.norm.mydownloadmanager

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class AndroidDownloader(
    private val context: Context,
) : Downloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {
        val tokenFile = context.assets.open("kaggle.json")
        val jsonString = BufferedReader(
            InputStreamReader(tokenFile)
        )
        val token = Gson().fromJson(jsonString, Kaggle::class.java)

        val request = DownloadManager.Request(url.toUri())
            .setMimeType("application/gzip")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("archive.tar.gz")
            .addRequestHeader(
                "Authorization",
                "Bearer $token}"
            )
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "archive.tar.gz")
        return downloadManager.enqueue(request)
    }
}

data class Kaggle(
    val name: String,
    val key: String,
)