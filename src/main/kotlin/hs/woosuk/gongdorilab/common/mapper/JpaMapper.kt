package hs.woosuk.gongdorilab.common.mapper

import org.springframework.data.jpa.repository.JpaRepository

fun <T: Any, ID: Any> JpaRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return findById(id).orElseThrow { IllegalArgumentException("$id Not found") }
}