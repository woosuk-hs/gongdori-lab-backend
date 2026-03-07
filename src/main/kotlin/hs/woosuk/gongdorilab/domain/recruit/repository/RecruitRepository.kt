package hs.woosuk.gongdorilab.domain.recruit.repository

import hs.woosuk.gongdorilab.domain.recruit.entity.RecruitEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecruitRepository : JpaRepository<RecruitEntity, Long> {
    fun findByStudentId(studentId: String): RecruitEntity?
}