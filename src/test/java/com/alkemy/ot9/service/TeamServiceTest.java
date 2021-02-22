package com.alkemy.ot9.service;

import com.alkemy.ot9.entities.TeamEntity;
import com.alkemy.ot9.exceptions.TeamNotFound;
import com.alkemy.ot9.mappers.TeamMapper;
import com.alkemy.ot9.models.Team;
import com.alkemy.ot9.repository.TeamRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TeamServiceTest {

    @Mock
    TeamRepository teamRepository;

    TeamMapper teamMapper;

    TeamService teamService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        teamMapper = new TeamMapper(new ModelMapper());
        teamService = new TeamService(teamMapper, teamRepository);

    }

    @Test
    public void testCreateTeam() {
        Long id = 1L;

        Team team = new Team();
        team.setId(id);

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(id);

        when(teamRepository.save(any())).thenReturn(teamEntity);

        Long resultId = teamService.createTeam(team);

        Assertions.assertEquals(id, resultId);
    }

    @Test
    public void testFindAllTeams() {

        Long id = 1L;
        String name = "team-test-name";
        String surname = "team-test-surname";

        Team team = new Team();
        team.setId(id);
        team.setName(name);
        team.setSurname(surname);

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(id);
        teamEntity.setName(name);
        teamEntity.setSurname(surname);

        List<TeamEntity> teamEntityList = new ArrayList<>();
        teamEntityList.add(teamEntity);

        List<Team> teamList = new ArrayList<>();
        teamList.add(team);

        when(teamRepository.findAll()).thenReturn(teamEntityList);

        List<Team> result = teamService.findAllTeams();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(team.getId(), result.get(0).getId());
        Assertions.assertEquals(team.getName(), result.get(0).getName());
        Assertions.assertEquals(team.getSurname(), result.get(0).getSurname());
    }

    @Test
    public void testGetStudentById() throws TeamNotFound {
        Long id = 1L;
        String name = "team-test-name";
        String surname = "team-test-surname";

        Team team = new Team();
        team.setId(id);
        team.setName(name);
        team.setSurname(surname);

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(id);
        teamEntity.setName(name);
        teamEntity.setSurname(surname);

        when(teamRepository.findById(id)).thenReturn(java.util.Optional.of(teamEntity));

        Team resultTeam = teamService.getTeamById(id);

        Assertions.assertEquals(id, resultTeam.getId());
        Assertions.assertEquals(name, resultTeam.getName());
        Assertions.assertEquals(surname, resultTeam.getSurname());
    }

    @Test
    public void testDeleteStudentById() throws TeamNotFound {
        Long id = 1L;
        String name = "team-test-name";
        String surname = "team-test-surname";

        Team team = new Team();
        team.setId(id);
        team.setName(name);
        team.setSurname(surname);

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(id);
        teamEntity.setName(name);
        teamEntity.setSurname(surname);

        when(teamRepository.findById(id)).thenReturn(java.util.Optional.of(teamEntity));

        teamService.deleteTeamById(1L);
    }

    @Test
    public void testGetTeamByIdException() {
        Long id = 1L;

        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        try {
            teamService.getTeamById(id);
        } catch (TeamNotFound e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

    @Test
    public void testDeleteTeamByIdException() {
        Long id = 1L;

        when(teamRepository.findById(id)).thenReturn(Optional.empty());

        try {
            teamService.deleteTeamById(id);
        } catch (TeamNotFound e) {
            Assert.assertTrue(true);
            return;
        }
        Assert.fail();
    }

}

