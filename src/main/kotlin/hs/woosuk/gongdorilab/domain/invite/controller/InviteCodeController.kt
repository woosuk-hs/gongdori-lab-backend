package hs.woosuk.gongdorilab.domain.invite.controller

import hs.woosuk.gongdorilab.domain.invite.dto.InviteCodeCreateDTO
import hs.woosuk.gongdorilab.domain.invite.dto.InviteCodeResponseDTO
import hs.woosuk.gongdorilab.domain.invite.mapper.toResponseDTO
import hs.woosuk.gongdorilab.domain.invite.service.InviteCodeService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/invite")
class InviteCodeController(
    private val inviteCodeService: InviteCodeService
) {

    @PostMapping("/create")
    fun create(@RequestBody dto: InviteCodeCreateDTO): InviteCodeResponseDTO {
        return inviteCodeService.createInviteCode(dto).toResponseDTO()
    }
}