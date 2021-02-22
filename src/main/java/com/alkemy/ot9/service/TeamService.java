
package com.alkemy.ot9.service;

import com.alkemy.ot9.entities.TeamEntity;
import com.alkemy.ot9.exceptions.TeamNotFound;
import com.alkemy.ot9.interfaceService.ITeamService;
import com.alkemy.ot9.mappers.TeamMapper;
import com.alkemy.ot9.models.Team;
import com.alkemy.ot9.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService implements ITeamService {

	TeamMapper teamMapper;

	TeamRepository teamRepository;

	@Autowired
	public TeamService(TeamMapper teamMapper, TeamRepository teamRepository) {
		this.teamMapper = teamMapper;
		this.teamRepository = teamRepository;
	}

	public Long createTeam(Team team) {
		TeamEntity createTeamEntity = teamRepository.save(teamMapper.map(team));
		return createTeamEntity.getId();
	}

	public List<Team> findAllTeams() {
		return teamMapper.map((List<TeamEntity>) teamRepository.findAll());
	}

	public Team getTeamById(Long id) throws TeamNotFound {
		Optional<TeamEntity> teamEntity = teamRepository.findById(id);
		if (teamEntity.isEmpty()) {
			throw new TeamNotFound();
		}
		return teamMapper.map(teamEntity.get());
	}

	public void deleteTeamById(Long id) throws TeamNotFound {
		Optional<TeamEntity> teamEntity = teamRepository.findById(id);
		if (teamEntity.isEmpty()) {
			throw new TeamNotFound();
		}
		teamRepository.deleteById(id);
	}

	@Override
	public Page<TeamEntity> getAllTeams(Pageable pageable) {
		return teamRepository.findAll(pageable);
	}

}
