package applications

import com.google.inject.AbstractModule
import com.mongodb.ConnectionString
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import generated.model.Seeds.Chore
import generated.model.SeedsDto
import generated.model.db.SeedsDb
import javax.inject.Inject
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.ChoreCreate
import models.NodeUpdate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import services.SeedsService
import java.util.*

//Moving on I need to fix my id. I will move to name=path. This gives me assigned vs unassigned.
class PlanPrioritizeApplication @Inject constructor(val service: Service, val database: CoroutineDatabase) {

    val collection
        get() = database.getCollection<Chore>()

    fun routesFrom(routing: Routing) = routing.route(SeedsDto.Chore.path) {

        get {
            //call.respond(collection.find().toList())
            call.respond(service.get())
        }

        post {
            val item = call.receive<ChoreCreate>()
            service.add(item)
            call.respond(HttpStatusCode.OK)
        }
        //
        ////XXX -- none of this works anymore. figure out the mongo api.
        //put("/{id}") {
        //    val item = call.receive<NodeUpdate>()
        //    service.update(item)
        //    call.respond(HttpStatusCode.OK)
        //}
        //
        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            service.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }

    object Module : AbstractModule() {

        override fun configure() {
            bind(CoroutineDatabase::class.java).toInstance(database())
        }

        fun database(): CoroutineDatabase {
            val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
                ConnectionString("$it?retryWrites=false")
            }
            val client = connectionString?.let {
                KMongo.createClient(connectionString).coroutine
            } ?: KMongo.createClient().coroutine
            return client.getDatabase(connectionString?.database ?: "test")
        }

    }

    //Rename to CRUDService.
    class Service @Inject constructor(
        val database: CoroutineDatabase,
        val seedsService: SeedsService) {

        //val collection
        //    get() = database.getCollection<Chore>()

        //suspend fun get(): List<Chore> {
        //    //Reinsert root.
        //    //collection.insertOne(Chore(name = "<root>", id = 0, parentId = -1))
        //    return collection.find().toList()
        //}
        fun get(): List<Chore> {
            //Reinsert root.
            //collection.insertOne(Chore(name = "<root>", id = 0, parentId = -1))
            return SeedsDb.Chore.fetchAll()
        }

        fun add(item: ChoreCreate): Int {
            var id = -1
            transaction {
                id = SeedsDb.Chore.Table.insertAndGetId {
                    it[parentId] = item.parentId
                    it[name] = item.name
                    it[childrenIds] = ""
                }.value
                val childrenIds = SeedsDb.Chore.Table.select {
                    SeedsDb.Chore.Table.id.eq(item.parentId)
                }.single()[SeedsDb.Chore.Table.childrenIds]
                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(item.parentId) }) {
                    val newChildrenId = if (childrenIds == "") id.toString() else childrenIds + "," + id.toString()
                    it[SeedsDb.Chore.Table.childrenIds] = childrenIds
                }
            }
            return id
        }

        /**
         * Todo
         *   the node id should then be used for our update
         *   let's start with move then do link
         *   display on the front end more like a graph
         *   create a link routine something as simple as little x(es) that connect
         *   make a priority widget something like a +/-
         *   make a box for real description
         *   we need a field for time estimates
         *   move to tornadoFx
         */
        fun update(item: NodeUpdate) {
            //collection.updateOne(
            //    Chore::id eq parent(item.id),
            //    pull(Chore::childrenIds, item.id!!)
            //)
            //collection.updateOne(
            //    Chore::id eq item.moveTo,
            //    addToSet(Chore::childrenIds, item.id!!)
            //)
            //collection.updateOne(
            //    Chore::id eq item.id,
            //    setValue(Chore::parentId, item.moveTo!!)
            //)
        }

        ////Todo - make this non-nullable
        //suspend fun element(id: Int) =
        //    collection.findOne(Chore::id eq id)
        //
        //suspend fun parent(id: Int) =
        //    element(id)!!.parentId
        //
        fun delete(id: Int) {
            transaction {
                val parentId = SeedsDb.Chore.Table.select {
                    SeedsDb.Chore.Table.id.eq(id)
                }.single()[SeedsDb.Chore.Table.parentId]
                val childrenIds = SeedsDb.Chore.Table.select {
                    SeedsDb.Chore.Table.id.eq(parentId)
                }.single()[SeedsDb.Chore.Table.childrenIds]
                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(parentId) }) {
                    val newChildrenId = if (childrenIds == "") id.toString() else childrenIds + "," + id.toString()
                    it[SeedsDb.Chore.Table.childrenIds] = newChildrenId
                }
                SeedsDb.Chore.Table.deleteWhere { SeedsDb.Chore.Table.id eq id }
            }
        }
        //    collection.updateOne(
        //        Chore::id eq parent(id),
        //        pull(Chore::childrenIds, id)
        //    )
        //    collection.deleteMany(Chore::id eq id)
    }
}