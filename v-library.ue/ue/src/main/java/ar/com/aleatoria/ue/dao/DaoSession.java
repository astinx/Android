package ar.com.aleatoria.ue.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import ar.com.aleatoria.ue.dao.Subject;
import ar.com.aleatoria.ue.dao.Instant;

import ar.com.aleatoria.ue.dao.SubjectDao;
import ar.com.aleatoria.ue.dao.InstantDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig subjectDaoConfig;
    private final DaoConfig instantDaoConfig;

    private final SubjectDao subjectDao;
    private final InstantDao instantDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        subjectDaoConfig = daoConfigMap.get(SubjectDao.class).clone();
        subjectDaoConfig.initIdentityScope(type);

        instantDaoConfig = daoConfigMap.get(InstantDao.class).clone();
        instantDaoConfig.initIdentityScope(type);

        subjectDao = new SubjectDao(subjectDaoConfig, this);
        instantDao = new InstantDao(instantDaoConfig, this);

        registerDao(Subject.class, subjectDao);
        registerDao(Instant.class, instantDao);
    }
    
    public void clear() {
        subjectDaoConfig.getIdentityScope().clear();
        instantDaoConfig.getIdentityScope().clear();
    }

    public SubjectDao getSubjectDao() {
        return subjectDao;
    }

    public InstantDao getInstantDao() {
        return instantDao;
    }

}
