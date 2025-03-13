package blockchain.models

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers
import utils.HashUtils

class BlockchainSpec extends AnyFlatSpec with Matchers {

  "Wallets balance" should "match" in {
    val walletA = new Wallet()
    val walletB = new Wallet()

    val walletAInitialBalance = Blockchain.getAddressBalance(walletA.address)
    val walletBInitialBalance = Blockchain.getAddressBalance(walletB.address)

    walletA.send(walletB.address, 10, "First transaction")

    val walletABalance = Blockchain.getAddressBalance(walletA.address)
    val walletBBalance = Blockchain.getAddressBalance(walletB.address)

    assert(walletAInitialBalance - 10 == walletABalance)
    assert(walletBInitialBalance + 10 == walletBBalance)
  }
}
