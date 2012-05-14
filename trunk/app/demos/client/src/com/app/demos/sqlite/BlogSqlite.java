package com.app.demos.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.app.demos.base.BaseSqlite;
import com.app.demos.model.Blog;

public class BlogSqlite extends BaseSqlite {

	public BlogSqlite(Context context) {
		super(context);
	}

	@Override
	protected String tableName() {
		return "blogs";
	}

	@Override
	protected String[] tableColumns() {
		String[] columns = {
			Blog.COL_ID,
			Blog.COL_FACE,
			Blog.COL_CONTENT,
			Blog.COL_COMMENT,
			Blog.COL_AUTHOR,
			Blog.COL_UPTIME
		};
		return columns;
	}

	@Override
	protected String createSql() {
		return "CREATE TABLE " + tableName() + " (" +
			Blog.COL_ID + " INTEGER PRIMARY KEY, " +
			Blog.COL_FACE + " TEXT, " +
			Blog.COL_CONTENT + " TEXT, " +
			Blog.COL_COMMENT + " TEXT, " +
			Blog.COL_AUTHOR + " TEXT, " +
			Blog.COL_UPTIME + " TEXT" +
			");";
	}

	@Override
	protected String upgradeSql() {
		return "DROP TABLE IF EXISTS " + tableName();
	}

	public boolean updateBlog (Blog blog) {
		// prepare blog data
		ContentValues values = new ContentValues();
		values.put(Blog.COL_ID, blog.getId());
		values.put(Blog.COL_FACE, blog.getFace());
		values.put(Blog.COL_CONTENT, blog.getContent());
		values.put(Blog.COL_COMMENT, blog.getComment());
		values.put(Blog.COL_AUTHOR, blog.getAuthor());
		values.put(Blog.COL_UPTIME, blog.getUptime());
		// prepare sql
		String whereSql = Blog.COL_ID + "=?";
		String[] whereParams = new String[]{blog.getId()};
		// create or update
		try {
			if (this.exists(whereSql, whereParams)) {
				this.update(values, whereSql, whereParams);
			} else {
				this.create(values);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public ArrayList<Blog> getAllBlogs () {
		ArrayList<Blog> blogList = new ArrayList<Blog>();
		Cursor cursor = null;
		try {
			cursor = this.query(null, null);
			while (cursor.moveToNext()) {
				Blog blog = new Blog();
				blog.setId(cursor.getString(0));
				blog.setFace(cursor.getString(1));
				blog.setContent(cursor.getString(2));
				blog.setComment(cursor.getString(3));
				blog.setAuthor(cursor.getString(4));
				blog.setUptime(cursor.getString(5));
				blogList.add(blog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
		}
		return blogList;
	}
}