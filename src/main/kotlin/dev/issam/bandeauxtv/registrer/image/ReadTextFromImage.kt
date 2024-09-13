package dev.issam.bandeauxtv.registrer.image

import com.github.pgreze.process.process
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.Scanner

class ReadTextFromImage(private val bandeauImagePath: String) {

    private val tesseractBinPath = "\"c:/Program Files/Tesseract-OCR/tesseract\""
    private val generatedTextFileSuffix = "_out"
    private val generatedTextFileExtension = ".txt"
    private val generatedTextFilePath = bandeauImagePath + generatedTextFileSuffix + generatedTextFileExtension
    private val tesseractOptionFrench = "-l fra"

    fun read(): String = runBlocking {
        readFromImageAndGenerateTextFile()
        val bandeauSingleLineString = readSingleLineInTextFile(generatedTextFilePath)
        deleteBandeauImageAndBandeauTextFile()
        bandeauSingleLineString
    }

    private fun deleteBandeauImageAndBandeauTextFile() {
        File(generatedTextFilePath).delete()
        File(bandeauImagePath).delete()
    }

    private fun readFromImageAndGenerateTextFile() = runBlocking {
        val ocrJob = GlobalScope.launch {
            process(
                tesseractBinPath,
                bandeauImagePath,
                bandeauImagePath + generatedTextFileSuffix
            )
        }
        ocrJob.join()
    }

    private fun readSingleLineInTextFile(textFilePathWithReadContent: String): String {
        val scanner = Scanner(File(textFilePathWithReadContent))
        val line = scanner.nextLine()
        scanner.close()
        return line
    }
}
