package dev.issam.bandeauxtv.registrer.image

import java.io.File

class WatchFilesInFolderAndCallback {

    fun watchFolderAndCallback(folderToWatch: String, callbackFn: (screenshotFilename: String) -> Unit) {
        println("Watching $folderToWatch for new screenshots to read...")
        while (true) {
            if (File(folderToWatch).listFiles()?.size ?: 0 > 0) {
                for (screenshotFilename in File(folderToWatch).listFiles()
                    .filter { file -> file.isFile }
                    .filter { file -> isAnImage(file) }
                    .map { file -> file.name }) {
                    println("New screenshot to read = $folderToWatch$screenshotFilename")
                    callbackFn(screenshotFilename)
                }
            }
        }
    }

    private fun isAnImage(file: File) =
        "png".equals(file.extension, false)
                || "jpg".equals(file.extension, false)
                || "jpeg".equals(file.extension, false)

}
