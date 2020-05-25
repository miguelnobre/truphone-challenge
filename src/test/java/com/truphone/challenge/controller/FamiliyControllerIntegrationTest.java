package com.truphone.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truphone.challenge.IntegrationTest;
import com.truphone.challenge.dto.FamilyDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class FamiliyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateFamily() throws Exception {
        FamilyDto newFamily = new FamilyDto();
        newFamily.setName("New Family");
        newFamily.setCountryCode("PRT");

        mockMvc.perform(post("/api/families")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newFamily)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("New Family"))
                .andExpect(jsonPath("countryCode").value("PRT"))
                .andDo(print());
    }

    @Test
    public void testGetFamily() throws Exception {
        mockMvc.perform(get("/api/families/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Nobre"))
                .andExpect(jsonPath("countryCode").value("PRT"))
                .andDo(print());
    }

    @Test
    public void testGetAllFamily() throws Exception {
        mockMvc.perform(get("/api/families/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value("0"))
                .andExpect(jsonPath("limit").value("10"))
                .andExpect(jsonPath("hasNext").value(false))
                .andExpect(jsonPath("elements.[0].name").value("Nobre"))
                .andExpect(jsonPath("elements.[0].countryCode").value("PRT"))
                .andExpect(jsonPath("elements.[1].name").value("Smith"))
                .andExpect(jsonPath("elements.[1].countryCode").value("GBR"))
                .andDo(print());
    }

    @Test
    public void testGetAllFamily_with_two_pages() throws Exception {
        mockMvc.perform(get("/api/families/")
                .param("offset", "0")
                .param("limit", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value("0"))
                .andExpect(jsonPath("limit").value("1"))
                .andExpect(jsonPath("hasNext").value(true))
                .andExpect(jsonPath("elements.[0].name").value("Nobre"))
                .andExpect(jsonPath("elements.[0].countryCode").value("PRT"))
                .andDo(print());

        mockMvc.perform(get("/api/families/")
                .param("offset", "1")
                .param("limit", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value("1"))
                .andExpect(jsonPath("limit").value("1"))
                .andExpect(jsonPath("hasNext").value(false))
                .andExpect(jsonPath("elements.[0].name").value("Smith"))
                .andExpect(jsonPath("elements.[0].countryCode").value("GBR"))
                .andDo(print());
    }

    @Test
    public void testGetFamiliesByCountryCode_with_default_pagination() throws Exception {
        mockMvc.perform(get("/api/families/country/{isoCountryCode}", "PRT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value("0"))
                .andExpect(jsonPath("limit").value("10"))
                .andExpect(jsonPath("hasNext").value(false))
                .andExpect(jsonPath("elements.[0].name").value("Nobre"))
                .andExpect(jsonPath("elements.[0].countryCode").value("PRT"))
                .andDo(print());
    }

    @Test
    public void testGetFamiliesByCountryCode() throws Exception {
        mockMvc.perform(get("/api/families/country/{isoCountryCode}", "PRT")
                .param("offset", "0")
                .param("limit", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value("0"))
                .andExpect(jsonPath("limit").value("10"))
                .andExpect(jsonPath("hasNext").value(false))
                .andExpect(jsonPath("elements.[0].name").value("Nobre"))
                .andExpect(jsonPath("elements.[0].countryCode").value("PRT"))
                .andDo(print());
    }

    @Test
    public void testGetFamiliesByCountryCode_page_without_elements() throws Exception {
        mockMvc.perform(get("/api/families/country/{isoCountryCode}", "PRT")
                .param("offset", "1")
                .param("limit", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value("1"))
                .andExpect(jsonPath("limit").value("20"))
                .andExpect(jsonPath("hasNext").value(false))
                .andExpect(jsonPath("elements").isEmpty())
                .andDo(print());
    }

    @Test
    public void testDeleteFamily() throws Exception {
        FamilyDto newFamily = new FamilyDto();
        newFamily.setName("New Family");
        newFamily.setCountryCode("PRT");

        MvcResult result = mockMvc.perform(post("/api/families")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(newFamily)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value("New Family"))
                .andExpect(jsonPath("countryCode").value("PRT"))
                .andDo(print())
                .andReturn();

        newFamily = new ObjectMapper().readValue(result.getResponse().getContentAsString(), FamilyDto.class);

        mockMvc.perform(delete("/api/families/{id}", newFamily.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("New Family"))
                .andExpect(jsonPath("countryCode").value("PRT"))
                .andDo(print());

        mockMvc.perform(get("/api/families/{id}", newFamily.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateFamily() throws Exception {
        mockMvc.perform(get("/api/families/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Nobre"))
                .andExpect(jsonPath("countryCode").value("PRT"));

        FamilyDto familyToUpdate = new FamilyDto();
        familyToUpdate.setId(1L);
        familyToUpdate.setName("Updated Name");
        familyToUpdate.setCountryCode("PRT");

        mockMvc.perform(put("/api/families/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(familyToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Updated Name"))
                .andExpect(jsonPath("countryCode").value("PRT"))
                .andDo(print());
    }

    @Test
    public void testPartialUpdateFamily() throws Exception {
        mockMvc.perform(get("/api/families/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Nobre"))
                .andExpect(jsonPath("countryCode").value("PRT"));

        FamilyDto familyToUpdate = new FamilyDto();
        familyToUpdate.setId(1L);
        familyToUpdate.setName("Updated Name");

        mockMvc.perform(patch("/api/families/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(familyToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value("Updated Name"))
                .andExpect(jsonPath("countryCode").value("PRT"))
                .andDo(print());
    }

    @Test
    public void testFindAgedFamily() throws Exception {
        mockMvc.perform(get("/api/families/aged")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalAge").value("224"))
                .andExpect(jsonPath("averageAge").value("28.0"))
                .andDo(print());
    }

    @Test
    public void testFindFastGrowingFamily() throws Exception {
        mockMvc.perform(get("/api/families/fast-growing")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("growingRate").value("0.05"))
                .andDo(print());
    }
}
