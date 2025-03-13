package blockchain.models
import play.api.libs.json.{Json, OFormat}
import utils.HashUtils

/**
 * Represents a transaction on a blockchain.
 *
 * @param sender sender of the transaction
 * @param receiver receiver of the transaction
 * @param amount value of the transaction
 * @param message message attached to the transaction
 * @param hash hash of the transaction
 * @param signature signature of the transaction
 */
case class Transaction(sender: String,
                       receiver: String,
                       amount: Double,
                       message: String,
                       var hash: String = "",
                       var signature: Array[Byte] = Array.emptyByteArray) {

  hash = transactionHash()
  println(s"Created transaction with hash $hash")

  /**
    * Calculates the SHA-256 hash of the transaction's data.
    * Includes the sender, receiver, amount, and message to ensure the integrity and uniqueness of each transaction.
    *
    * @return the SHA-256 hash as a hexadecimal string
    */
  private def transactionHash(): String = {
    HashUtils.sha256Hash(s"$sender$receiver$amount$message")
  }

  /**
    * Attaches a digital signature to the transaction, typically called from the Wallet.
    *
    * @param sig the digital signature generated using the sender's private key
    */
  def sign(sig: Array[Byte]): Unit = {
    this.signature = sig
  }
}

object Transaction {
  implicit val format: OFormat[Transaction] = Json.format[Transaction]
}
