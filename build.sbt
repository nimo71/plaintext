name := "plaintext"

version := "0.1"

scalaVersion := "2.9.2"

libraryDependencies ++= Seq(
    "net.databinder" %% "unfiltered-filter" % "0.6.3",
    "net.databinder" %% "unfiltered-jetty" % "0.6.3",
    "org.clapper" %% "avsl" % "0.4",
    "org.scalatest" %% "scalatest" % "1.6.1" % "test",
    "postgresql" % "postgresql" % "9.1-901.jdbc4",
    "com.jolbox" % "bonecp" % "0.7.1.RELEASE"
)

// jasmine conf

seq(jasmineSettings : _*)

appJsDir <+= sourceDirectory { src => src / "main" / "resources" / "www" / "js" }

appJsLibDir <+= sourceDirectory { src => src / "main" / "resources" / "www" / "js" / "lib" }

jasmineTestDir <+= sourceDirectory { src => src / "test" / "resources" / "www" / "js" }

jasmineConfFile <+= sourceDirectory { src => src / "test" / "resources" / "www" / "js" / "test.dependencies.js" }

jasmineRequireJsFile <+= sourceDirectory { src => src / "main" / "resources" / "www" / "js" / "lib" / "require-jquery.js" }

jasmineRequireConfFile <+= sourceDirectory { src => src / "test" / "resources" / "www" / "js" / "require.conf.js" }

(test in Test) <<= (test in Test) dependsOn (jasmine)
