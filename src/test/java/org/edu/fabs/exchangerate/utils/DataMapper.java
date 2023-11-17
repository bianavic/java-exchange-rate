//package org.edu.fabs.exchangerate.utils;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataMapper {
//
//    @Value("classpath:json/byBaseCode_OK.json")
//    private Resource byBaseCodeOK;
//
//    @Value("classpath:json/byPair_OK.json")
//    private Resource characterComicsOK;
//
//    @Value("classpath:json/byPairConversion_OK.json")
//    private Resource characterEventsOK;
//
//    @Value("classpath:json/byBaseCode_NotFound.json")
//    private Resource byBaseCodeNotFound;
//
//    public Resource getByBaseCodeOK() {
//        return byBaseCodeOK;
//    }
//
//    public void setByBaseCodeOK(Resource byBaseCodeOK) {
//        this.byBaseCodeOK = byBaseCodeOK;
//    }
//
//    public Resource getCharacterComicsOK() {
//        return characterComicsOK;
//    }
//
//    public void setCharacterComicsOK(Resource characterComicsOK) {
//        this.characterComicsOK = characterComicsOK;
//    }
//
//    public Resource getCharacterEventsOK() {
//        return characterEventsOK;
//    }
//
//    public void setCharacterEventsOK(Resource characterEventsOK) {
//        this.characterEventsOK = characterEventsOK;
//    }
//
//    public Resource getByBaseCodeNotFound() {
//        return byBaseCodeNotFound;
//    }
//
//    public void setByBaseCodeNotFound(Resource byBaseCodeNotFound) {
//        this.byBaseCodeNotFound = byBaseCodeNotFound;
//    }
//
//}
