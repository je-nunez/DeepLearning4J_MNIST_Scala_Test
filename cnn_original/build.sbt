
name := "MNISTdl4jScala_CNN"

version := "0.0.1"

scalaVersion := "2.11.11"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature",
                      "-encoding", "utf8", "-Xfuture", "-Xlint")

fork in run := true
fork in Test := true

javaOptions in run ++= Seq(
    "-Xms4G", "-Xmx4G", "-XX:+UseG1GC"
)

// remove the [info] preffixes given by SBT
outputStrategy        :=   Some(StdoutOutput)


testOptions in Test += Tests.Argument("-oD")

concurrentRestrictions in Global += Tags.limit(Tags.Test, 1)

val versions = Map(
                    "nd4j" -> "0.9.1",
                    "dl4j" -> "0.9.1",
                    "guava" -> "20.0",
                    "datavec" -> "0.9.1",
                    "twelvemonkeys" -> "3.3.2",
                    "jfreechart" -> "1.0.19",
                    "arbiter-deeplearning4j" -> "0.9.1"
                  )

libraryDependencies ++= Seq(
    "org.nd4j" % "nd4j-native-platform" % versions("nd4j"),
    // "org.nd4j" % "nd4j-cuda-7.5-platform" % versions("nd4j"),
    // "org.nd4j" % "nd4j-my-specific-backend" % "my-version",

    "org.deeplearning4j" % "deeplearning4j-core" % versions("dl4j"),
    "org.deeplearning4j" % "deeplearning4j-nlp" % versions("dl4j"),
    "org.deeplearning4j" % "deeplearning4j-ui_2.11" % versions("dl4j"),
    "org.deeplearning4j" % "deeplearning4j-play_2.11" % versions("dl4j"),

    "com.google.guava" % "guava" % versions("guava"),
    "org.datavec" % "datavec-data-codec" % versions("datavec"),

    "com.twelvemonkeys.common" % "common-lang" % versions("twelvemonkeys"),
    "com.twelvemonkeys.common" % "common-io" % versions("twelvemonkeys"),
    "com.twelvemonkeys.common" % "common-image" % versions("twelvemonkeys"),
    "com.twelvemonkeys.imageio" % "imageio-core" % versions("twelvemonkeys"),
    "com.twelvemonkeys.imageio" % "imageio-metadata" % versions("twelvemonkeys"),

    "org.jfree" % "jfreechart" % versions("jfreechart"),
    "org.deeplearning4j" % "arbiter-deeplearning4j" % versions("arbiter-deeplearning4j")
)


resolvers ++= Seq(
  "Netbeans Repository" at "http://bits.netbeans.org/nexus/content/groups/netbeans/",
  "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
  "Spray Repository" at "http://repo.spray.cc/",
  "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
  "Akka Repository" at "http://repo.akka.io/releases/",
  "Twitter4J Repository" at "http://twitter4j.org/maven2/",
  "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
  "Twitter Maven Repo" at "http://maven.twttr.com/",
  "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
  "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
  Resolver.sonatypeRepo("public")
)

