import java.io.File
import java.util.Scanner

class ReadTextFromBandeauImage(private val bandeauImagePath: String) {

    private val tesseractBinPath = "\"c:/Program Files/Tesseract-OCR/tesseract\""
    private val generatedTextFileSuffix = "_out"
    private val generatedTextFileExtension = ".txt"
    private val tesseractOptionFrench = "-l fra"

    fun readLine(): String {
        val textFilePathWithReadContent = readFromImageAndGenerateTextFile()
        val bandeauSingleLineString = readSingleLineInTextFile(textFilePathWithReadContent)
        // deleteBandeauImageAndBandeauTextFile(textFilePathWithReadContent)
        return bandeauSingleLineString
    }

    private fun deleteBandeauImageAndBandeauTextFile(textFilePathWithReadContent: String) {
        File(textFilePathWithReadContent).delete()
        File(bandeauImagePath).delete()
    }

    private fun readSingleLineInTextFile(textFilePathWithReadContent: String): String {
        val scanner = Scanner(File(textFilePathWithReadContent))
        return scanner.nextLine()
    }

    private fun readFromImageAndGenerateTextFile(): String {
        val textFilePathWithReadContent = bandeauImagePath + generatedTextFileSuffix
        val process = ProcessBuilder(
            tesseractBinPath,
            bandeauImagePath,
            textFilePathWithReadContent,
            tesseractOptionFrench
        )
        process.start()
        return textFilePathWithReadContent + generatedTextFileExtension
    }
}
