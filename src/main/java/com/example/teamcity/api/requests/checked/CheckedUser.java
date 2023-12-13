package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.unchecked.UncheckedUser;
import com.example.teamcity.models.User;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class CheckedUser implements CrudInterface {
    private RequestSpecification spec;

    public CheckedUser(RequestSpecification spec) {
        this.spec = spec;
    }

    @Override
    public Object create(Object obj) {
        return new UncheckedUser(spec).create(obj)
                .then()
                .assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(User.class);
    }

    @Override
    public Object get(String id) {
        return null;
    }

    @Override
    public Object update(Object obj) {
        return null;
    }

    @Override
    public String delete(String username) {
        return new UncheckedUser(spec).delete(username)
                .then()
                .assertThat().statusCode(HttpStatus.SC_NO_CONTENT)
                .extract().asString();
    }
}
