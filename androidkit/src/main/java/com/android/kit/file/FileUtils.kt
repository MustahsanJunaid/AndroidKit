package com.android.kit.file

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import com.android.kit.ktx.appName
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.security.DigestInputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

/**
 * Created by Mustahsan on 30-Jul-17.
 */
object FileUtils {
    private const val TAG = "FileUtils"
    private val LINE_SEP = System.getProperty("line.separator")
    fun initializeAppDir(context: Context): Boolean {
        val path = getAppDirPath(context)
        return createOrExistsDir(path)
    }

    private fun getAppDirPath(context: Context): String {
        return Environment.getExternalStorageDirectory().toString() + File.separator + context.appName
    }

    fun getAppDir(context: Context): File? {
        return if (initializeAppDir(context)) getFileByPath(
            getAppDirPath(context)
        ) else null
    }

    @JvmStatic
    fun getFolderInAppDir(context: Context, folder: String): File? {
        val path =
            getAppDir(context).toString() + File.separator + folder
        if (isSpace(path)) return null
        return if (createOrExistsDir(path)) getFileByPath(
            path
        ) else null
    }

    @JvmStatic
    fun getFileByPath(filePath: String?): File? {
        return if (isSpace(filePath)) null else File(
            filePath
        )
    }

    fun isFileExists(filePath: String?): Boolean {
        return isFileExists(
            getFileByPath(
                filePath
            )
        )
    }

    fun isFileExists(file: File?): Boolean {
        return file != null && file.exists()
    }

    fun rename(filePath: String?, newName: String): Boolean {
        return rename(
            getFileByPath(
                filePath
            ), newName
        )
    }

    fun rename(file: File?, newName: String): Boolean {
        if (file == null) return false
        if (!file.exists()) return false
        if (isSpace(newName)) return false
        if (newName == file.name) return true
        val newFile =
            File(file.parent + File.separator + newName)
        return (!newFile.exists()
                && file.renameTo(newFile))
    }

    fun isDir(dirPath: String?): Boolean {
        return isDir(
            getFileByPath(
                dirPath
            )
        )
    }

    fun isDir(file: File?): Boolean {
        return isFileExists(file) && file!!.isDirectory
    }

    fun isFile(filePath: String?): Boolean {
        return isFile(
            getFileByPath(
                filePath
            )
        )
    }

    fun isFile(file: File?): Boolean {
        return isFileExists(file) && file!!.isFile
    }

    fun createOrExistsDir(dirPath: String?): Boolean {
        return createOrExistsDir(
            getFileByPath(
                dirPath
            )
        )
    }

    fun createOrExistsDir(file: File?): Boolean {
        return file != null && if (file.exists()) file.isDirectory else file.mkdirs()
    }

    fun createOrExistsFile(filePath: String?): Boolean {
        return createOrExistsFile(
            getFileByPath(
                filePath
            )
        )
    }

    @JvmStatic
    fun createOrExistsFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists()) return file.isFile
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun createFileByDeleteOldFile(file: File?): Boolean {
        if (file == null) return false
        if (file.exists() && !file.delete()) return false
        return if (!createOrExistsDir(file.parentFile)) false else try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun copyOrMoveDir(
        srcDirPath: String,
        destDirPath: String,
        isMove: Boolean
    ): Boolean {
        return copyOrMoveDir(
            getFileByPath(
                srcDirPath
            ), getFileByPath(destDirPath), isMove
        )
    }

    private fun copyOrMoveDir(
        srcDir: File?,
        destDir: File?,
        isMove: Boolean
    ): Boolean {
        if (srcDir == null || destDir == null) return false
        val srcPath = srcDir.path + File.separator
        val destPath = destDir.path + File.separator
        if (destPath.contains(srcPath)) return false
        if (!srcDir.exists() || !srcDir.isDirectory) return false
        if (!createOrExistsDir(destDir)) return false
        val files = srcDir.listFiles()
        for (file in files) {
            val oneDestFile = File(destPath + file.name)
            if (file.isFile) {
                if (!copyOrMoveFile(
                        file,
                        oneDestFile,
                        isMove
                    )
                ) return false
            } else if (file.isDirectory) {
                if (!copyOrMoveDir(
                        file,
                        oneDestFile,
                        isMove
                    )
                ) return false
            }
        }
        return !isMove || deleteDir(srcDir)
    }

    private fun copyOrMoveFile(
        srcFilePath: String,
        destFilePath: String,
        isMove: Boolean
    ): Boolean {
        return copyOrMoveFile(
            getFileByPath(
                srcFilePath
            ), getFileByPath(destFilePath), isMove
        )
    }

    internal fun copyOrMoveFile(
        srcFile: File?,
        destFile: File?,
        isMove: Boolean
    ): Boolean {
        if (srcFile == null || destFile == null) return false
        if (!srcFile.exists() || !srcFile.isFile) return false
//        if (destFile.exists() && destFile.isFile) return false
        return if (!createOrExistsDir(destFile.parentFile)) false else try {
            (FileIOUtils.writeFileFromIS(destFile, FileInputStream(srcFile), false)
                    && !(isMove && !deleteFile(srcFile)))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            false
        }
    }

    fun copyDir(srcDirPath: String?, destDirPath: String?): Boolean {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath))
    }

    fun copyDir(srcDir: File?, destDir: File?): Boolean {
        return copyOrMoveDir(srcDir, destDir, false)
    }

    fun copyFile(src: File?, dest: File?): Boolean {
        return copyOrMoveFile(src, dest, false)
    }

    fun copyFile(srcFilePath: String?, destFilePath: String?): Boolean {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath))
    }

    fun moveDir(srcDirPath: String?, destDirPath: String?): Boolean {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath))
    }

    fun moveDir(srcDir: File?, destDir: File?): Boolean {
        return copyOrMoveDir(srcDir, destDir, true)
    }

    fun moveFile(srcFilePath: String?, destFilePath: String?): Boolean {
        return moveFile(
            getFileByPath(srcFilePath),
            getFileByPath(destFilePath)
        )
    }

    fun moveFile(srcFile: File?, destFile: File?): Boolean {
        return copyOrMoveFile(srcFile, destFile, true)
    }

    fun deleteDir(dirPath: String?): Boolean {
        return deleteDir(
            getFileByPath(
                dirPath
            )
        )
    }

    fun deleteDir(dir: File?): Boolean {
        if (dir == null) return false
        if (!dir.exists()) return true
        if (!dir.isDirectory) return false
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (file.isFile) {
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    if (!deleteDir(file)) return false
                }
            }
        }
        return dir.delete()
    }

    fun deleteFile(srcFilePath: String?): Boolean {
        return deleteFile(
            getFileByPath(
                srcFilePath
            )
        )
    }

    fun deleteFile(file: File?): Boolean {
        return file != null && (!file.exists() || file.isFile && file.delete())
    }

    fun deleteFilesInDir(dirPath: String?): Boolean {
        return deleteFilesInDir(
            getFileByPath(
                dirPath
            )
        )
    }

    fun deleteFilesInDir(dir: File?): Boolean {
        if (dir == null) return false
        if (!dir.exists()) return true
        if (!dir.isDirectory) return false
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (file.isFile) {
                    if (!file.delete()) return false
                } else if (file.isDirectory) {
                    if (!deleteDir(file)) return false
                }
            }
        }
        return true
    }

    fun listFilesInDir(
        dirPath: String?,
        isRecursive: Boolean
    ): List<File>? {
        return listFilesInDir(
            getFileByPath(
                dirPath
            ), isRecursive
        )
    }

    fun listFilesInDir(
        dir: File?,
        isRecursive: Boolean
    ): List<File>? {
        if (!isDir(dir)) return null
        if (isRecursive) return listFilesInDir(dir)
        val list: MutableList<File> = ArrayList()
        val files = dir!!.listFiles()
        if (files != null && files.isNotEmpty()) {
            Collections.addAll(list, *files)
        }
        return list
    }

    fun listFilesInDir(dirPath: String?): List<File>? {
        return listFilesInDir(
            getFileByPath(
                dirPath
            )
        )
    }

    fun listFilesInDir(dir: File?): List<File>? {
        if (!isDir(dir)) return null
        val list: MutableList<File> = ArrayList()
        val files = dir!!.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                list.add(file)
                if (file.isDirectory) {
                    val fileList =
                        listFilesInDir(file)
                    if (fileList != null) {
                        list.addAll(fileList)
                    }
                }
            }
        }
        return list
    }

    fun listFilesInDirWithFilter(
        dirPath: String?,
        suffix: String,
        isRecursive: Boolean
    ): List<File>? {
        return listFilesInDirWithFilter(
            getFileByPath(
                dirPath
            ), suffix, isRecursive
        )
    }

    fun listFilesInDirWithFilter(
        dir: File?,
        suffix: String,
        isRecursive: Boolean
    ): List<File>? {
        if (isRecursive) return listFilesInDirWithFilter(
            dir,
            suffix
        )
        if (dir == null || !isDir(dir)) return null
        val list: MutableList<File> = ArrayList()
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (file.name.toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file)
                }
            }
        }
        return list
    }

    fun listFilesInDirWithFilter(
        dirPath: String?,
        suffix: String
    ): List<File>? {
        return listFilesInDirWithFilter(
            getFileByPath(
                dirPath
            ), suffix
        )
    }

    fun listFilesInDirWithFilter(
        dir: File?,
        suffix: String
    ): List<File>? {
        if (dir == null || !isDir(dir)) return null
        val list: MutableList<File> = ArrayList()
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (file.name.toUpperCase().endsWith(suffix.toUpperCase())) {
                    list.add(file)
                }
                if (file.isDirectory) {
                    list.addAll(
                        listFilesInDirWithFilter(
                            file,
                            suffix
                        )!!
                    )
                }
            }
        }
        return list
    }

    fun listFilesInDirWithFilter(
        dirPath: String?,
        filter: FilenameFilter,
        isRecursive: Boolean
    ): List<File>? {
        return listFilesInDirWithFilter(
            getFileByPath(
                dirPath
            ), filter, isRecursive
        )
    }

    fun listFilesInDirWithFilter(
        dir: File?,
        filter: FilenameFilter,
        isRecursive: Boolean
    ): List<File>? {
        if (isRecursive) return listFilesInDirWithFilter(
            dir,
            filter
        )
        if (dir == null || !isDir(dir)) return null
        val list: MutableList<File> = ArrayList()
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (filter.accept(file.parentFile, file.name)) {
                    list.add(file)
                }
            }
        }
        return list
    }

    fun listFilesInDirWithFilter(
        dirPath: String?,
        filter: FilenameFilter
    ): List<File>? {
        return listFilesInDirWithFilter(
            getFileByPath(
                dirPath
            ), filter
        )
    }

    fun listFilesInDirWithFilter(
        dir: File?,
        filter: FilenameFilter
    ): List<File>? {
        if (dir == null || !isDir(dir)) return null
        val list: MutableList<File> = ArrayList()
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (filter.accept(file.parentFile, file.name)) {
                    list.add(file)
                }
                if (file.isDirectory) {
                    list.addAll(
                        listFilesInDirWithFilter(
                            file,
                            filter
                        )!!
                    )
                }
            }
        }
        return list
    }

    fun searchFileInDir(
        dirPath: String?,
        fileName: String
    ): List<File>? {
        return searchFileInDir(
            getFileByPath(
                dirPath
            ), fileName
        )
    }

    fun searchFileInDir(
        dir: File?,
        fileName: String
    ): List<File>? {
        if (dir == null || !isDir(dir)) return null
        val list: MutableList<File> = ArrayList()
        val files = dir.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                if (file.name.toUpperCase() == fileName.toUpperCase()) {
                    list.add(file)
                }
                if (file.isDirectory) {
                    list.addAll(searchFileInDir(file, fileName)!!)
                }
            }
        }
        return list
    }

    fun getFileLastModified(filePath: String?): Long {
        return getFileLastModified(
            getFileByPath(
                filePath
            )
        )
    }

    fun getFileLastModified(file: File?): Long {
        return file?.lastModified() ?: -1
    }

    fun getFileCharsetSimple(filePath: String?): String {
        return getFileCharsetSimple(
            getFileByPath(
                filePath
            )
        )
    }

    fun getFileCharsetSimple(file: File?): String {
        var p = 0
        var `is`: InputStream? = null
        try {
            `is` = BufferedInputStream(FileInputStream(file))
            p = (`is`.read() shl 8) + `is`.read()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIO(`is`)
        }
        return when (p) {
            0xefbb -> "UTF-8"
            0xfffe -> "Unicode"
            0xfeff -> "UTF-16BE"
            else -> "GBK"
        }
    }

    fun getFileLines(filePath: String?): Int {
        return getFileLines(
            getFileByPath(
                filePath
            )
        )
    }

    fun getFileLines(file: File?): Int {
        var count = 1
        var `is`: InputStream? = null
        try {
            `is` = BufferedInputStream(FileInputStream(file))
            val buffer = ByteArray(1024)
            var readChars: Int
            if (LINE_SEP!!.endsWith("\n")) {
                while (`is`.read(buffer, 0, 1024).also { readChars = it } != -1) {
                    for (i in 0 until readChars) {
                        if (buffer[i] == '\n'.toByte()) ++count
                    }
                }
            } else {
                while (`is`.read(buffer, 0, 1024).also { readChars = it } != -1) {
                    for (i in 0 until readChars) {
                        if (buffer[i] == '\r'.toByte()) ++count
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIO(`is`)
        }
        return count
    }

    fun getDirSize(dirPath: String?): String {
        return getDirSize(
            getFileByPath(
                dirPath
            )
        )
    }

    fun getDirSize(dir: File?): String {
        val len = getDirLength(dir)
        return if (len == -1L) "" else byte2FitMemorySize(len)
    }

    fun getFileSize(filePath: String?): String {
        return getFileSize(
            getFileByPath(
                filePath
            )
        )
    }

    fun getFileSize(file: File?): String {
        val len = getFileLength(file)
        return if (len == -1L) "" else byte2FitMemorySize(len)
    }

    fun getDirLength(dirPath: String?): Long {
        return getDirLength(
            getFileByPath(
                dirPath
            )
        )
    }

    fun getDirLength(dir: File?): Long {
        if (!isDir(dir)) return -1
        var len: Long = 0
        val files = dir!!.listFiles()
        if (files != null && files.size != 0) {
            for (file in files) {
                len += if (file.isDirectory) {
                    getDirLength(file)
                } else {
                    file.length()
                }
            }
        }
        return len
    }

    fun getFileLength(filePath: String?): Long {
        return getFileLength(
            getFileByPath(
                filePath
            )
        )
    }

    fun getFileLength(file: File?): Long {
        return if (!isFile(file)) -1 else file!!.length()
    }

    fun getFileMD5(filePath: String?): ByteArray? {
        val file =
            if (isSpace(filePath)) null else File(filePath)
        return getFileMD5(file)
    }

    fun getFileMD5(file: File?): ByteArray? {
        if (file == null) return null
        var dis: DigestInputStream? = null
        try {
            val fis = FileInputStream(file)
            var md = MessageDigest.getInstance("MD5")
            dis = DigestInputStream(fis, md)
            val buffer = ByteArray(1024 * 256)
            while (true) {
                if (dis.read(buffer) <= 0) break
            }
            md = dis.messageDigest
            return md.digest()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            CloseUtils.closeIO(dis)
        }
        return null
    }

    fun getDirName(file: File?): String? {
        return if (file == null) null else getDirName(file.path)
    }

    fun getDirName(filePath: String): String {
        if (isSpace(filePath)) return filePath
        val lastSep = filePath.lastIndexOf(File.separator)
        return if (lastSep == -1) "" else filePath.substring(0, lastSep + 1)
    }

    fun getFileName(file: File?): String? {
        return if (file == null) null else getFileName(file.path)
    }

    fun getFileName(filePath: String): String {
        if (isSpace(filePath)) return filePath
        val lastSep = filePath.lastIndexOf(File.separator)
        return if (lastSep == -1) filePath else filePath.substring(lastSep + 1)
    }

    fun getFileNameNoExtension(file: File?): String? {
        return if (file == null) null else getFileNameNoExtension(file.path)
    }

    fun getFileNameNoExtension(filePath: String): String {
        if (isSpace(filePath)) return filePath
        val lastPoi = filePath.lastIndexOf('.')
        val lastSep = filePath.lastIndexOf(File.separator)
        if (lastSep == -1) {
            return if (lastPoi == -1) filePath else filePath.substring(0, lastPoi)
        }
        return if (lastPoi == -1 || lastSep > lastPoi) {
            filePath.substring(lastSep + 1)
        } else filePath.substring(lastSep + 1, lastPoi)
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param file 文件
     * @return 文件拓展名
     */
    fun getFileExtension(file: File?): String? {
        return if (file == null) null else getFileExtension(file.path)
    }

    /**
     * 获取全路径中的文件拓展名
     *
     * @param filePath 文件路径
     * @return 文件拓展名
     */
    fun getFileExtension(filePath: String): String {
        if (isSpace(filePath)) return filePath
        val lastPoi = filePath.lastIndexOf('.')
        val lastSep = filePath.lastIndexOf(File.separator)
        return if (lastPoi == -1 || lastSep >= lastPoi) "" else filePath.substring(lastPoi + 1)
    }

    /**
     * 字节数转合适内存大小
     *
     * 保留3位小数
     *
     * @param byteNum 字节数
     * @return 合适内存大小
     */
    @SuppressLint("DefaultLocale")
    private fun byte2FitMemorySize(byteNum: Long): String {
        return if (byteNum < 0) {
            "shouldn't be less than zero!"
        } else if (byteNum < 1024) {
            String.format("%.3fB", byteNum.toDouble() + 0.0005)
        } else if (byteNum < 1048576) {
            String.format("%.3fKB", byteNum.toDouble() / 1024 + 0.0005)
        } else if (byteNum < 1073741824) {
            String.format("%.3fMB", byteNum.toDouble() / 1048576 + 0.0005)
        } else {
            String.format("%.3fGB", byteNum.toDouble() / 1073741824 + 0.0005)
        }
    }

    @JvmStatic
    fun writeBitmapInAppDir(
        context: Context,
        folderName: String,
        fileName: String?,
        bitmap: Bitmap,
        quality: Int
    ): File {
        val directory =
            getFolderInAppDir(context, folderName)
        return writeBitmap(directory, fileName, bitmap, quality)
    }

    fun readJsonFromAppDir(
        context: Context,
        folderName: String,
        fileName: String?
    ): JSONObject? {
        val directory =
            getFolderInAppDir(context, folderName)
        return readJson(directory, fileName)
    }

    fun readJson(directory: File?, fileName: String?): JSONObject? {
        return try {
            createOrExistsDir(directory)
            val file = File(directory, fileName)
            createOrExistsFile(file)
            //check whether file exists
            val `is` = FileInputStream(file)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            val jsonString = String(buffer)
            JSONObject(jsonString)
        } catch (e: IOException) {
            Log.e("TAG", "Error in Reading: " + e.localizedMessage)
            null
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    fun writeFileInAppDir(
        context: Context,
        folderName: String,
        jsonObject: JSONObject
    ): File? {
        val fileName = System.currentTimeMillis().toString() + ".json"
        val directory =
            getFolderInAppDir(context, folderName)
        return writeFile(directory, fileName, jsonObject)
    }

    fun writeFileInAppDir(
        context: Context,
        folderName: String,
        fileName: String?,
        jsonObject: JSONObject
    ): File? {
        val directory =
            getFolderInAppDir(context, folderName)
        return writeFile(directory, fileName, jsonObject)
    }

    fun writeFile(
        directory: File?,
        fileName: String?,
        jsonObject: JSONObject
    ): File? {
        createOrExistsDir(directory)
        val file = File(directory, fileName)
        createFileByDeleteOldFile(file)
        try {
            val fileWriter = FileWriter(file.path)
            fileWriter.write(jsonObject.toString())
            fileWriter.flush()
            fileWriter.close()
            return file
        } catch (e: IOException) {
            Log.e("TAG", "Error in Writing: " + e.localizedMessage)
        }
        return null
    }

    fun writeBitmap(
        directory: File?,
        fileName: String?,
        bitmap: Bitmap,
        quality: Int
    ): File {
        val file = File(directory, fileName)
        try {
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()
            var fos: FileOutputStream? = null
            fos = FileOutputStream(file)
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, fos)
            fos.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Log.v(
            TAG,
            "created image path: " + file.absolutePath
        )
        return file
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     * @author paulburke
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     * @author paulburke
     */
    fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
            column
        )
        try {
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } catch (ex: IllegalArgumentException) {
            Log.i(
                TAG,
                String.format(
                    Locale.getDefault(),
                    "getDataColumn: _data - [%s]",
                    ex.message
                )
            )
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.<br></br>
     * <br></br>
     * Callers should check whether the path is local before assuming it
     * represents a local file.
     *
     * @param uri The Uri to query.
     * @author paulburke
     */
    @SuppressLint("NewApi")
    fun getPath(context: Context, uri: Uri): String? {
        val isKitKat =
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    java.lang.Long.valueOf(id)
                )
                return getDataColumn(
                    context,
                    contentUri,
                    null,
                    null
                )
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":").toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                    split[1]
                )
                return getDataColumn(
                    context,
                    contentUri,
                    selection,
                    selectionArgs
                )
            }
        } else if ("content".equals(
                uri.scheme,
                ignoreCase = true
            )
        ) { // Return the remote address
            return if (isGooglePhotosUri(uri)) {
                uri.lastPathSegment
            } else getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}