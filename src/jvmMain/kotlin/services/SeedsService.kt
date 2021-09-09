package services

import com.google.inject.Inject
import dao.SeedsDao
import generated.model.db.SeedsDb
import org.jetbrains.exposed.sql.transactions.transaction

class SeedsService @Inject constructor(val dao: SeedsDao) {

    fun getDetailedSeeds() = transaction { dao.DetailedSeeds.index() }

}
