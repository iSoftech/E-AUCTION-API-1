package com.cognizant.fse.eauction.seller.service;

/**
 * Sequence Service Interface to generate custom sequences
 *
 * @author Mohamed Yusuff
 * @since 30/11/2021
 */
public interface SequenceService {

    Integer getNextSequence(String sequenceName);
}
