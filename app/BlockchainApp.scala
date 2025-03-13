import blockchain.models.{Blockchain, Wallet}

object BlockchainApp extends App {
  val walletA = new Wallet()
  val walletB = new Wallet()

  walletA.send(walletB.address, 10, "First transaction")

  println(s"Balance of wallet A: ${walletA.balance}")
  println(s"Balance of wallet B: ${walletB.balance}")

  walletA.send(walletB.address, 10, "First transaction")
  walletA.send(walletB.address, 15, "First transaction")
  walletA.send(walletB.address, 25, "First transaction")

}
