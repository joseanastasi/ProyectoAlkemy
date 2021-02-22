package com.alkemy.ot9.mappers;

import com.alkemy.ot9.entities.TeamEntity;
import com.alkemy.ot9.models.Team;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamMapper {

    ModelMapper modelMapper;

    @Autowired
    public TeamMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TeamEntity map(Team team) {
        return modelMapper.map(team, TeamEntity.class);
    }

    public Team map(TeamEntity teamEntity) {
        return modelMapper.map(teamEntity, Team.class);
    }

    public List<Team> map(List<TeamEntity> teamEntities) {
        List<Team> teams = new ArrayList<>();
        for (TeamEntity teamEntity : teamEntities) {
            teams.add(map(teamEntity));
        }
        return teams;
    }
}
