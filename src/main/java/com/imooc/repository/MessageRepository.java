package com.imooc.repository;

import com.imooc.dataobject.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Created by qcl on 2019-03-31
 * 微信：2501902696
 * desc:
 */
public interface MessageRepository extends JpaRepository<Message, String> {
    List<Message> findAllByUserId(String id);

}
