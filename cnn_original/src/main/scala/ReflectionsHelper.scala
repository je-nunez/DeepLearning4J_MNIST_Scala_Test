
package mainapp

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

import java.lang.{Iterable => JavaIterable}
import java.net.URL

import org.reflections.vfs.Vfs

/**
 * Scala version of the Java workaround at:
 *
 * https://gist.github.com/nonrational/287ed109bb0852f982e8
 *
 * to avoid the exception messages and stacktraces in some platforms (like Mac OS/X) for
 * org.reflections.ReflectionsException:
 *
 * ... [Reflections.java:208]: org.reflections.Reflections: could not create Vfs.Dir from url. ignoring the exception and continuing
 * ... org.reflections.ReflectionsException: could not create Vfs.Dir from url, no matching UrlType was found
 * .......... [stack-trace-dump] ......
 */
object ReflectionsHelper {

    /**
     * OSX contains file:// resources on the classpath including .mar and .jnilib files.
     *
     * Reflections use of Vfs doesn't recognize these URLs and logs warns when it sees them. By registering those file endings, we supress the warns.
     */
    def registerUrlTypes(): Unit = {

        val urlTypes = ListBuffer.empty[Vfs.UrlType]

        // include a list of file extensions / filenames to be recognized
        urlTypes.add(new EmptyIfFileEndingsUrlType(".mar", ".jnilib"))

        // add all other file extensions / filenames
        urlTypes.addAll(Vfs.DefaultUrlTypes.values.toList)

        Vfs.setDefaultURLTypes(urlTypes.toList)
    }

    private class EmptyIfFileEndingsUrlType(val fileSuffixes: String*)  extends Vfs.UrlType {

        def matches(url: URL): Boolean = {

            val protocol = url.getProtocol
            val externalForm = url.toExternalForm

            (protocol == "file") &&
              fileSuffixes.exists( fileSuffix => externalForm.endsWith(fileSuffix) )
        }

        @throws(classOf[Exception])
        def createDir(url: URL): Vfs.Dir = {

            def emptyVfsDir(url: URL): Vfs.Dir = {

                new Vfs.Dir() {
                    override def getPath: String = {
                        url.toExternalForm
                    }

                    override def getFiles(): JavaIterable[Vfs.File] = {
                        List[Vfs.File]()
                    }

                    override def close: Unit = { }
                }
            }

            emptyVfsDir(url)
        }

    }
}

