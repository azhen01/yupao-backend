package com.zzlearning.yupao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzlearning.yupao.model.domain.Team;
import com.zzlearning.yupao.model.domain.User;
import com.zzlearning.yupao.model.domain.dto.TeamQuery;
import com.zzlearning.yupao.model.domain.request.TeamJoinRequest;
import com.zzlearning.yupao.model.domain.request.TeamQuitRequest;
import com.zzlearning.yupao.model.domain.request.TeamUpdateRequest;
import com.zzlearning.yupao.model.domain.vo.TeamUserVO;

import java.util.List;

/**
* @author 36447
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2024-04-27 20:45:56
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery,boolean isAdmin);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest,User loginUser);


    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest,User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除（解散）队伍
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id,User loginUser);


}
