package blockchain.models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import utils.HashUtils

class TransactionSpec extends AnyFlatSpec with Matchers{
  "A Transaction" should "correctly calculate its hash" in {
    val transaction = Transaction("senderAddress", "receiverAddress", 100.0, "Test message")
    val expectedHash = HashUtils.sha256Hash("senderAddressreceiverAddress100.0Test message")

    assert(expectedHash == transaction.hash)
  }
}
