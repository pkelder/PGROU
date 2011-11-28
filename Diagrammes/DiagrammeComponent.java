
/**
 * @opt shape node
 * @note Genere une representation des corrections appliquees
 * @depend - - - GrammaticalCorrection
 * @depend - - - OrthographicCorrection
 * @depend - - - PDFExtraction
 * @depend - - - Gson
 */
class CorrectionResult {}

interface Corrector {}

/**
 * @opt shape node
 * @note Applique la correction grammaticale sur le texte extrait
 * @depend - - - LanguageTool
 */
class GrammaticalCorrection implements Corrector {}

/**
 * @opt shape node
 * @note Applique la correction orthograhique sur le texte extrait
 * @depend - - - Hunspell
 * @depend - - - JNA
 */
class OrthographicCorrection implements Corrector {}

/**
 * @opt shape node
 * @note Extrait le texte d'un PDF
 * @depend - - - PDFBox
 */
class PDFExtraction implements PDFExtractor {}

interface PDFExtractor {}

/**
 * @opt shape component
 */
class PDFBox {}

/**
 * @opt shape component
 */
class LanguageTool {}

/**
 * @opt shape component
 */
class Hunspell {}

/**
 * @opt shape component
 */
class JNA {}

/**
 * @opt shape component
 */
class Gson {}

/**
 * Dependances avec des librairies externes
 * @opt shape note
 * @opt commentname
 * @assoc - - - LanguageTool
 * @assoc - - - Hunspell
 * @assoc - - - JNA
 * @assoc - - - Gson
 * @assoc - - - PDFBox
 */
class librariesComment {}

