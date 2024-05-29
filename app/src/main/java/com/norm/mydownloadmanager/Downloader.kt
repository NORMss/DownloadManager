package com.norm.mydownloadmanager

interface Downloader {
    fun downloadFile(url: String): Long
}