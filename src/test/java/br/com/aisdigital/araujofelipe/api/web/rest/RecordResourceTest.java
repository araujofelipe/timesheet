package br.com.aisdigital.araujofelipe.api.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@SpringBootTest
@AutoConfigureMockMvc
class RecordResourceTest {

	
	@Autowired
	private MockMvc mockMvc;
		
	static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	
	
	ResultActions _post(String uri, Object content, Object... params) throws Exception {
		return this.mockMvc.perform(post(uri, params).content(ow.writeValueAsString(content).getBytes())
				.header(HttpHeaders.AUTHORIZATION, "Basic dmVsaG9jb2VsaG86c2VjcmV0")
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}
}
