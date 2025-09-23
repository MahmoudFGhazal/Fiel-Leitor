package com.mahas.dto.response;

import com.mahas.domain.DomainEntity;

public interface DTOResponse {
    void mapFromEntity(DomainEntity entity);
}
