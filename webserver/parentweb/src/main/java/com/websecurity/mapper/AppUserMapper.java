package com.websecurity.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.websecurity.models.AppUser;
import org.springframework.jdbc.core.RowMapper;

public class AppUserMapper implements RowMapper<AppUser> {

    public static final String BASE_SQL //
            = "select u.user_id, u.user_name, u.encrypted_password From app_user u ";
    public static final String INSERT_SQL //
            = "insert into app_user (user_name, encrypted_password, enabled) values ";

    @Override
    public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {

        Long userId = rs.getLong("user_id");
        String userName = rs.getString("user_name");
        String encrytedPassword = rs.getString("encrypted_password");

        return new AppUser(userId, userName, encrytedPassword);
    }

}
