import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.BuilderGenerator
import generators.DbGenerator
import generators.DtoGenerator
import generators.InterfaceGenerator
import models.manifest

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.buckysoap"
        description = "generate"
    }

    @TaskAction
    fun generate() {
        //TODO - manifest.namespaces.values.forEach { namespace ->
        listOf(manifest.namespaces["Seeds"]!!).forEach { namespace ->
            InterfaceGenerator.generate(namespace)
            DtoGenerator.generate(namespace)
            DbGenerator.generate(namespace)
            BuilderGenerator.generate(namespace)
        }

    }
}
