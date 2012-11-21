package ar.com.aleatoria.ue.dao;

import ar.com.aleatoria.ue.dao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table INSTANT.
 */
public class Instant {

    private Long id;
    private java.util.Date date_created;
    private java.util.Date date_registered;
    private Boolean sync;
    private Long subjectId;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient InstantDao myDao;

    private Subject subject;
    private Long subject__resolvedKey;


    public Instant() {
    }

    public Instant(Long id) {
        this.id = id;
    }

    public Instant(Long id, java.util.Date date_created, java.util.Date date_registered, Boolean sync, Long subjectId) {
        this.id = id;
        this.date_created = date_created;
        this.date_registered = date_registered;
        this.sync = sync;
        this.subjectId = subjectId;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInstantDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.util.Date getDate_created() {
        return date_created;
    }

    public void setDate_created(java.util.Date date_created) {
        this.date_created = date_created;
    }

    public java.util.Date getDate_registered() {
        return date_registered;
    }

    public void setDate_registered(java.util.Date date_registered) {
        this.date_registered = date_registered;
    }

    public Boolean getSync() {
        return sync;
    }

    public void setSync(Boolean sync) {
        this.sync = sync;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    /** To-one relationship, resolved on first access. */
    public Subject getSubject() {
        if (subject__resolvedKey == null || !subject__resolvedKey.equals(subjectId)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SubjectDao targetDao = daoSession.getSubjectDao();
            subject = targetDao.load(subjectId);
            subject__resolvedKey = subjectId;
        }
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
        subjectId = subject == null ? null : subject.getId();
        subject__resolvedKey = subjectId;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
