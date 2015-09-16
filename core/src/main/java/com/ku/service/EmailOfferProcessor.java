package com.ku.service;

import java.text.ParseException;

import com.ku.common.KUException;


public interface EmailOfferProcessor {

	boolean processPayoomOffers() throws KUException, ParseException;
}
