package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.unchecked.UncheckedProjectRequest;
import com.example.teamcity.models.Project;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedProjectRequest implements CrudInterface {
    private static final String PROJECT_ENDPOINT = "/app/rest/projects";
    private final RequestSpecification spec;

    public CheckedProjectRequest(RequestSpecification user) {
        this.spec = user;
    }

    @Override
    public Project create(Object obj) {
        return new UncheckedProjectRequest(spec).create(obj)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Object get(String id) {
        return new UncheckedProjectRequest(spec)
                .get(id)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(Project.class);
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public Object delete(String id) {

        return new UncheckedProjectRequest(spec)
                .delete(id)
                .then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
