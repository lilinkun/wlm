package com.wlm.wlm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wlm.wlm.entity.BrowseRecordBean;
import com.wlm.wlm.entity.BrowseRecordBeanDao;
import com.wlm.wlm.entity.CollectBean;
import com.wlm.wlm.entity.CollectBeanDao;
import com.wlm.wlm.entity.DaoMaster;
import com.wlm.wlm.entity.DaoSession;
import com.wlm.wlm.entity.HomeCategoryBean;
import com.wlm.wlm.entity.HomeCategoryBeanDao;
import com.wlm.wlm.entity.RecordBean;
import com.wlm.wlm.entity.RecordBeanDao;
import com.wlm.wlm.entity.SearchBean;
import com.wlm.wlm.entity.SearchBeanDao;
import com.wlm.wlm.entity.TbMaterielBean;
import com.wlm.wlm.entity.TbMaterielBeanDao;
import com.wlm.wlm.util.WlmUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LG on 2018/12/2.
 */

public class DBManager {
    private final static String dbName = "lzyyd_db";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }


    /**
     * 插入一条记录
     *
     * @param tbMaterielBeanDao
     */
    public void insertTbMateriel(TbMaterielBean tbMaterielBeanDao) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TbMaterielBeanDao userDao = daoSession.getTbMaterielBeanDao();
        userDao.insert(tbMaterielBeanDao);
    }

    /**
     * 插入用户集合
     *
     * @param homeCategoryBeans
     */
    public void insertCategoryList(ArrayList<HomeCategoryBean> homeCategoryBeans) {
        if (homeCategoryBeans == null || homeCategoryBeans.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        HomeCategoryBeanDao userDao = daoSession.getHomeCategoryBeanDao();
        userDao.insertInTx(homeCategoryBeans);
    }

    /**
     * 删除一条记录
     */
    public void deleteCategoryList(HomeCategoryBean homeCategoryBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        HomeCategoryBeanDao userDao = daoSession.getHomeCategoryBeanDao();
        userDao.delete(homeCategoryBean);
    }

    /**
     * 更新一条记录
     *
     * @param homeCategoryBean
     */
    public void updateCategoryList(HomeCategoryBean homeCategoryBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        HomeCategoryBeanDao userDao = daoSession.getHomeCategoryBeanDao();
        userDao.update(homeCategoryBean);
    }


    /**
     * 查询用户列表
     */
    public List<HomeCategoryBean> queryCategoryList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        HomeCategoryBeanDao userDao = daoSession.getHomeCategoryBeanDao();
        QueryBuilder<HomeCategoryBean> qb = userDao.queryBuilder();
        List<HomeCategoryBean> list = qb.list();
        return list;
    }

    /**
     * 删除所有记录
     */
    public void deleteCategoryListBean() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        HomeCategoryBeanDao userDao = daoSession.getHomeCategoryBeanDao();
        userDao.deleteAll();
    }

    /**
     * 插入用户集合
     *
     * @param homeCategoryBeans
     */
    public void insertTbMaterielBeanList(List<HomeCategoryBean> homeCategoryBeans) {
        if (homeCategoryBeans == null || homeCategoryBeans.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        HomeCategoryBeanDao userDao = daoSession.getHomeCategoryBeanDao();
        userDao.insertInTx(homeCategoryBeans);
    }

    /**
     * 删除一条记录
     */
    public void deleteTbMaterielBean(TbMaterielBean tbMaterielBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TbMaterielBeanDao userDao = daoSession.getTbMaterielBeanDao();
        userDao.delete(tbMaterielBean);
    }

    /**
     * 更新一条记录
     *
     * @param tbMaterielBean
     */
    public void updateTbMaterielBean(TbMaterielBean tbMaterielBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TbMaterielBeanDao userDao = daoSession.getTbMaterielBeanDao();
        userDao.update(tbMaterielBean);
    }


    /**
     * 查询用户列表
     */
    public List<TbMaterielBean> queryTbMaterielBeanList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TbMaterielBeanDao userDao = daoSession.getTbMaterielBeanDao();
        QueryBuilder<TbMaterielBean> qb = userDao.queryBuilder();
        List<TbMaterielBean> list = qb.list();
        return list;
    }

    /**
     * 删除所有记录
     */
    public void deleteAllTbMaterielBean() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        TbMaterielBeanDao newsInfoDao = daoSession.getTbMaterielBeanDao();
        newsInfoDao.deleteAll();
    }

    /**
     * 插入一条记录
     *
     * @param collectBean
     */
    public void insertCollectBean(CollectBean collectBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectBeanDao userDao = daoSession.getCollectBeanDao();
        userDao.insert(collectBean);
    }

    /**
     * 查询用户列表
     */
    public List<CollectBean> queryCollectBean(String username) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectBeanDao userDao = daoSession.getCollectBeanDao();
        QueryBuilder<CollectBean> qb = userDao.queryBuilder();
        qb.where(CollectBeanDao.Properties.User_id.eq(username)).orderAsc(CollectBeanDao.Properties.User_id);
        List<CollectBean> list = qb.list();
        return list;
    }

    /**
     * 删除一条记录
     *
     * @param collectBean
     */
    public void deleteOneCollectBean(CollectBean collectBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectBeanDao collectBeanDao = daoSession.getCollectBeanDao();
        collectBeanDao.deleteByKey(collectBean.getGoods_id());
    }


    /**
     * 删除一条记录
     *
     * @param recordBean
     */
    public void deleteOneBrowseBean(BrowseRecordBean recordBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        BrowseRecordBeanDao browseRecordBeanDao = daoSession.getBrowseRecordBeanDao();
        browseRecordBeanDao.deleteByKey(recordBean.getId());
    }

    /**
     * 删除多条记录
     *
     * @param recordBean
     */
    public void deleteBrowseBean(ArrayList<Long> recordBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        BrowseRecordBeanDao browseRecordBeanDao = daoSession.getBrowseRecordBeanDao();
        browseRecordBeanDao.deleteByKeyInTx(recordBean);
    }

    /**
     * 插入一条记录
     *
     * @param recordBean
     */
    public void insertBrowseBean(BrowseRecordBean recordBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        BrowseRecordBeanDao browseRecordBeanDao = daoSession.getBrowseRecordBeanDao();
        browseRecordBeanDao.insert(recordBean);
    }

    /**
     * 查询用户列表
     */
    public List<BrowseRecordBean> queryBrowseBean(BrowseRecordBean recordBean) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        BrowseRecordBeanDao browseRecordBeanDao = daoSession.getBrowseRecordBeanDao();
        QueryBuilder<BrowseRecordBean> qb = browseRecordBeanDao.queryBuilder();
        qb.where(BrowseRecordBeanDao.Properties.Goods_id.eq(recordBean.getGoods_id())).orderAsc(BrowseRecordBeanDao.Properties.Goods_id);
        qb.where(BrowseRecordBeanDao.Properties.Browse_date.eq(WlmUtil.getCurDate())).orderAsc(BrowseRecordBeanDao.Properties.Browse_date);
        List<BrowseRecordBean> list = qb.list();
        return list;
    }
    /**
     * 查询用户列表
     */
    public List<BrowseRecordBean> queryBrowseBean(String username) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        BrowseRecordBeanDao browseRecordBeanDao = daoSession.getBrowseRecordBeanDao();
        QueryBuilder<BrowseRecordBean> qb = browseRecordBeanDao.queryBuilder();
        qb.where(BrowseRecordBeanDao.Properties.User_id.eq(username)).orderAsc(BrowseRecordBeanDao.Properties.User_id);
        List<BrowseRecordBean> list = qb.list();
        return list;
    }

    /**
     * 删除所有记录
     */
    public void deleteAllBrowseBean() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        BrowseRecordBeanDao browseRecordBeanDao = daoSession.getBrowseRecordBeanDao();
        browseRecordBeanDao.deleteAll();
    }

    /**
     * 更新一条记录
     *
     * @param collectBean
     */
    public void updateOneCollectBean(CollectBean collectBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectBeanDao collectBeanDao = daoSession.getCollectBeanDao();
        collectBeanDao.update(collectBean);
    }


    /**
     * 查询用户列表
     */
    public List<CollectBean> queryGoodCollectBean(Long goodsid) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectBeanDao userDao = daoSession.getCollectBeanDao();
        QueryBuilder<CollectBean> qb = userDao.queryBuilder();
        qb.where(CollectBeanDao.Properties.Goods_id.eq(goodsid)).orderAsc(CollectBeanDao.Properties.Goods_id);
        List<CollectBean> list = qb.list();
        return list;
    }

    /**
     * 删除所有记录
     */
    public void deleteAllCollectBean() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CollectBeanDao userDao = daoSession.getCollectBeanDao();
        userDao.deleteAll();
    }


    /**
     * 插入一条记录
     *
     * @param searchBean
     */
    public void insertSearchBean(SearchBean searchBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearchBeanDao userDao = daoSession.getSearchBeanDao();
        userDao.insert(searchBean);
    }

    /**
     * 查询用户列表
     */
    public List<SearchBean> querySearch(String search) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearchBeanDao userDao = daoSession.getSearchBeanDao();
        QueryBuilder<SearchBean> qb = userDao.queryBuilder();
        qb.where(SearchBeanDao.Properties.Searchname.eq(search)).orderAsc(SearchBeanDao.Properties.Searchname);
        List<SearchBean> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<SearchBean> querySearchBean(String username) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearchBeanDao userDao = daoSession.getSearchBeanDao();
        QueryBuilder<SearchBean> qb = userDao.queryBuilder();
        qb.where(SearchBeanDao.Properties.Username.eq(username)).orderAsc(SearchBeanDao.Properties.Username);
        List<SearchBean> list = qb.list();
        return list;
    }

    /**
     * 删除所有记录
     */
    public void deleteAllSearchBean() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        SearchBeanDao userDao = daoSession.getSearchBeanDao();
        userDao.deleteAll();
    }

    /**
     * 插入一条记录
     *
     * @param recordBean
     */
    public void insertRecordBean(RecordBean recordBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecordBeanDao recordBeanDao = daoSession.getRecordBeanDao();
        recordBeanDao.insert(recordBean);
    }

    /**
     * 查询用户列表
     */
    public List<RecordBean> queryRecordBean(String username) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecordBeanDao recordBeanDao = daoSession.getRecordBeanDao();
        QueryBuilder<RecordBean> qb = recordBeanDao.queryBuilder();
        qb.where(RecordBeanDao.Properties.User_id.eq(username)).orderAsc(RecordBeanDao.Properties.User_id);
        List<RecordBean> list = qb.list();
        return list;
    }

    /**
     * 删除一条记录
     *
     * @param recordBean
     */
    public void deleteOneRecordBean(RecordBean recordBean) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecordBeanDao recordBeanDao = daoSession.getRecordBeanDao();
        recordBeanDao.deleteByKey(recordBean.getGoods_id());
    }

    /**
     * 更新一条记录
     *
     * @param recordBean
     */
    public void updateOneRecordBean(RecordBean recordBean){
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecordBeanDao recordBeanDao = daoSession.getRecordBeanDao();
        recordBeanDao.update(recordBean);
    }


    /**
     * 查询用户列表
     */
    public List<RecordBean> queryGoodRecordBean(long goodsid) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecordBeanDao recordBeanDao = daoSession.getRecordBeanDao();
        QueryBuilder<RecordBean> qb = recordBeanDao.queryBuilder();
        qb.where(RecordBeanDao.Properties.Goods_id.eq(goodsid)).orderAsc(RecordBeanDao.Properties.Goods_id);
        List<RecordBean> list = qb.list();
        return list;
    }

    /**
     * 删除所有记录
     */
    public void deleteAllRecordBean() {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        RecordBeanDao recordBeanDao = daoSession.getRecordBeanDao();
        recordBeanDao.deleteAll();
    }

}
