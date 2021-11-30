package com.cognizant.fse.eauction.seller.service.impl;

import com.cognizant.fse.eauction.seller.model.IdSequence;
import com.cognizant.fse.eauction.seller.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Sequence Service Implementation class to generate custom sequences
 *
 * @author Mohamed Yusuff
 * @since 30/11/2021
 */
@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private MongoOperations mongoOperations;

    public Integer getNextSequence(String sequenceName) {
        Query query = new Query(Criteria.where("id").is(sequenceName));
        Update update = new Update().inc("sequence", 1);
        IdSequence sequenceCounter = mongoOperations.findAndModify(query, update,
                FindAndModifyOptions.options().returnNew(true).upsert(true), IdSequence.class);
        return Objects.nonNull(sequenceCounter) ? sequenceCounter.getSequence() : 1;
    }
}
