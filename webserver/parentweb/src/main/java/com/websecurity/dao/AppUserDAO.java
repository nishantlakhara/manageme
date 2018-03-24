package com.websecurity.dao;

import javax.sql.DataSource;


import com.websecurity.mapper.AppUserMapper;
import com.websecurity.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AppUserDAO extends JdbcDaoSupport {

    @Autowired
    public AppUserDAO(DataSource dataSource) {
        this.setDataSource(dataSource);
    }

    public AppUser findUserAccount(String userName) {
        // Select .. from App_User u Where u.User_Name = ?
        String sql = AppUserMapper.BASE_SQL + " where u.user_name = ? ";

        Object[] params = new Object[] { userName };
        AppUserMapper mapper = new AppUserMapper();
        try {
            AppUser userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
            return userInfo;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int insert(AppUser user) {
        String sql = AppUserMapper.INSERT_SQL + "( ?, ?, ? )";

        Object[] params = new Object[] { user.getUserName(), user.getEncryptedPassword(), 1 };

        int row = this.getJdbcTemplate().update(sql, params);
        System.out.println("Rows inserted : " + row);
        return row;
    }

}

