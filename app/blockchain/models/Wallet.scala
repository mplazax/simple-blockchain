package blockchain.models

import utils.HashUtils
import java.security.{KeyPairGenerator, PublicKey, Signature}
import utils.StringUtils._

/**
 * Wallet class representing a blockchain wallet with a private and public key pair.
 * It allows creating transactions, signing them, and sending funds.
 */
class Wallet {
  println("Creating wallet...")
  private val keyPair = generateKeyPair()
  private val privateKey = keyPair.getPrivate
  val publicKey: PublicKey = keyPair.getPublic
  val address: String = HashUtils.generateAddress(publicKey)
  println(s"Created wallet, address: ${address.shorten()}")

  /**
   * Generates and returns a new key pair using RSA encryption.
   *
   * @return A KeyPair object containing both public and private keys.
   */
  private def generateKeyPair() = {
    val keyGen = KeyPairGenerator.getInstance("RSA")
    keyGen.initialize(2048)
    keyGen.generateKeyPair()
  }

  /**
   * Generates a new transaction from this wallet to a recipient, signs it, and sends it to the blockchain.
   *
   * @param receiverAddress The public key of the transaction's recipient.
   * @param amount The amount of the transaction.
   * @param message An optional message to include in the transaction.
   * @return The signed transaction, wrapped in an Option.
   */
  def send(receiverAddress: String,
           amount: Double,
           message: String = ""): Option[Transaction] = {
    val balance = Blockchain.getAddressBalance(address)
    if (amount > balance) {
      println(s"Cannot send $amount, not enough funds")
      return None
    }
    println(s"Sending $amount from ${address.shorten()} to address ${receiverAddress.shorten()} ...")
    val transaction = Transaction(address, receiverAddress, amount, message)
    transaction.sign(this.signTransaction(transaction))
    Blockchain.addTransaction(transaction)
    Some(transaction)
  }

  /**
   * Signs a transaction with the wallet's private key.
   *
   * @param transaction The transaction to sign.
   * @return The digital signature as a byte array.
   */
  private def signTransaction(transaction: Transaction): Array[Byte] = {
    println(s"Signing transaction $transaction...")
    val data =
      s"${transaction.sender}${transaction.receiver}${transaction.amount}${transaction.message}"
    val dsa = Signature.getInstance("SHA256withRSA")
    dsa.initSign(privateKey)
    dsa.update(data.getBytes)
    dsa.sign()
  }

  /**
   * Retrieves the balance of the wallet.
   *
   * @return The current balance of the wallet.
   */
  def balance: Double = {
    Blockchain.getAddressBalance(address)
  }

  /**
   * Retrieves the list of transactions associated with this wallet.
   *
   * @return A list of transactions involving this wallet.
   */
  def transactions: List[Transaction] = {
    Blockchain.getAddressTransactions(address)
  }
}
