import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import generators.InterfaceGenerator2
import generators.DtoGenerator2
import generators.DbGenerator2
import models.manifest

open class ModelGenerator : DefaultTask() {

    init {
        group = "com.buckysoap"
        description = "generate"
    }

    @TaskAction
    fun generate() {
        //manifest.namespaces.values.forEach { namespace ->
        listOf(manifest.namespaces["Seeds"]!!).forEach { namespace ->
            InterfaceGenerator2.generate(namespace)
            DtoGenerator2.generate(namespace)
            DbGenerator2.generate(namespace)
            //BuilderGenerator.generate(namespace)
        }

    }
}
