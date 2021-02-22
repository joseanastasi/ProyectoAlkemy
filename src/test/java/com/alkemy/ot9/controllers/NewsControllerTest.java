package com.alkemy.ot9.controllers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.alkemy.ot9.controller.NewsController;
import com.alkemy.ot9.entities.NewsEntity;
import com.alkemy.ot9.exceptions.NewsModelNotFoundException;
import com.alkemy.ot9.interfaceService.INewsService;
import com.alkemy.ot9.util.FileUtil;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = { TestContext.class, WebApplicationContext.class })
@WebAppConfiguration
public class NewsControllerTest {

	@Mock
	private INewsService newsServiceMock;

	@Mock
	private FileUtil fileUtil;

	@InjectMocks
	private NewsController newsController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(newsController).build();

	}

	@Test
	public void testListNews() throws Exception {

		List<NewsEntity> newsList = new ArrayList<NewsEntity>();

		NewsEntity news1 = new NewsEntity();
		news1.setId(1L);
		news1.setTitle("Spring MVC");
		news1.setShortContent("Programación");
		news1.setContent("Inyección de dependencias");
		news1.setCategory("Educación");
		news1.setDate(new Date());
		news1.setEnabled(true);
		news1.setImage("programacion.jpg");

		NewsEntity news2 = new NewsEntity();
		news2.setId(2L);
		news2.setTitle("Spring Boot");
		news2.setShortContent("Programación");
		news2.setContent("Capa de servicio");
		news2.setCategory("Práctica programación");
		news2.setDate(new Date());
		news2.setEnabled(false);
		news2.setImage("matematica.jpg");

		newsList.add(news1);
		newsList.add(news2);

		Page<NewsEntity> pageNewsEntity = new PageImpl<NewsEntity>(newsList);

		int page = 0;
		when(newsServiceMock.getAllNews(PageRequest.of(page, 3))).thenReturn(pageNewsEntity);

		mockMvc.perform(get("/admin/news/")).andExpect(status().isOk()).andExpect(view().name("/news/list-news"))
				.andExpect(model().attribute("list", hasSize(2)))
				.andExpect(model().attribute("list",
						hasItem(allOf(hasProperty("id", is(1L)), hasProperty("title", is("Spring MVC")),
								hasProperty("shortContent", is("Programación"))))))
				.andExpect(model().attribute("list",
						hasItem(allOf(hasProperty("id", is(2L)), hasProperty("title", is("Spring Boot")),
								hasProperty("shortContent", is("Programación"))))))
				.andExpect(model().attribute("current", 1)).andExpect(model().attribute("next", 2))
				.andExpect(model().attribute("previous", 0)).andExpect(model().attribute("last", 1));

		verify(newsServiceMock, times(1)).getAllNews(PageRequest.of(page, 3));
		verifyNoMoreInteractions(newsServiceMock);

	}

	@Test
	public void testCreateNews() throws Exception {

		mockMvc.perform(get("/admin/news/createNews")).andExpect(status().isOk())
				.andExpect(view().name("/news/create-news"))
				.andExpect(model().attribute("news", instanceOf(NewsEntity.class)));
		verifyNoInteractions(newsServiceMock);
	}

	@Test
	public void test1_saveNews_ValidationFails_idnull() throws Exception {

		String title = "";
		String shortContent = "";

		mockMvc.perform(post("/admin/news/saveNews").param("title", title).param("shortContent", shortContent))
				.andExpect(status().isOk()).andExpect(view().name("/news/create-news"))
				.andExpect(model().attribute("news", hasProperty("id", nullValue())))
				.andExpect(model().attribute("news", hasProperty("title", is(title))))
				.andExpect(model().attribute("news", hasProperty("shortContent", is(shortContent))))
				.andExpect(model().attribute("news", instanceOf(NewsEntity.class)))
				.andExpect(model().attribute("titleTable", "Creando Novedad"))
				.andExpect(model().attribute("action", "CREATE"));
	}

	@Test
	public void test2_saveNews_ValidationFails_idNotnull() throws Exception {

		Long id = 1L;
		String title = "";
		String shortContent = "";

		mockMvc.perform(
				post("/admin/news/saveNews").param("id", "1").param("title", title).param("shortContent", shortContent))
				.andExpect(status().isOk()).andExpect(view().name("/news/create-news"))
				.andExpect(model().attribute("news", hasProperty("id", is(id))))
				.andExpect(model().attribute("news", hasProperty("title", is(title))))
				.andExpect(model().attribute("news", hasProperty("shortContent", is(shortContent))))
				.andExpect(model().attribute("news", instanceOf(NewsEntity.class)))
				.andExpect(model().attribute("titleTable", "Editando Novedad"));

	}

	@Test
	public void test3_saveNews_NewsEntryIsAddedToTheDatabase() throws Exception {

		MultipartFile multipartFile = null;
		when(fileUtil.isEmpty(multipartFile)).thenReturn(true);

		mockMvc.perform(
				post("/admin/news/saveNews").param("title", "Junit and Mockito").param("shortContent", "Spring MCV"))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/admin/news"));

		ArgumentCaptor<NewsEntity> formObjectArgument = ArgumentCaptor.forClass(NewsEntity.class);
		verify(newsServiceMock).saveNews(formObjectArgument.capture());

		NewsEntity formObject = formObjectArgument.getValue();

		assertEquals(formObject.getTitle(), "Junit and Mockito");
		assertEquals(formObject.getShortContent(), "Spring MCV");
		assertNull(formObject.getId());

	}

	@Test
	public void test4_saveNews_NewsEntryIsUpdatedToTheDatabase() throws Exception {

		MultipartFile multipartFile = null;
		when(fileUtil.isEmpty(multipartFile)).thenReturn(true);

		mockMvc.perform(post("/admin/news/saveNews").param("id", "1").param("title", "Junit and Mockito")
				.param("shortContent", "Spring MCV")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/news"))
				.andExpect(flash().attribute("success", "Novedad/agenda guardada con éxito!"));

		ArgumentCaptor<NewsEntity> formObjectArgument = ArgumentCaptor.forClass(NewsEntity.class);
		verify(newsServiceMock).saveNews(formObjectArgument.capture());

		NewsEntity formObject = formObjectArgument.getValue();

		assertEquals(formObject.getTitle(), "Junit and Mockito");
		assertEquals(formObject.getShortContent(), "Spring MCV");
		assertNotNull(formObject.getId());
		assertTrue(formObject.getId().equals(1L));

	}

	@Test
	public void test__NewsEntryNotFound() throws Exception {

		when(newsServiceMock.getNewsById(1L))
				.thenThrow(new NewsModelNotFoundException("La agenda/novedad no fue encontrada"));

		mockMvc.perform(get("/admin/news/viewNews/{id}", 1L)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/news"))
				.andExpect(flash().attribute("error", "La agenda/novedad no fue encontrada"));
		verify(newsServiceMock, times(1)).getNewsById(1L);

	}

	@Test
	public void test_viewNewsNewsEntryFound() throws Exception {

		NewsEntity newsEntity = new NewsEntity();
		Long id = 1L;
		String title = "Junit and Mockito";
		String shortContent = "Spring MCV";

		newsEntity.setId(id);
		newsEntity.setTitle(title);
		newsEntity.setShortContent(shortContent);

		when(newsServiceMock.getNewsById(id)).thenReturn(newsEntity);

		mockMvc.perform(get("/admin/news/viewNews/{id}", 1L)).andExpect(status().isOk())
				.andExpect(view().name("/news/see-news"))
				.andExpect(model().attribute("news", instanceOf(NewsEntity.class)))
				.andExpect(model().attribute("news", hasProperty("id", is(1L))))
				.andExpect(model().attribute("news", hasProperty("title", is("Junit and Mockito"))))
				.andExpect(model().attribute("news", hasProperty("shortContent", is("Spring MCV"))));

		verify(newsServiceMock, times(1)).getNewsById(1L);
		verifyNoMoreInteractions(newsServiceMock);
	}

	@Test
	public void test_editNewsEntryFound() throws Exception {

		NewsEntity newsEntity = new NewsEntity();
		Long id = 1L;
		String title = "Junit and Mockito";
		String shortContent = "Spring MCV";

		newsEntity.setId(id);
		newsEntity.setTitle(title);
		newsEntity.setShortContent(shortContent);

		when(newsServiceMock.getNewsById(id)).thenReturn(newsEntity);

		mockMvc.perform(get("/admin/news/edit/{id}", 1L)).andExpect(status().isOk())
				.andExpect(view().name("/news/create-news"))
				.andExpect(model().attribute("news", instanceOf(NewsEntity.class)))
				.andExpect(model().attribute("news", hasProperty("id", is(1L))))
				.andExpect(model().attribute("news", hasProperty("title", is("Junit and Mockito"))))
				.andExpect(model().attribute("news", hasProperty("shortContent", is("Spring MCV"))));

		verify(newsServiceMock, times(1)).getNewsById(1L);
		verifyNoMoreInteractions(newsServiceMock);
	}

	@Test
	public void test_deleteNewsEntryFound() throws Exception {

		NewsEntity newsEntity = new NewsEntity();
		Long id = 1L;
		String title = "Junit and Mockito";
		String shortContent = "Spring MCV";

		newsEntity.setId(id);
		newsEntity.setTitle(title);
		newsEntity.setShortContent(shortContent);

		when(newsServiceMock.getNewsById(id)).thenReturn(newsEntity);

		mockMvc.perform(get("/admin/news/delete/{id}", 1L)).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/admin/news"));

		ArgumentCaptor<Long> formObjectArgument = ArgumentCaptor.forClass(Long.class);
		verify(newsServiceMock).deleteNewsById(formObjectArgument.capture());

		Long formObject = formObjectArgument.getValue();

		assertNotNull(formObject);
		assertTrue(formObject.equals(1L));

		verify(newsServiceMock, times(1)).deleteNewsById(1L);
		verify(newsServiceMock, times(1)).getNewsById(1L);
		verifyNoMoreInteractions(newsServiceMock);
	}

}
