package com.alkemy.ot9.interfaceService;

import com.alkemy.ot9.entities.TeamEntity;
import com.alkemy.ot9.exceptions.TeamNotFound;
import com.alkemy.ot9.models.Team;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITeamService {

	Long createTeam(Team team);

	List<Team> findAllTeams();

	Team getTeamById(Long id) throws TeamNotFound;

	void deleteTeamById(Long id) throws TeamNotFound;

	Page<TeamEntity> getAllTeams(Pageable pageable);
}
