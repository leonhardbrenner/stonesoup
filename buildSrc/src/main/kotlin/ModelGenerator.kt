import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.InterfaceGenerator
import generators.DtoGenerator
import generators.DbGenerator
import generators.BuilderGenerator
import generators.CsvLoaderGenerator
import generators.InterfaceGenerator2
import generators.DtoGenerator2
import models.*


import schema.Manifest.Namespace
import kotlin.reflect.KClass

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.buckysoap"
        description = "generate"
    }

    @TaskAction
    fun generate() {
        /*
        val flat = Namespace(Flat::class)
        InterfaceGenerator.generate(flat)
        DtoGenerator.generate(flat)
        BuilderGenerator.generate(flat)
        DbGenerator.generate(flat)
        CsvLoaderGenerator.generate(flat)

        val fancy = Namespace(Fancy::class)
        InterfaceGenerator.generate(fancy)
        DtoGenerator.generate(fancy)
        //XXX - BuilderGenerator.generate(fancy)
        */
        //val seeds = Namespace(Seeds::class)
        //listOf(seeds).forEach { namespace ->
        //    InterfaceGenerator.generate(namespace)
        //    DbGenerator.generate(namespace)
        //    DtoGenerator.generate(namespace)
        //    BuilderGenerator.generate(namespace)
        //}

        //manifest.namespaces.values.forEach { namespace ->
        listOf(manifest.namespaces["Seeds"]!!).forEach { namespace ->
            InterfaceGenerator2.generate(namespace)
            DtoGenerator2.generate(namespace)
        }

    }
}
