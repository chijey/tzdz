package com.imooc.service;

import com.imooc.dataobject.Message;
import com.imooc.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {


    Message create(Message orderDTO);


}
