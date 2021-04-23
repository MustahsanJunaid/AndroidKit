package com.android.kit.file

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.android.kit.ktx.appName
import java.io.File


fun String.toFile(): File? {
    return if (isBlank()) null else File(this)
}

fun String.createDirectoriesIfNeeded() = toFile()?.createDirectoriesIfNeeded()

fun File.createDirectoriesIfNeeded(): File? {
    return if (isDirectoryExists) this else null
}

val String.isDirectoryExists get() = toFile()?.isDirectoryExists ?: false

val File.isDirectoryExists get() = if (exists()) isDirectory else mkdirs()

fun File.createDirectory(dir: String): File? {
    val file = File(this.path + File.separator + dir)
    return file.createDirectoriesIfNeeded()
}


fun File.createFile(name: String, deleteExisting: Boolean = false): File {
    val file = File(this.path + File.separator + name)
    if (deleteExisting) {
        if (file.exists()) {
            file.delete()
        }
    }
    file.createNewFile()
    return file
}

val Context.appDirectory: File?
    get() {
        return getExternalFilesDir(appName)
    }

val Context.privateDirectory: File?
    get() {
        val path = filesDir.toString() + File.separator + appName
        return path.createDirectoriesIfNeeded()
    }

val Context.picturesDirectory: File?
    get() {
        val path =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + File.separator + appName
        return path.createDirectoriesIfNeeded()
    }

val Context.cacheDirectory: File?
    get() {
        return externalCacheDir
    }

fun Context.folderInAppDir(folder: String): File? {
    if (appDirectory == null) return null
    val path = appDirectory!!.path + File.separator + folder
    return path.createDirectoriesIfNeeded()
}

fun Context.folderInPicturesDir(folder: String): File? {
    if (picturesDirectory == null) return null
    val path = picturesDirectory!!.path + File.separator + folder
    return path.createDirectoriesIfNeeded()
}

fun Context.folderInCacheDir(folder: String): File? {
    if (cacheDirectory == null) return null
    val path = cacheDirectory!!.path + File.separator + folder
    return path.createDirectoriesIfNeeded()
}

fun Context.folderInPrivateDir(folder: String): File? {
    if (privateDirectory == null) return null
    val path = privateDirectory!!.path + File.separator + folder
    return path.createDirectoriesIfNeeded()
}

fun File.copy(dest: File) = FileUtils.copyOrMoveFile(this, dest, false)

fun Uri.copy(context: Context, dest: File) =
    FileUtils.copyOrMoveFile(FileUtils.getFileByPath(FileUtils.getPath(context, this)), dest, false)
