package utils

import java.security.{MessageDigest, PublicKey}

object HashUtils {
  private val md = MessageDigest.getInstance("SHA-256")
  /**
   * Calculates the SHA-256 hash of a given string.
   *
   * @param text The string to hash.
   * @return The SHA-256 hash as a hexadecimal string.
   */
  def sha256Hash(text: String): String = {
    md.digest(text.getBytes("UTF-8")).map("%02x".format(_)).mkString
  }

  /**
   * Generates a blockchain address from a public key.
   *
   * This method takes the encoded bytes of the public key, computes the SHA-256 hash,
   * and returns the first 20 bytes of the hash as a hexadecimal string. This is a common
   * method to generate a shortened representation of a public key to be used as an address.
   *
   * @param publicKey The public key to generate the address from.
   * @return The generated address as a hexadecimal string.
   */
  def generateAddress(publicKey: PublicKey): String = {
    val bytes = publicKey.getEncoded
    val hash = md.digest(bytes)
    hash.slice(0, 20).map("%02x".format(_)).mkString
  }
}
