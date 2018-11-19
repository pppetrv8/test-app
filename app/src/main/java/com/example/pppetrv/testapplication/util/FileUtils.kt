package com.example.pppetrv.testapplication.util

import android.content.Context
import java.io.File

object FileUtils {

    fun getExtCacheDir(c: Context): File {
        return c.externalCacheDir
    }

    fun createTmpFile(c: Context, filename: String): File {
        val extCacheDir = getExtCacheDir(c)
        val filePath = extCacheDir?.absolutePath + File.separator + filename
        return File(filePath)
    }

    private fun deleteFile(file: String): Boolean {
        val f = File(file)
        return if (f.exists()) {
            f.delete()
        } else false
    }

    fun deleteFile(filePath: String, deleteRecursive: Boolean): Boolean {
        val file = File(filePath)
        return deleteFile(file, deleteRecursive)
    }

    private fun deleteFile(file: File, deleteRecursive: Boolean): Boolean {
        if (file.isDirectory && deleteRecursive) {
            if (file.listFiles() != null) {
                for (child in file.listFiles()) {
                    deleteFile(child, deleteRecursive)
                }
            }
        }
        return file.delete()
    }
}