package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.requests.CrudInterface;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class UncheckedProjectRequest implements CrudInterface {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";
    private final RequestSpecification spec;

    public UncheckedProjectRequest(RequestSpecification spec) {
        this.spec = spec;
    }

    @Override
    public Response create(Object obj) {
        return given()
                .spec(spec)
                .body(obj)
                .post(PROJECT_ENDPOINT);
    }

    @Override
    public Response get(String id) {
        return given().spec(spec)
                .get(PROJECT_ENDPOINT + "/id:" + id);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Response delete(String id) {
        return given().spec(spec)
                .delete(PROJECT_ENDPOINT + "/id:" + id);
    }
}
