package com.cakkie.backend.dto.adminLogin;

import lombok.Builder;

@Builder
public record AdminMailBody(String to, String subject, String text) {

}
