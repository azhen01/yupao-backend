package com.zzlearning.yupao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzlearning.yupao.model.domain.UserTeam;
import com.zzlearning.yupao.mapper.UserTeamMapper;
import com.zzlearning.yupao.service.UserTeamService;
import org.springframework.stereotype.Service;

/**
* @author 36447
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2024-04-27 20:48:21
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




