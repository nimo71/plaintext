package com.plaintext.adapters.slick

import java.util.Properties
import org.slf4j.LoggerFactory
import com.mchange.v2.c3p0.ComboPooledDataSource
import scala.slick.driver.H2Driver.simple._

trait SlickSupport {

    private val logger = LoggerFactory.getLogger(this.getClass)
  
    val cpds = {
        val props = new Properties
        props.load(getClass.getResourceAsStream("/c3p0.properties"))
        
        val cpds = new ComboPooledDataSource
        cpds.setProperties(props)
        logger.debug("Created c3p0 connection pool")
        cpds
    }

    def closeDbConnection() {
        logger.info("Closing c3po connection pool")
        cpds.close
    }

    val db = Database.forDataSource(cpds)

    def destroy() {
        closeDbConnection
    }
}
