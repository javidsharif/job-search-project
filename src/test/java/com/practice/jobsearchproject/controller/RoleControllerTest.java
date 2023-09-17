package com.practice.jobsearchproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.jobsearchproject.model.dto.RoleDto;
import com.practice.jobsearchproject.model.entity.Role;
import com.practice.jobsearchproject.model.mapper.RoleMapper;
import com.practice.jobsearchproject.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = RoleController.class)
@WithMockUser
@RequiredArgsConstructor
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {RoleController.class})
public class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleService roleService;

    @MockBean
    private RoleMapper roleMapper;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllRoles() throws Exception {
        List<Role> userList = List.of(new Role(), new Role());
        when(roleService.getAllRoles()).thenReturn(userList);
        RequestBuilder requestBuilderGet = MockMvcRequestBuilders
                .get("/api/v1/roles")
                .with(csrf());
        MvcResult mvcResultGet = mockMvc.perform(requestBuilderGet)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseBodyGet = mvcResultGet.getResponse().getContentAsString();
        List<RoleDto> list = objectMapper.readValue(responseBodyGet, List.class);
        assertEquals(list.size(), 2);
        assertEquals(HttpStatus.OK.value(), mvcResultGet.getResponse().getStatus());
    }
}

