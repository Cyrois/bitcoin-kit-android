package io.horizontalsystems.dashkit.storage

import android.arch.persistence.room.*
import io.horizontalsystems.bitcoincore.models.*
import io.horizontalsystems.bitcoincore.models.Transaction
import io.horizontalsystems.bitcoincore.storage.CoreDatabase
import io.horizontalsystems.dashkit.models.Masternode
import io.horizontalsystems.dashkit.models.MasternodeListState

@Database(entities = [
    FeeRate::class,
    BlockchainState::class,
    PeerAddress::class,
    BlockHash::class,
    Block::class,
    SentTransaction::class,
    Transaction::class,
    TransactionInput::class,
    TransactionOutput::class,
    PublicKey::class,
    Masternode::class,
    MasternodeListState::class
], version = 5, exportSchema = false)
abstract class DashKitDatabase : CoreDatabase() {
    abstract val masternodeDao: MasternodeDao
    abstract val masternodeListStateDao: MasternodeListStateDao
}

@Dao
interface MasternodeDao {
    @Query("SELECT * FROM Masternode")
    fun getAll(): List<Masternode>

    @Query("DELETE FROM Masternode")
    fun clearAll()

    @Insert
    fun insertAll(masternodes: List<Masternode>)

}

@Dao
interface MasternodeListStateDao {
    @Query("SELECT * FROM MasternodeListState LIMIT 1")
    fun getState(): MasternodeListState?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setState(state: MasternodeListState)
}
