name := "plaintext"

version := "0.1"

// scalaVersion := "2.9.2"

scalaVersion := "2.10.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
    "net.databinder" %% "unfiltered-filter" % "0.6.6",
    "net.databinder" %% "unfiltered-jetty" % "0.6.6",
    "net.databinder" %% "unfiltered-scalatest" % "0.6.6" % "test",
	"net.databinder.dispatch" %% "dispatch-core" % "0.9.5" % "test",
	"ch.qos.logback" % "logback-classic" % "1.0.9",
    "org.scalatest" %% "scalatest" % "1.6.1" % "test",
    "net.minidev" % "json-smart" % "1.1.1", 
    "com.typesafe.slick" %% "slick" % "1.0.0",
  	"org.slf4j" % "slf4j-nop" % "1.6.4",
  	"com.h2database" % "h2" % "1.3.166", 
  	"c3p0" % "c3p0" % "0.9.1.2"
)

//"org.clapper" %% "avsl" % "0.4",
//"postgresql" % "postgresql" % "9.1-901.jdbc4",
//"com.jolbox" % "bonecp" % "0.7.1.RELEASE", 


// jasmine conf

seq(jasmineSettings : _*)

appJsDir <+= sourceDirectory { src => src / "main" / "resources" / "www" / "js" }

appJsLibDir <+= sourceDirectory { src => src / "main" / "resources" / "www" / "js" / "lib" }

jasmineTestDir <+= sourceDirectory { src => src / "test" / "resources" / "www" / "js" }

jasmineConfFile <+= sourceDirectory { src => src / "test" / "resources" / "www" / "js" / "test.dependencies.js" }

jasmineRequireJsFile <+= sourceDirectory { src => src / "main" / "resources" / "www" / "js" / "lib" / "require-jquery.js" }

jasmineRequireConfFile <+= sourceDirectory { src => src / "test" / "resources" / "www" / "js" / "require.conf.js" }

(test in Test) <<= (test in Test) dependsOn (jasmine)
