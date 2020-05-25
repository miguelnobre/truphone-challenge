package com.truphone.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.truphone.challenge.IntegrationTest;
import com.truphone.challenge.dto.FamilyDto;
import com.truphone.challenge.dto.FamilyMemberDto;
import com.truphone.challenge.dto.FamilyPersonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class FamilyMemberControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testCreateFamilyMember() throws Exception {
        FamilyDto family = new FamilyDto();
        family.setId(1L);

        FamilyPersonDto father = new FamilyPersonDto();
        father.setId(1L);

        FamilyPersonDto mother = new FamilyPersonDto();
        mother.setId(2L);

        FamilyPersonDto me = new FamilyPersonDto();
        me.setFirstName("First Name");
        me.setMiddleName("Middle Name");
        me.setLastName("Last Name");
        me.setDateOfBirth(LocalDate.parse("2000-10-14"));

        FamilyMemberDto newFamilyMember = new FamilyMemberDto();
        newFamilyMember.setMe(me);
        newFamilyMember.setFamily(family);
        newFamilyMember.setFather(father);
        newFamilyMember.setMother(mother);

        mockMvc.perform(post("/api/family-members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newFamilyMember)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("family.id").value(family.getId()))
                .andExpect(jsonPath("father.id").value(father.getId()))
                .andExpect(jsonPath("mother.id").value(mother.getId()))
                .andExpect(jsonPath("firstName").value(me.getFirstName()))
                .andExpect(jsonPath("middleName").value(me.getMiddleName()))
                .andExpect(jsonPath("lastName").value(me.getLastName()))
                .andExpect(jsonPath("dateOfBirth").value("2000-10-14"))
                .andDo(print());
    }

    @Test
    public void testGetFamilyMember() throws Exception {
        mockMvc.perform(get("/api/family-members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value("Miguel"))
                .andExpect(jsonPath("middleName").doesNotExist())
                .andExpect(jsonPath("lastName").value("Nobre"))
                .andDo(print());
    }

    @Test
    public void getFamilyMembers() throws Exception {
        mockMvc.perform(get("/api/family-members/families/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value(0))
                .andExpect(jsonPath("limit").value(10))
                .andExpect(jsonPath("hasNext").value(false))
                .andExpect(jsonPath("elements.[0].family.id").value(2))
                .andExpect(jsonPath("elements.[0].family.name").value("Smith"))
                .andExpect(jsonPath("elements.[0].firstName").value("John"))
                .andExpect(jsonPath("elements.[0].lastName").value("Smith"))
                .andExpect(jsonPath("elements.[1].family.id").value(2))
                .andExpect(jsonPath("elements.[1].family.name").value("Smith"))
                .andExpect(jsonPath("elements.[1].firstName").value("Jane"))
                .andExpect(jsonPath("elements.[1].lastName").value("Smith"))
                .andDo(print());
    }

    @Test
    public void getFamilyMembers_with_two_pages() throws Exception {
        mockMvc.perform(get("/api/family-members/families/2")
                .param("offset", "0")
                .param("limit", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value(0))
                .andExpect(jsonPath("limit").value(1))
                .andExpect(jsonPath("hasNext").value(true))
                .andExpect(jsonPath("elements.[0].family.id").value(2))
                .andExpect(jsonPath("elements.[0].family.name").value("Smith"))
                .andExpect(jsonPath("elements.[0].firstName").value("John"))
                .andExpect(jsonPath("elements.[0].lastName").value("Smith"))
                .andDo(print());

        mockMvc.perform(get("/api/family-members/families/2")
                .param("offset", "1")
                .param("limit", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("offset").value(1))
                .andExpect(jsonPath("limit").value(1))
                .andExpect(jsonPath("hasNext").value(false))
                .andExpect(jsonPath("elements.[0].family.id").value(2))
                .andExpect(jsonPath("elements.[0].family.name").value("Smith"))
                .andExpect(jsonPath("elements.[0].firstName").value("Jane"))
                .andExpect(jsonPath("elements.[0].lastName").value("Smith"))
                .andDo(print());
    }

    @Test
    public void deleteFamilyMember() throws Exception {
        FamilyDto family = new FamilyDto();
        family.setId(1L);

        FamilyPersonDto father = new FamilyPersonDto();
        father.setId(1L);

        FamilyPersonDto mother = new FamilyPersonDto();
        mother.setId(2L);

        FamilyPersonDto me = new FamilyPersonDto();
        me.setFirstName("First Name");
        me.setMiddleName("Middle Name");
        me.setLastName("Last Name");
        me.setDateOfBirth(LocalDate.parse("2000-10-14"));

        FamilyMemberDto newFamilyMember = new FamilyMemberDto();
        newFamilyMember.setMe(me);
        newFamilyMember.setFamily(family);
        newFamilyMember.setFather(father);
        newFamilyMember.setMother(mother);

        MvcResult result = mockMvc.perform(post("/api/family-members")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"family\":{\"id\":1},\"father\":{\"id\":1},\"mother\":{\"id\":2},\"firstName\":\"First Name\",\"middleName\":\"Middle Name\",\"lastName\":\"Last Name\",\"dateOfBirth\": \"2000-10-14\"}"))
                .andExpect(status().isCreated())
                .andReturn();

        newFamilyMember = objectMapper.readValue(result.getResponse().getContentAsString(), FamilyMemberDto.class);

        mockMvc.perform(delete("/api/family-members/{id}", newFamilyMember.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(newFamilyMember.getId()))
                .andDo(print());

        mockMvc.perform(get("/api/family-members/{id}", newFamilyMember.getId()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testUpdateFamilyMember() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/family-members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value("Miguel"))
                .andExpect(jsonPath("middleName").doesNotExist())
                .andExpect(jsonPath("lastName").value("Nobre"))
                .andReturn();

        FamilyMemberDto familyMemberDto = objectMapper.readValue(result.getResponse().getContentAsString(), FamilyMemberDto.class);
        familyMemberDto.getMe().setFirstName("Updated Name");

        mockMvc.perform(put("/api/family-members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(familyMemberDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("firstName").value("Updated Name"))
                .andDo(print());
    }

    @Test
    public void testAddSpouse() throws Exception {
        mockMvc.perform(get("/api/family-members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("spouse").doesNotHaveJsonPath())
                .andExpect(jsonPath("firstName").value("Miguel"))
                .andExpect(jsonPath("middleName").doesNotExist())
                .andExpect(jsonPath("lastName").value("Nobre"))
                .andDo(print());

        FamilyPersonDto spouse = new FamilyPersonDto();
        spouse.setId(2L);
        FamilyMemberDto familyMemberDto = new FamilyMemberDto();
        familyMemberDto.setSpouse(spouse);

        familyMemberDto.setId(1L);
        mockMvc.perform(patch("/api/family-members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(familyMemberDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("spouse.id").value(2))
                .andDo(print());
    }

    @Test
    public void testDestroyMarriage() throws Exception {
        mockMvc.perform(get("/api/family-members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("spouse").doesNotHaveJsonPath())
                .andDo(print());

        FamilyPersonDto spouse = new FamilyPersonDto();
        spouse.setId(2L);

        FamilyMemberDto familyMemberDto = new FamilyMemberDto();
        familyMemberDto.setId(1L);
        familyMemberDto.setSpouse(spouse);

        mockMvc.perform(patch("/api/family-members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(familyMemberDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("spouse.id").value(2))
                .andDo(print());

        MvcResult result = mockMvc.perform(get("/api/family-members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("spouse.id").value(2))
                .andReturn();

        familyMemberDto = objectMapper.readValue(result.getResponse().getContentAsString(), FamilyMemberDto.class);
        familyMemberDto.setSpouse(null);

        mockMvc.perform(put("/api/family-members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(familyMemberDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("spouse.id").doesNotHaveJsonPath())
                .andDo(print());
    }

    @Test
    public void testAddSpouseAlreadyMarried() throws Exception {
        mockMvc.perform(get("/api/family-members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("spouse").doesNotHaveJsonPath())
                .andExpect(jsonPath("firstName").value("Miguel"))
                .andExpect(jsonPath("middleName").doesNotExist())
                .andExpect(jsonPath("lastName").value("Nobre"))
                .andDo(print());

        FamilyPersonDto spouse = new FamilyPersonDto();
        spouse.setId(2L);
        FamilyMemberDto familyMemberDto = new FamilyMemberDto();
        familyMemberDto.setSpouse(spouse);

        familyMemberDto.setId(1L);
        mockMvc.perform(patch("/api/family-members/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(familyMemberDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("spouse.id").value(2))
                .andDo(print());

        familyMemberDto.setId(3L);
        mockMvc.perform(patch("/api/family-members/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(familyMemberDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
