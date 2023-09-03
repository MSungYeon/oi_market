package com.lec.domain;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

public class BoardfListeners {

//    @PostLoad
//    public void postLoad(Board board) {
//        System.out.println("post load: {}" +  board);
//    }
    
    @PrePersist
    public void prePersist(Boardf boardf) {
        System.out.println("pre persist: {}" + boardf);
    }
    
    @PostPersist
    public void postPersist(Boardf boardf) {
        System.out.println("post persist: {}" + boardf);
    }
    
    @PreUpdate
    public void preUpdate(Boardf boardf) {
        System.out.println("pre update: {}" + boardf);
    }
    
    @PostUpdate
    public void postUpdate(Boardf boardf) {
        System.out.println("post update: {}" +  boardf);
    }
    
    @PreRemove
    public void preRemove(Boardf boardf) {
        System.out.println("pre remove: {}" +  boardf);
    }
    
    @PostRemove
    public void postRemove(Boardf boardf) {
        System.out.println("post remove: {}" +  boardf);
    }
}

