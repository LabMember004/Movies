package com.example.data.mapper

import com.example.data.model.UpdateEmailRequestDTO
import com.example.data.model.UpdateEmailResponseDTO
import com.example.domain.entity.UpdateEmailRequest
import com.example.domain.entity.UpdateEmailResponse

fun UpdateEmailRequestDTO.toUpdateEmailRequest(): UpdateEmailRequest {
    return UpdateEmailRequest(
        email = this.email,

    )
}
fun UpdateEmailResponseDTO.toUpdateEmailResponse(): UpdateEmailResponse {
    return UpdateEmailResponse(
        message= this.message
    )
}
fun UpdateEmailRequest.toUpdateEmailRequestDTO(): UpdateEmailRequestDTO {
    return UpdateEmailRequestDTO(
        email= this.email
    )
}

fun UpdateEmailResponse.toUpdateEmailResponseDTO(): UpdateEmailResponseDTO {
    return UpdateEmailResponseDTO(
        message = this.message
    )
}