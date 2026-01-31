package com.mike.healthmadeeasy.dto.response;

import java.time.Instant;

public record PingResponse( String status, Instant timestamp ) {

}
