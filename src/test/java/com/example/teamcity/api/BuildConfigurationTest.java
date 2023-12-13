package com.example.teamcity.api;

import com.example.teamcity.api.requests.checked.CheckedProjectRequest;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.specifications.Specifications;
import org.testng.annotations.Test;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
        //var token = new AuthRequest(user).getCsrfToken();
        var testData = testDataStorage.addTestData();

        new CheckedUser(Specifications.getSpec().superUserSpec()).create(testData.getUser());

        var project = new CheckedProjectRequest(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }
}
