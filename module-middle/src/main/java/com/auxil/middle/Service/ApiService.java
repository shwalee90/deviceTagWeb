package com.auxil.middle.Service;

import com.auxil.middle.domain.TbTagBase;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

@Service
public class ApiService {


    public List<TbTagBase> getAllTag(){

        //URI를 빌드한다
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:7001")
                .path("/api/allTag")
                .encode(Charset.defaultCharset())
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<TbTagBase[]> result = restTemplate.getForEntity(uri,TbTagBase[].class);
        List<TbTagBase> list = Arrays.asList(result.getBody());

        return list;
    }



}
