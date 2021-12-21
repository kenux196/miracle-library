package org.kenux.miraclelibrary.service;

import org.kenux.miraclelibrary.domain.Customer;
import org.kenux.miraclelibrary.rest.dto.CustomerJoinDto;

public interface CustomerService {

    Customer join(CustomerJoinDto customerJoinDto);
}