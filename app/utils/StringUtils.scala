package utils

object StringUtils {

  /**
   * Implicit class to add extension methods to String.
   *
   * @param str The string to extend.
   */
  implicit class RichString(str: String) {
    /**
     * Shortens the string to the first n and last n characters (default n=3),
     * with the middle part replaced by an ellipsis ("...").
     *
     * @return The shortened string.
     */
    def shorten(n: Int = 3): String = {
      if (str.length <= 6) {
        str
      } else {
        val firstPart = str.take(n)
        val lastPart = str.takeRight(n)
        s"$firstPart...$lastPart"
      }
    }
  }
}
