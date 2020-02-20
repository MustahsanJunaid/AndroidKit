package com.android.kit.file

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.android.kit.ktx.appName
import java.io.File


fun String.toFile(): File? {
    return if (isBlank()) null else File(this)
}

fun String.directory(): File? {
    val file = toFile()
    val exits = file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    return if (exits) file else null
}

fun File.directory() = if (exists()) isDirectory else mkdirs()

fun File.directory(dir: String): File {
    val file = File(this.path + File.separator + dir)
    file.directory()
    return file
}

fun File.file(fileName: String, deleteExisting: Boolean = false): File {
    val file = File(this.path + File.separator + fileName)
    if (deleteExisting) {
        if (file.exists()) {
            file.delete()
        }
        file.createNewFile()
    }
    return file
}

val Context.appDirectory: File?
    get() {
        val path = Environment.getExternalStorageDirectory().toString() + File.separator + appName
        return path.directory()
    }

val Context.privateDirectory: File?
    get() {
        val path = filesDir.toString() + File.separator + appName
        return path.directory()
    }

val Context.picturesDirectory: File?
    get() {
        val path =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + File.separator + appName
        return path.directory()
    }

val Context.cacheDirectory: File?
    get() {
        return externalCacheDir
    }

fun Context.folderInAppDir(folder: String): File? {
    if (appDirectory == null) return null
    val appDirPath = appDirectory!!.path
    val path = appDirPath + File.separator + folder
    return path.directory()
}

fun Context.folderInPicturesDir(folder: String): File? {
    if (picturesDirectory == null) return null
    val picturesDirectoryPath = picturesDirectory!!.path
    val path = picturesDirectoryPath + File.separator + folder
    return path.directory()
}

fun Context.folderInCacheDir(folder: String): File? {
    if (cacheDirectory == null) return null
    val picturesDirectoryPath = cacheDirectory!!.path
    val path = picturesDirectoryPath + File.separator + folder
    return path.directory()
}

fun Context.folderInPrivateDir(folder: String): File? {
    if (privateDirectory == null) return null
    val appDirPath = privateDirectory!!.path
    val path = appDirPath + File.separator + folder
    return path.directory()
}

fun File.copy(dest: File) = FileUtils.copyOrMoveFile(this, dest, false)

fun Uri.copy(context: Context, dest: File) =
    FileUtils.copyOrMoveFile(FileUtils.getFileByPath(FileUtils.getPath(context, this)), dest, false)
