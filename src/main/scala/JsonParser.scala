import scala.io.Source
import scala.util.{Try, Success, Failure}
import java.io.File

object JsonParser {
    def main(args: Array[String]): Unit = {
        val testFolder = if (args.nonEmpty) args(0) else "step1"
        val basePath = s"data/tests/$testFolder/"

        val directory = new File(basePath)
        if (!directory.exists() || !directory.isDirectory) {
            println(s"Directory $basePath does not exist or is not a directory.")
            System.exit(1)
        }

        val testFiles = directory.listFiles().filter(_.getName.endsWith(".json")).map(_.getName).toList

        println(s"Found JSON files: ${testFiles.mkString(", ")}")

        if (testFiles.isEmpty) {
            println(s"No JSON files found in $basePath.")
            System.exit(1)
        }

        testFiles.foreach { file =>
            val fullPath = getFullPath(basePath, file)
            println(s"Trying to read file: $fullPath")
            val content = readFile(fullPath)
            println(s"Raw content of $fullPath: '${content}'")

            if (content.isEmpty) {
                println(s"$fullPath is empty and considered invalid JSON.")
            } else if (isValidJson(content)) {
                println(s"$fullPath is a valid JSON.")
            } else {
                println(s"$fullPath is an invalid JSON.")
            }
        }
        System.exit(0)
    }

    def getFullPath(basePath: String, fileName: String): String = {
        new File(basePath, fileName).getAbsolutePath
    }

    def readFile(filePath: String): String = {
        Try {
            val source = Source.fromFile(filePath)
            try {
                source.getLines().mkString("\n")
            } finally {
                source.close()
            }
        } match {
            case Success(content) => content
            case Failure(exception) =>
                println(s"Error reading file $filePath: ${exception.getMessage}")
                ""
        }
    }

    def isValidJson(json: String): Boolean = {
        val trimmed = json.trim
        if (!trimmed.startsWith("{") || !trimmed.endsWith("}")) return false

        val content = trimmed.substring(1, trimmed.length - 1).trim
        if (content.isEmpty) return true // {} is considered valid

        val pairs = content.split(",").map(_.trim)
        // Check that each pair is valid
        pairs.forall { pair =>
            // Match key-value pairs with different types including objects and arrays
            // The array starts with [ and ends with ]. The elements inside the array can be strings (in double quotes), numbers, booleans, or null.
            pair.matches("""^"[^"]+"\s*:\s*(?:"[^"]*"|true|false|null|\d+|\{[^{}]*\}|\[[^'"\[\]]*("[^"]*"|true|false|null|\d+)*\])$""")
        } && !content.endsWith(",") // Ensure there is no trailing comma
    }
}