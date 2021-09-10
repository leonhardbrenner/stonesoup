import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.InterfaceGenerator
import generators.DtoGenerator
import generators.DbGenerator
import generators.BuilderGenerator
import models.*

import schema.Manifest.Namespace

//Todo - move back to this
open class ModelGenerator : DefaultTask() {

    init {
        group = "com.buckysoap"
        description = "generate"
    }

    @TaskAction
    fun generate() {
        //val flat = Namespace(Flat::class)
        //InterfaceGeneratorOld.generate(flat)
        //DtoGeneratorOld.generate(flat)
        //BuilderGeneratorOld.generate(flat)
        //DbGeneratorOld.generate(flat)
        //CsvLoaderGeneratorOld.generate(flat)
        //
        //val fancy = Namespace(Fancy::class)
        //InterfaceGeneratorOld.generate(fancy)
        //DtoGeneratorOld.generate(fancy)
        //XXX - BuilderGenerator.generate(fancy)

        val seeds = Namespace(Seeds::class)
        listOf(seeds).forEach { namespace ->
            println("Generating ${namespace.name}")
            InterfaceGenerator.generate(namespace)
            DtoGenerator.generate(namespace)
            DbGenerator.generate(namespace)
            BuilderGenerator.generate(namespace)
        }

    }
}
