name := "Recipies_New_Play_Version"
 
version := "1.0" 
      
lazy val `recipies_new_play_version` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.mockito" % "mockito-core" % "2.7.22" % Test,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.20.3-play27",
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.apache.commons" % "commons-email" % "1.5",
  "com.typesafe.play" %% "play-mailer" % "8.0.0",
  "com.typesafe.play" %% "play-mailer-guice" % "8.0.0")



unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  
