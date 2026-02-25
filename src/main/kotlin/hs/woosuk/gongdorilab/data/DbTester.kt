package hs.woosuk.gongdorilab.data

import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.sql.DataSource

//@Component
//class DbTester {
//
//    @Autowired
//    lateinit var dataSource: DataSource
//
//    @PostConstruct
//    fun testConnection() {
//        dataSource.connection.use { conn ->
//            println("DB 연결 OK!")
//            println("DB 이름: ${conn.metaData.databaseProductName}")
//            println("DB URL: ${conn.metaData.url}")
//        }
//    }
//}