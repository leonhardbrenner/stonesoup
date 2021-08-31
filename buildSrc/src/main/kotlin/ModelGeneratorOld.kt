import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.InterfaceGeneratorOld
import generators.DtoGeneratorOld
import generators.DbGeneratorOld
import generators.BuilderGeneratorOld
import generators.CsvLoaderGeneratorOld
import models.*

import schema.ManifestOld.Namespace

open class ModelGeneratorOld : DefaultTask() {

    init {
        group = "com.buckysoap"
        description = "generateOld"
    }

    @TaskAction
    fun generate() {
        val flat = Namespace(Flat::class)
        InterfaceGeneratorOld.generate(flat)
        DtoGeneratorOld.generate(flat)
        BuilderGeneratorOld.generate(flat)
        DbGeneratorOld.generate(flat)
        CsvLoaderGeneratorOld.generate(flat)

        val fancy = Namespace(Fancy::class)
        InterfaceGeneratorOld.generate(fancy)
        DtoGeneratorOld.generate(fancy)
        //XXX - BuilderGenerator.generate(fancy)

        val seeds = Namespace(Seeds::class)
        listOf(seeds).forEach { namespace ->
            InterfaceGeneratorOld.generate(namespace)
            DbGeneratorOld.generate(namespace)
            DtoGeneratorOld.generate(namespace)
            BuilderGeneratorOld.generate(namespace)
        }

    }
}
