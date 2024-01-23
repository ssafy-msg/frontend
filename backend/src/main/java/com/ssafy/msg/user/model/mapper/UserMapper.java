package com.ssafy.msg.user.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.msg.user.model.dto.FollowDetailDto;
import com.ssafy.msg.user.model.dto.FollowFindDto;
import com.ssafy.msg.user.model.dto.FollowParamDto;
import com.ssafy.msg.user.model.dto.FollowUserDto;
import com.ssafy.msg.user.model.dto.Oauth2Dto;
import com.ssafy.msg.user.model.dto.ProfileImageDto;
import com.ssafy.msg.user.model.dto.SignUpDto;
import com.ssafy.msg.user.model.dto.UserDto;
import com.ssafy.msg.user.model.dto.UserInfoDto;

@Mapper
public interface UserMapper {

	UserDto findUserByEmailId(String emailId) throws SQLException;

	void signUpWithOauth2(Oauth2Dto oauth2Dto) throws SQLException;

	void signInWithOauth2(Oauth2Dto oauth2Dto) throws SQLException;

	void signUp(SignUpDto signUpDto) throws SQLException;

	void signIn(UserDto userDto) throws SQLException;

	void signOut(String emailId) throws SQLException;

	void withdraw(String emailId) throws SQLException;

	UserInfoDto getUserInfo(String emailId) throws SQLException;

	void updateNickname(UserDto userDto) throws SQLException;

	void updatePassword(UserDto userDto) throws SQLException;

	void updateIdentifier(UserDto userDto) throws SQLException;

	void updateImage(ProfileImageDto profileImageDto) throws SQLException;

	void follow(FollowDetailDto followDetailDto) throws SQLException;

	FollowFindDto findFollow(FollowDetailDto followDetailDto) throws SQLException;

	void unfollow(FollowDetailDto followDetailDto) throws SQLException;

	List<FollowUserDto> getFollowList(FollowParamDto followParamDto) throws SQLException;
}
