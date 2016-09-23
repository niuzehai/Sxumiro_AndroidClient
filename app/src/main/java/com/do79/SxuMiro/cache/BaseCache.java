package com.do79.SxuMiro.cache;

import com.do79.greendao.DaoSession;

import java.util.ArrayList;

public abstract class BaseCache<T> {

	public static final String DB_NAME = "sxumiro-db";

	protected static DaoSession mDaoSession;

	public abstract void clearAllCache();

	public abstract ArrayList<T> getCacheByPage(int page);

	public abstract void addResultCache(String result, int page);

}
