package hs.woosuk.gongdorilab.common.mapper

import org.springframework.data.jpa.repository.JpaRepository

fun <T, ID> JpaRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return findById(id).orElseThrow { IllegalArgumentException("$id Not found") }
}

inline fun <T> T?.orThrow(exception: () -> Exception): T {
    return this ?: throw exception()
}