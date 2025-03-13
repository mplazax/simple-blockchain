package blockchain.models
import play.api.libs.json.{Json, OFormat}
import utils.HashUtils

/**
 * Represents a blockchain's block.
 *
 * @param index index of the block
 * @param timestamp timestamp at which block was created
 * @param transactions list of transactions included in the block
 * @param previousHash hash of the previous block
 * @param hash hash of the block
 * @param nonce number used once
 */
case class Block(index: Int, timestamp: Long, transactions: List[Transaction], previousHash: String, var hash: String = "", var nonce: Long = 0) {
  /**
   * Mines the block by finding a nonce that results in a hash
   * with a specified number of leading zeros, as defined by the difficulty.
   *
   * The mining process involves repeatedly incrementing the nonce and
   * recalculating the hash until a valid hash is found.
   *
   * */
  def mine(): Unit = {
    println(s"Mining block...")
    val startTimestamp = System.currentTimeMillis()
    val target = "0" * Blockchain.difficulty
    while (!hash.startsWith(target)) {
      nonce += 1
      hash = calculateHash()
    }

    val endTimestamp = System.currentTimeMillis()
    val timeDifference = endTimestamp - startTimestamp
    println(s"Mined block! Took ${timeDifference}ms")
  }

  /**
   * Calculates hash of the block based on its data.
   *
   * @return hash
   */
  def calculateHash(): String = {
    val transactionsAsString = transactions.map(t => s"${t.sender}${t.receiver}${t.amount}${t.message}").mkString
    val toHash = s"$index$timestamp$transactionsAsString$previousHash$nonce"
    HashUtils.sha256Hash(toHash)
  }
}

object Block {
  implicit val format: OFormat[Block] = Json.format[Block]
}
