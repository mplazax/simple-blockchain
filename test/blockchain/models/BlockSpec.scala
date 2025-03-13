package blockchain.models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import utils.HashUtils

class BlockSpec extends AnyFlatSpec with Matchers {

  "A Block" should "correctly calculate its hash" in {
    val transactions = List(
      Transaction("sender1", "receiver1", 50.0, "Payment"),
      Transaction("sender2", "receiver2", 25.0, "Gift")
    )
    val block = Block(1, System.currentTimeMillis(), transactions, "previousHashValue")
    val transactionsAsString = transactions.map(t => s"${t.sender}${t.receiver}${t.amount}${t.message}").mkString
    val expectedHash = HashUtils.sha256Hash(s"1${block.timestamp}$transactionsAsString${block.previousHash}${block.nonce}")

    assert(block.calculateHash() == expectedHash, "Block hash calculation failed")
  }
}
