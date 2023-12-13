package com.example.teamcity.api;

import com.example.teamcity.api.enums.RoleEnum;
import com.example.teamcity.api.generators.TestDataGenerator;
import com.example.teamcity.api.requests.checked.CheckedBuildConfig;
import com.example.teamcity.api.requests.checked.CheckedProjectRequest;
import com.example.teamcity.api.requests.checked.CheckedUser;
import com.example.teamcity.api.requests.unchecked.UncheckedProjectRequest;
import com.example.teamcity.api.specifications.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

public class RolesTest extends BaseApiTest {

    @Test
    public void unauthorizedUser() {
        var testData = testDataStorage.addTestData();
        new UncheckedProjectRequest(Specifications.getSpec().unauthSpec())
                .create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body(Matchers.equalTo("Authentication required\n" +
                        "To login manually go to \"/login.html\" page"));
        new UncheckedProjectRequest(Specifications.getSpec().authSpec(testData.getUser()))
                .get(testData.getProject().getId())
                .then()
                .assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void systemAdminTest() {
        var testData = testDataStorage.addTestData();
        testData.getUser().setRoles(TestDataGenerator.generateRoles(RoleEnum.SYSTEM_ADMIN, "g"));

        new CheckedUser(Specifications.getSpec().superUserSpec()).create(testData.getUser());

        var project = new CheckedProjectRequest(Specifications.getSpec().authSpec(testData.getUser()))
                .create(testData.getProject());

        softy.assertThat(project.getId()).isEqualTo(testData.getProject().getId());
    }

    @Test
    public void projectAdminTest() {
        var firstTestData = testDataStorage.addTestData();
        var secondTestData = testDataStorage.addTestData();

        var project1 = new CheckedProjectRequest(Specifications.getSpec().superUserSpec())
                .create(firstTestData.getProject());

        firstTestData.getUser().setRoles(TestDataGenerator
                .generateRoles(RoleEnum.PROJECT_ADMIN, "p:"+firstTestData.getProject().getId()));

        new CheckedUser(Specifications.getSpec().superUserSpec()).create(firstTestData.getUser());

        var project2 = new CheckedProjectRequest(Specifications.getSpec().superUserSpec())
                .create(secondTestData.getProject());

        secondTestData.getUser().setRoles(TestDataGenerator
                .generateRoles(RoleEnum.PROJECT_ADMIN, "p:"+secondTestData.getProject().getId()));

        new CheckedUser(Specifications.getSpec().superUserSpec()).create(secondTestData.getUser());

        var buildConfig = new CheckedBuildConfig(Specifications.getSpec().authSpec(secondTestData.getUser()))
                .create(firstTestData.getBuildType());

        softy.assertThat(project1.getId()).isEqualTo(firstTestData.getProject().getId());
        softy.assertThat(buildConfig.getId()).isEqualTo(firstTestData.getBuildType().getId());
    }
}
