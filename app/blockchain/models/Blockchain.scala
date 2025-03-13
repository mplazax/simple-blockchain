package blockchain.models

object Blockchain {
  val difficulty: Int = 2
  private val initialWalletBalance = 100
  var chain: List[Block] = List(createGenesisBlock())

  /**
   * Creates the genesis block, the first block in the blockchain.
   *
   * @return The genesis block.
   */
  private def createGenesisBlock(): Block =
    Block(0, System.currentTimeMillis(), List(), "0")

  /**
   * Gets the latest block in the blockchain.
   *
   * @return The latest block.
   */
  def latestBlock: Block = chain.last

  /**
   * Adds a new block to the blockchain after mining it.
   *
   * @param newBlock The new block to add.
   */
  private def addBlock(newBlock: Block): Unit = {
    newBlock.mine()
    chain = chain :+ newBlock
  }

  /**
   * Adds a new transaction to the blockchain by creating a new block
   * containing the transaction and adding it to the chain.
   *
   * @param transaction The transaction to add.
   * @return The new block that contains the transaction.
   */
  def addTransaction(transaction: Transaction): Block = {
    val newBlock = Block(chain.size,
      System.currentTimeMillis(),
      List(transaction),
      latestBlock.hash)
    addBlock(newBlock)
    newBlock
  }

  /**
   * Checks if the blockchain is valid by verifying the hashes and previous
   * hashes of all blocks.
   *
   * @return True if the blockchain is valid, false otherwise.
   */
  def isChainValid: Boolean = {
    for (i <- 1 until chain.length) {
      val currentBlock = chain(i)
      val previousBlock = chain(i - 1)

      if (currentBlock.hash != currentBlock.calculateHash()) {
        return false
      }

      if (currentBlock.previousHash != previousBlock.hash) {
        return false
      }
    }
    true
  }

  /**
   * Gets the balance of a specific address by summing up the received and
   * sent amounts from all transactions in the blockchain.
   *
   * @param address The address to check the balance for.
   * @return The balance of the address.
   */
  def getAddressBalance(address: String): Double = {
    var balance = 0.0
    Blockchain.chain.foreach { block =>
      block.transactions.foreach { transaction =>
        if (transaction.receiver == address) {
          balance += transaction.amount
        }
        if (transaction.sender == address) {
          balance -= transaction.amount
        }
      }
    }
    balance + initialWalletBalance
  }

  /**
   * Gets all transactions related to a specific address, either as the sender
   * or the receiver.
   *
   * @param address The address to get transactions for.
   * @return A list of transactions related to the address.
   */
  def getAddressTransactions(address: String): List[Transaction] = {
    transactions.filter(tx => tx.sender == address || tx.receiver == address)
  }

  /**
   * Gets all transactions in the blockchain.
   *
   * @return A list of all transactions.
   */
  def transactions: List[Transaction] = {
    chain.flatMap(_.transactions)
  }

  /**
   * Gets the block with specified hash.
   *
   * @return Block.
   */
  def getBlockByHash(blockHash: String): Option[Block] = {
    chain.find(_.hash == blockHash)
  }
}
