package applications

import com.google.inject.AbstractModule
import com.mongodb.ConnectionString
import org.litote.kmongo.coroutine.coroutine
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
class PlanPrioritizeApplication @Inject constructor(val service: Service) {

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
        put("/{id}") {
            val item = call.receive<NodeUpdate>()
            service.update(item)
            call.respond(HttpStatusCode.OK)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toInt() ?: error("Invalid delete request")
            service.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }

    object Module : AbstractModule() {

        override fun configure() {
            //bind(CoroutineDatabase::class.java).toInstance(database())
        }

        //fun database(): CoroutineDatabase {
        //    val connectionString: ConnectionString? = System.getenv("MONGODB_URI")?.let {
        //        ConnectionString("$it?retryWrites=false")
        //    }
        //    val client = connectionString?.let {
        //        KMongo.createClient(connectionString).coroutine
        //    } ?: KMongo.createClient().coroutine
        //    return client.getDatabase(connectionString?.database ?: "test")
        //}

    }

    //Rename to CRUDService.
    class Service @Inject constructor(
        val seedsService: SeedsService) {

        fun get() = transaction { SeedsDb.Chore.fetchAll() }

        fun add(item: ChoreCreate): Int {
            var id = -1
            transaction {
                id = SeedsDb.Chore.Table.insertAndGetId {
                    it[SeedsDb.Chore.Table.parentId] = item.parentId
                    it[SeedsDb.Chore.Table.name] = item.name
                    it[SeedsDb.Chore.Table.childrenIds] = ""
                }.value
                val childrenIds = SeedsDb.Chore.Table.select {
                    SeedsDb.Chore.Table.id.eq(item.parentId)
                }.single()[SeedsDb.Chore.Table.childrenIds]
                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(item.parentId) }) {
                    it[SeedsDb.Chore.Table.childrenIds] = (childrenIds.split(",") + id.toString()).joinToString(",")
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
            transaction {

                //Remove node from old parent children list
                val oldParentId = SeedsDb.Chore.Table.select {
                    SeedsDb.Chore.Table.id.eq(item.id)
                }.single()[SeedsDb.Chore.Table.parentId]

                val oldChildrenIds = SeedsDb.Chore.Table.select {
                    SeedsDb.Chore.Table.id.eq(oldParentId)
                }.single()[SeedsDb.Chore.Table.childrenIds]

                //Insert node to new parent children list
                val newParentId = item.moveTo

                val newChildrenIds = SeedsDb.Chore.Table.select {
                    SeedsDb.Chore.Table.id.eq(newParentId)
                }.single()[SeedsDb.Chore.Table.childrenIds]

                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(oldParentId) }) {
                    //Remove item from old list
                    val oldChildrenIdsRewritten = (oldChildrenIds.split(",") - item.id.toString()).joinToString(",")
                    it[SeedsDb.Chore.Table.childrenIds] = oldChildrenIdsRewritten
                }

                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(newParentId) }) {
                    it[SeedsDb.Chore.Table.childrenIds] = (newChildrenIds.split(",") + item.id.toString()).joinToString(",")
                }

                SeedsDb.Chore.Table.update({ SeedsDb.Chore.Table.id.eq(item.id) }) {
                    it[SeedsDb.Chore.Table.parentId] = item.moveTo!!
                }
            }
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
                    val newChildrenId = (childrenIds.split(",") - id.toString()).joinToString(",")
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
