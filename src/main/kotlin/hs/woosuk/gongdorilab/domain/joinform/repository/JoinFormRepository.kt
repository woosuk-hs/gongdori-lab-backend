package hs.woosuk.gongdorilab.domain.joinform.repository

import hs.woosuk.gongdorilab.domain.joinform.entity.JoinFormEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JoinFormRepository : JpaRepository<JoinFormEntity, Long> {
    fun findByStudentId(studentId: String): JoinFormEntity?
}