import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.dsl.BuilderGeneratorDsl
import generators.dsl.DbGeneratorDsl
import generators.dsl.DtoGeneratorDsl
import generators.dsl.InterfaceGeneratorDsl
import models.manifestDsl

open class ModelGeneratorDsl : DefaultTask() {

    init {
        group = "com.buckysoap"
        description = "generateDsl"
    }

    @TaskAction
    fun generate() {
        //TODO - manifest.namespaces.values.forEach { namespace ->
        listOf(manifestDsl.namespaces["Seeds"]!!).forEach { namespace ->
            InterfaceGeneratorDsl.generate(namespace)
            DtoGeneratorDsl.generate(namespace)
            DbGeneratorDsl.generate(namespace)
            BuilderGeneratorDsl.generate(namespace)
        }

    }
}
